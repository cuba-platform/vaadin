package com.vaadin.client.ui;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseEvent;

import java.io.Serializable;

/**
 * An interface used by client-side connectors whose server side component
 * listens for context help icon clicks.
 * <p>
 * Haulmont API.
 */
public interface HasContextHelpConnector extends Serializable {
    /**
     * Notifies a server side component that a context help icon was clicked.
     *
     * @param event
     *            an event with additional details
     */
    void contextHelpIconClick(NativeEvent event);

    /**
     * Notifies a server side component that a context help icon was clicked.
     *
     * @param event
     *            an event with additional details
     */
    void contextHelpIconClick(MouseEvent event);
}
