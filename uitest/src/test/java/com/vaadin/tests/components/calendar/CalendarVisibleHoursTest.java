package com.vaadin.tests.components.calendar;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.tests.tb3.MultiBrowserTest;

/**
 * Tests calendar via prepared screenshots calendar visible hours of day invalid
 * shows invalid dates(week/day view) (#12521)
 */
public class CalendarVisibleHoursTest extends MultiBrowserTest {

    @Test
    public void testCalendar() throws InterruptedException, IOException {
        openTestURL();

        openWeekView();
        compareScreen("weekview");

        openDayView();

        compareScreen("dayview");
    }

    private void openWeekView() {
        List<WebElement> elements = getDriver()
                .findElements(By.className("v-calendar-week-number"));

        for (WebElement webElement : elements) {
            if (webElement.getText().equals("36")) {
                webElement.click();
                break;
            }
        }
    }

    private void openDayView() {
        List<WebElement> elements = getDriver()
                .findElements(By.className("v-calendar-header-day"));

        for (WebElement webElement : elements) {
            if (webElement.getText().contains("Thursday 9/5/13")) {
                webElement.click();
                break;
            }
        }
    }
}
