package com.vaadin.tests.components.table;

import com.vaadin.tests.components.TestBase;
import com.vaadin.ui.CheckBox;
import com.vaadin.v7.ui.Table;

public class TableScrollOnFocus extends TestBase {
    @Override
    protected void setup() {
        final Table table = new Table();
        final CheckBox chkSelectable = new CheckBox("selectable");

        chkSelectable.addValueChangeListener(
                event -> table.setSelectable(chkSelectable.getValue()));

        table.addContainerProperty("row #", String.class, "-");
        table.setColumnWidth("row #", 150);
        for (int i = 1; i < 200; i++) {
            table.addItem(new String[] { "" + i }, null);
        }
        table.setSortDisabled(true);

        chkSelectable.setValue(true);

        addComponent(chkSelectable);
        addComponent(table);
    }

    @Override
    protected String getDescription() {
        return "The table scrolls up 2 pages after loosing and regaining the focus!</b><p>"
                + "Drag scrollbar to top then to the bottom of the table.<br>"
                + "Click somewhere beside the table to take away the focus,<br>"
                + "then click back on the table (header or scrollbar) to give back the focus<br>"
                + "(Pressing Tab and Shift-Tab does the same job).<p>"
                + "If the table is set to non-selectable-mode, no self-scrolling occurs.";
    }

    @Override
    protected Integer getTicketNumber() {
        return 6774;
    }
}
