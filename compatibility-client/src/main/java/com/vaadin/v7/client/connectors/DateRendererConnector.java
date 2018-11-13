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
package com.vaadin.v7.client.connectors;

import com.vaadin.shared.ui.Connect;

/**
 * A connector for {@link com.vaadin.ui.components.grid.renderers.DateRenderer
 * DateRenderer}.
 * <p>
 * The server-side Renderer operates on dates, but the data is serialized as a
 * string, and displayed as-is on the client side. This is to be able to support
 * the server's locale.
 *
 * @since 7.4
 * @author Vaadin Ltd
 */
@Connect(value = com.vaadin.v7.ui.renderers.DateRenderer.class, loadStyle = Connect.LoadStyle.NONE)
public class DateRendererConnector extends TextRendererConnector {
    // No implementation needed
}
