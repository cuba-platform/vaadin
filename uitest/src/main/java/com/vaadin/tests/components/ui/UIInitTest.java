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

package com.vaadin.tests.components.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.tests.components.AbstractTestUI;
import com.vaadin.ui.Label;

public class UIInitTest extends AbstractTestUI {

    @Override
    protected void setup(VaadinRequest request) {
        addComponent(new Label("Hello UI"));
    }

    @Override
    public String getTestDescription() {
        return "Testing basic UI creation";
    }

    @Override
    protected Integer getTicketNumber() {
        return Integer.valueOf(3067);
    }
}
