package com.vaadin.tests.server;

import static org.easymock.EasyMock.createMock;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import com.vaadin.Application;
import com.vaadin.Application.ApplicationStartEvent;
import com.vaadin.server.AbstractWebApplicationContext;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.WebApplicationContext;
import com.vaadin.service.ApplicationContext.TransactionListener;

import org.easymock.EasyMock;

public class TransactionListenersConcurrency extends TestCase {

    /**
     * This test starts N threads concurrently. Each thread creates an
     * application which adds a transaction listener to the context. A
     * transaction is then started for each application. Some semi-random delays
     * are included so that calls to addTransactionListener and
     * WebApplicationContext.startTransaction are mixed.
     * 
     */
    public void testTransactionListeners() throws Exception {
        final List<Throwable> exceptions = new ArrayList<Throwable>();

        HttpSession session = createSession();
        final WebApplicationContext context = WebApplicationContext
                .getApplicationContext(session);
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    Application app = new Application() {

                        @Override
                        public void init() {
                            // Sleep 0-1000ms so another transaction has time to
                            // start before we add the transaction listener.
                            try {
                                Thread.sleep((long) (1000 * new Random()
                                        .nextDouble()));
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            getContext().addTransactionListener(
                                    new DelayTransactionListener(2000));
                        }

                    };

                    // Start the application so the transaction listener is
                    // called later on.
                    try {
                        DeploymentConfiguration dc = EasyMock
                                .createMock(DeploymentConfiguration.class);
                        EasyMock.expect(dc.isProductionMode()).andReturn(true);
                        EasyMock.expect(dc.getInitParameters()).andReturn(
                                new Properties());
                        EasyMock.replay(dc);

                        app.start(new ApplicationStartEvent(new URL(
                                "http://localhost/"), dc, context));
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        // Call the transaction listener using reflection as
                        // startTransaction is protected.

                        Method m = AbstractWebApplicationContext.class
                                .getDeclaredMethod("startTransaction",
                                        Application.class, Object.class);
                        m.setAccessible(true);
                        m.invoke(context, app, null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            });

            threads.add(t);
            t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    if (e.getCause() != null) {
                        e = e.getCause();
                    }
                    exceptions.add(e);
                }
            });
        }

        // Start the threads and wait for all of them to finish
        for (Thread t : threads) {
            t.start();
        }
        int running = threads.size();

        while (running > 0) {
            for (Iterator<Thread> i = threads.iterator(); i.hasNext();) {
                Thread t = i.next();
                if (!t.isAlive()) {
                    running--;
                    i.remove();
                }
            }
        }

        for (Throwable t : exceptions) {
            if (t instanceof InvocationTargetException) {
                t = t.getCause();
            }
            if (t != null) {
                t.printStackTrace(System.err);
                fail(t.getClass().getName());
            }
        }

        System.out.println("Done, all ok");

    }

    /**
     * Creates a HttpSession mock
     * 
     */
    private static HttpSession createSession() {
        HttpSession session = createMock(HttpSession.class);
        EasyMock.expect(
                session.getAttribute(WebApplicationContext.class.getName()))
                .andReturn(null).anyTimes();
        session.setAttribute(
                EasyMock.eq(WebApplicationContext.class.getName()),
                EasyMock.anyObject());

        EasyMock.replay(session);
        return session;
    }

    /**
     * A transaction listener that just sleeps for the given amount of time in
     * transactionStart and transactionEnd.
     * 
     */
    public static class DelayTransactionListener implements TransactionListener {

        private int delay;

        public DelayTransactionListener(int delay) {
            this.delay = delay;
        }

        @Override
        public void transactionStart(Application application,
                Object transactionData) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void transactionEnd(Application application,
                Object transactionData) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
