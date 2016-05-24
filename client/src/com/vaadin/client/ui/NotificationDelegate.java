package com.vaadin.client.ui;

import com.google.gwt.user.client.Element;

public interface NotificationDelegate {

    void show(Element overlayContainer, Element element, boolean isShowing, String style, int index);

    void hide();
}
