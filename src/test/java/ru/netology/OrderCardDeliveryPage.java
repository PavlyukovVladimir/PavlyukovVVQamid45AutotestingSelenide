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
    private DataHelper.CardOrderInputInfo cardOrderInputInfo;
    private SelenideElement cityElement = $("[data-test-id=city] input");
    private SelenideElement citySubElement = $("[data-test-id=city] .input__sub");
    private SelenideElement dateElement = $("[data-test-id=date] input");
    private SelenideElement dateSubElement = $("[data-test-id=date] .input__sub");
    private SelenideElement nameElement = $("[data-test-id=name] input");
    private SelenideElement nameSubElement = $("[data-test-id=name] .input__sub");
    private SelenideElement phoneElement = $("[data-test-id=phone] input");
    private SelenideElement phoneSubElement = $("[data-test-id=phone] .input__sub");
    private SelenideElement agreementElement = $("[data-test-id=agreement]");
    private SelenideElement submitElement = $("button.button");
    private SelenideElement notificationElement = $("[data-test-id=notification]");


    public OrderCardDeliveryPage fillCity(@NotNull String city) {
        cityElement.shouldBe(Condition.visible).setValue(city);
        cityElement.shouldBe(Condition.visible).pressEscape();
        return this;
    }

    public OrderCardDeliveryPage fillCityByPopupList(@NotNull String city) {
        // Ввод первых 2х букв
        cityElement.shouldBe(Condition.visible).setValue(city.substring(0, 2));
        // список выпавших элементов
        ElementsCollection ec = $$(".menu-item__control");
        SelenideElement foundControlCityElement = ec.findBy(Condition.text(city));
        foundControlCityElement.click();
        cityElement.shouldHave(Condition.value(city));
        return this;
    }

    public OrderCardDeliveryPage clearDate() {
        dateElement.click();
        Actions actions = new Actions(dateElement.shouldBe(Condition.visible).getWrappedDriver());
        actions
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();
        return this;
    }

    public OrderCardDeliveryPage fillDate(@NotNull String date) {
        clearDate();
        dateElement.shouldBe(Condition.visible).setValue(date);
        return this;
    }

    public OrderCardDeliveryPage fillDateByWidget(@NotNull String dateStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.add(Calendar.DAY_OF_MONTH, 3);

        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(formatter.parse(dateStr));

        dateElement.shouldBe(Condition.visible).click();

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

        dateElement.should(Condition.value(dateStr));
        return this;
    }

    public OrderCardDeliveryPage fillName(@NotNull String name) {
        nameElement.shouldBe(Condition.visible).setValue(name);
        return this;
    }

    public OrderCardDeliveryPage fillPhone(@NotNull String phone) {
        phoneElement.shouldBe(Condition.visible).setValue(phone);
        return this;
    }

    public OrderCardDeliveryPage clickCheckBox() {
        agreementElement.shouldBe(Condition.visible).click();
        return this;
    }

    public OrderCardDeliveryPage clickSubmit() {
        submitElement.shouldBe(Condition.visible).click();
        return this;
    }

    public void checkNotificationMessage(@NotNull String dateStr) {
        notificationElement
                .$(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        notificationElement
                .$(".notification__content")
                .shouldBe(Condition.text("Встреча успешно забронирована на " + dateStr));
    }

    public void checkNotificationMessage() {
        assertNotNull(cardOrderInputInfo);
        assertNotNull(cardOrderInputInfo.getDate());

        notificationElement
                .$(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        notificationElement
                .$(".notification__content")
                .shouldBe(Condition.text("Встреча успешно забронирована на " + cardOrderInputInfo.getDate()));
    }

    public OrderCardDeliveryPage fillForm(@NotNull DataHelper.CardOrderInputInfo info) {
        cardOrderInputInfo = info;

        String city = cardOrderInputInfo.getCity();
        if (city != null) fillCity(city);

        String dateStr = cardOrderInputInfo.getDate();
        if (dateStr != null) fillDate(dateStr);

        String name = cardOrderInputInfo.getName();
        if (name != null) fillName(name);

        String phone = cardOrderInputInfo.getPhone();
        if (phone != null) fillPhone(phone);

        boolean isAgreement = cardOrderInputInfo.getIsAgreement();
        if (isAgreement) clickCheckBox();

        return this;
    }

    void checkCitySubText(@NotNull String text) {
        citySubElement.shouldHave(Condition.text(text));
    }

    void checkDateSubText(@NotNull String text) {
        dateSubElement.shouldHave(Condition.text(text));
    }

    void checkNameSubText(@NotNull String text) {
        nameSubElement.shouldHave(Condition.text(text));
    }

    void checkPhoneSubText(@NotNull String text) {
        phoneSubElement.shouldHave(Condition.text(text));
    }

    void checkAgreementInvalidIndication() {
        agreementElement.shouldHave(Condition.cssClass("input_invalid"));
    }

}
