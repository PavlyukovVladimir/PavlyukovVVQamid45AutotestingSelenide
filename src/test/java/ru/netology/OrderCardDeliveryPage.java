package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.*;


public class OrderCardDeliveryPage {
    private SelenideElement cityElement = $("[data-test-id=city]");
    private SelenideElement dateElement = $("[data-test-id=date]");
    private SelenideElement nameElement = $("[data-test-id=name]");
    private SelenideElement phoneElement = $("[data-test-id=phone]");
    private SelenideElement agreementElement = $("[data-test-id=agreement]");
    private SelenideElement submitElement = $("button.button");
    private SelenideElement notificationElement = $("[data-test-id=notification]");
    private SelenideElement inputInvalidElement = $(".input_invalid");

    public void fillCity(@NotNull String city) {
        cityElement.$("input").shouldBe(Condition.visible).setValue(city);
    }

    public void fillCityByPopupList(@NotNull String city) {
        // Ввод первых 2х букв
        cityElement.$("input").shouldBe(Condition.visible).setValue(city.substring(0, 2));
        // список выпавших элементов
        ElementsCollection ec = $$(".menu-item__control");
        SelenideElement findedControlCityElement = null;
        for (SelenideElement controlCityElement : ec) {
            String cityText = controlCityElement.getText();
            if (cityText.equals(city)) {
                // проверка, что не задублирован элемент
                assertNull(findedControlCityElement);
                findedControlCityElement = controlCityElement;
            }
        }
        // проверка, что нужный найден
        assertNotNull(findedControlCityElement);
        findedControlCityElement.click();
        assertEquals(city, cityElement.$("input").getValue());
    }

    public void clearDate() {
        dateElement.$("input").click();
        Actions actions = new Actions(dateElement.$("input").shouldBe(Condition.visible).getWrappedDriver());
        actions
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .keyDown(Keys.DELETE)
                .keyUp(Keys.DELETE)
                .perform();
    }

    public void fillDate(@NotNull String date) {
        clearDate();
        dateElement.$("input").shouldBe(Condition.visible).setValue(date);
    }

    public String getDate() {
        return dateElement.$("input").getValue();
    }

    public void fillDateByWidget(@NotNull String dateStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.add(Calendar.DAY_OF_MONTH, 3);

        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(formatter.parse(dateStr));

        dateElement.$("input").shouldBe(Condition.visible).click();

        LocalDate calendarDate = LocalDate.now().plusDays(3);

        int minDateYear = minCalendar.get(Calendar.YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        if (targetYear >= minDateYear) {
            for (int i = minDateYear; i < targetYear; i++) {
                SelenideElement yearForwardArrow = $("[data-step=\"12\"]");
                yearForwardArrow.click();
                SelenideElement calendarName = $(".calendar__title .calendar__name");

                calendarDate = calendarDate.plusYears(1);
                minCalendar.add(Calendar.YEAR, 1);

                String nameDateStr = calendarDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
                nameDateStr = nameDateStr.substring(0, 1).toUpperCase() + nameDateStr.substring(1).toLowerCase();
                nameDateStr += " " + calendarDate.getYear();

//                System.out.println(nameDateStr);

                calendarName.shouldBe(Condition.text(nameDateStr));
            }
        } else {
            for (int i = minDateYear; targetYear < i; i--) {
                SelenideElement yearBackArrow = $("[data-step=\"-12\"]");
                yearBackArrow.click();
                SelenideElement calendarName = $(".calendar__title .calendar__name");
                calendarDate = calendarDate.minusYears(1);
                minCalendar.add(Calendar.YEAR, -1);

                String nameDateStr = calendarDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
                nameDateStr = nameDateStr.substring(0, 1).toUpperCase() + nameDateStr.substring(1).toLowerCase();
                nameDateStr += " " + calendarDate.getYear();

//                System.out.println(nameDateStr);

                calendarName.shouldBe(Condition.text(nameDateStr));
            }
        }

        int minDateMonth = minCalendar.get(Calendar.MONTH);
        int targetMonth = targetCalendar.get(Calendar.MONTH);

        if (targetMonth >= minDateMonth) {
            for (int i = minDateMonth; i < targetMonth; i++) {
                SelenideElement monthForwardArrow = $("[data-step=\"1\"]");
                monthForwardArrow.click();
                SelenideElement calendarName = $(".calendar__title .calendar__name");

                calendarDate = calendarDate.plusMonths(1);
                minCalendar.add(Calendar.MONTH, 1);

                String nameDateStr = calendarDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
                nameDateStr = nameDateStr.substring(0, 1).toUpperCase() + nameDateStr.substring(1).toLowerCase();
                nameDateStr += " " + calendarDate.getYear();

//                System.out.println(nameDateStr);

                calendarName.shouldBe(Condition.text(nameDateStr));
            }
        } else {
            for (int i = minDateMonth; targetMonth < i; i--) {
                SelenideElement monthBackArrow = $("[data-step=\"-1\"]");
                monthBackArrow.click();
                SelenideElement calendarName = $(".calendar__title .calendar__name");

                calendarDate = calendarDate.minusMonths(1);
                minCalendar.add(Calendar.MONTH, -1);

                String nameDateStr = calendarDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
                nameDateStr = nameDateStr.substring(0, 1).toUpperCase() + nameDateStr.substring(1).toLowerCase();
                nameDateStr += " " + calendarDate.getYear();

//                System.out.println(nameDateStr);

                calendarName.shouldBe(Condition.text(nameDateStr));
            }
        }
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MILLISECOND, 0);

//        System.out.println(targetCalendar.getTimeInMillis());

        $(".calendar__day[data-day=\"" + targetCalendar.getTimeInMillis() + "\"]").click();

        dateElement.$("input").should(Condition.value(dateStr));
    }

    public void fillName(@NotNull String name) {
        nameElement.$("input").shouldBe(Condition.visible).setValue(name);
    }

    public void fillPhone(@NotNull String phone) {
        phoneElement.$("input").shouldBe(Condition.visible).setValue(phone);
    }

    public void clickCheckBox() {
//        agreementElement.$(".checkbox__box").click();
        agreementElement.shouldBe(Condition.visible).click();
    }

    public boolean isInvalidCheckBox() {
        boolean check = agreementElement.is(Condition.cssClass("input_invalid"));
        return check;
    }

    public void clickSubmit() {
        submitElement.shouldBe(Condition.visible).click();
    }

    public String notificationMessage() {
        notificationElement
                .$(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        return notificationElement.$(".notification__content").getText();
    }

    public String getInvalidMessage() {
        return inputInvalidElement.$(".input__sub").getText();
    }

//    @Test
//    public void test() throws ParseException {
//        fillDateByWidget("25.12.2022");
//    }
}
