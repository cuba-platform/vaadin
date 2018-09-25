/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.StyleConstants;
import com.vaadin.client.TooltipInfo;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.AbstractFieldState;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.hascontexthelp.HasContextHelpServerRpc;

public abstract class AbstractFieldConnector
        extends AbstractComponentConnector
        implements HasContextHelpConnector {

    @Override
    public AbstractFieldState getState() {
        return (AbstractFieldState) super.getState();
    }

    @Override
    public boolean isReadOnly() {
        return super.isReadOnly() || getState().propertyReadOnly;
    }

    public boolean isModified() {
        return getState().modified;
    }

    // Haulmont API
    public boolean isShowErrorForDisabledState() {
        return getState().showErrorForDisabledState;
    }

    @Override
    public TooltipInfo getTooltipInfo(Element element) {
        TooltipInfo info = super.getTooltipInfo(element);
        if (!isEnabled() && !isShowErrorForDisabledState()) {
            info.setErrorMessage(null);
        }
        // Haulmont API
        if (isContextHelpTooltipEnabled()) {
            info.setContextHelp(getState().contextHelpText);
            info.setContextHelpHtmlEnabled(getState().contextHelpTextHtmlEnabled);
        }

        return info;
    }

    @Override
    public boolean hasTooltip() {
        if (isEnabled()) {
            return super.hasTooltip()
                    // Haulmont API
                    || isContextHelpTooltipEnabled();
        } else {
            AbstractComponentState state = getState();
            return state.description != null && !state.description.equals("")
                    || isShowErrorForDisabledState() && state.errorMessage != null && !state.errorMessage.equals("");
        }
    }

    // Haulmont API
    protected boolean isContextHelpTooltipEnabled() {
        boolean hasListeners = getState().registeredEventListeners != null
                && getState().registeredEventListeners.contains(AbstractFieldState.CONTEXT_HELP_ICON_CLICK_EVENT);

        return !hasListeners && getState().contextHelpText != null
                && !getState().contextHelpText.isEmpty();
    }

    /**
     * Checks whether the required indicator should be shown for the field.
     *
     * Required indicators are hidden if the field or its data source is
     * read-only.
     *
     * @return true if required indicator should be shown
     */
    public boolean isRequired() {
        return getState().required && !isReadOnly();
    }

    @Override
    protected void updateWidgetStyleNames() {
        super.updateWidgetStyleNames();

        // add / remove modified style name to Fields
        setWidgetStyleName(StyleConstants.MODIFIED, isModified());

        // add / remove error style name to Fields
        setWidgetStyleNameWithPrefix(getWidget().getStylePrimaryName(),
                StyleConstants.REQUIRED_EXT, isRequired());

        getWidget().setStyleName(StyleConstants.REQUIRED, isRequired());
    }

    // Haulmont API
    @Override
    public void contextHelpIconClick(NativeEvent event) {
        MouseEventDetails details = MouseEventDetailsBuilder
                .buildMouseEventDetails(event, getWidget().getElement());

        getRpcProxy(HasContextHelpServerRpc.class).iconClick(details);
    }

    // Haulmont API
    @Override
    public void contextHelpIconClick(MouseEvent event) {
        contextHelpIconClick(event.getNativeEvent());
    }
}
