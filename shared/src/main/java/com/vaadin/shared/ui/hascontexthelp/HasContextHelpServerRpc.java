package com.vaadin.shared.ui.hascontexthelp;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface HasContextHelpServerRpc extends ServerRpc {
    /**
     * Context help icon click event.
     *
     * @param mouseEventDetails
     *            serialized mouse event details
     */
    void iconClick(MouseEventDetails mouseEventDetails);
}
