package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class AppCardDeliveryTest {
    private final String baseUrl = "http://0.0.0.0:9999";
    private OrderCardDeliveryPage page;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserVersion = "105.0.5195.125";
        Configuration.headless = false;  // true запускает браузер в невидимом режиме
        Configuration.baseUrl = baseUrl;
        Configuration.holdBrowserOpen = true;  // false не оставляет браузер открытым по завершению теста
        Selenide.open("");
        page = new OrderCardDeliveryPage();
    }

    @AfterEach
    void tearDown() {
        Selenide.webdriver().driver().getWebDriver().close();
        Selenide.webdriver().driver().getWebDriver().quit();
    }

    @DisplayName("Проверка административных центров.")
    @ParameterizedTest(name = "{0}")
    @CsvFileSource(files = "src/test/resources/CapitalsRF.csv")
    void allValidCapitalsTest(String capital) {
        page.fillCity(capital);
        page.clickSubmit();
        String expectedSubText = "Поле обязательно для заполнения";
        String actualSubText = page.getInvalidMessage();;
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Отправка формы.")
    @Test
    void positiveWithMinDateTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Не заполнено поле город.")
    @Test
    void negativeEmptyCityTest() {
        page.clickSubmit();
        String expectedSubText = "Поле обязательно для заполнения";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Город РФ не админ центр.")
    @Test
    void negativeNotAdministrativeCenterCityTest() {
        page.fillCity("Оха");
        page.clickSubmit();
        String expectedSubText = "Доставка в выбранный город недоступна";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("На день раньше допустимой даты.")
    @Test
    void negativeEarlierByADayThanTheMinDateTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.clickSubmit();
        String expectedSubText = "Заказ на выбранную дату невозможен";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("На день позже допустимой даты.")
    @Test
    void dayLaterThanTheMinimumAllowedDayTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Не заполненное поле даты.")
    @Test
    void negativeEmptyDateTest() {
        page.fillCity("Москва");
        page.clearDate();
        page.fillName("Иван");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedSubText = "Неверно введена дата";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("На год позже допустимой даты.")
    @Test
    void earLaterThanTheMinimumAllowedDayTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.YEAR, 1);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Имя с ё.")
    @Test
    void nameWithSmallYoTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Неумёха");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Имя с Ё.")
    @Test
    void nameWithLageYoTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Ёжик");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Имя с пробелами и тире.")
    @Test
    void nameWithDashesAndSpacesTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("По утру зубодробительно-скучающий");
        page.fillPhone("+79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedResponseMessage = "Встреча успешно забронирована на " + dateStr;
        String actualResponseMessage = page.notificationMessage();
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }

    @DisplayName("Имя \"-\"")
    @Test
    void negativeNameDashTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("-");
        page.fillPhone("+79781111111");
        page.clickSubmit();
        assertFalse(page.isInvalidCheckBox(), "The wrong name was allowed because the agreement data shows an error.");
    }

    @DisplayName("Имя начинающееся на \"-\"")
    @Test
    void negativeNameWithFirstDashTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("-Мандрагора");
        page.fillPhone("+79781111111");
        page.clickSubmit();
        assertFalse(page.isInvalidCheckBox(), "The wrong name was allowed because the agreement data shows an error.");
    }

    @DisplayName("Имя заканчивающееся на \"-\"")
    @Test
    void negativeNameWithLastDashTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Пенелопа Армани-");
        page.fillPhone("+79781111111");
        page.clickSubmit();
        assertFalse(page.isInvalidCheckBox(), "The wrong name was allowed because the agreement data shows an error.");
    }

    @DisplayName("Не заполненное имя")
    @Test
    void negativeEmptyNameTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillPhone("+79781111111");
        page.clickSubmit();
        assertFalse(page.isInvalidCheckBox(), "The wrong name was allowed because the agreement data shows an error.");
        String expectedSubText = "Поле обязательно для заполнения";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Телефон не заполнен.")
    @Test
    void negativeEmptyPhoneTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedSubText = "Поле обязательно для заполнения";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Телефон без +.")
    @Test
    void negativePhoneWithoutPlusTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("79781111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedSubText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Телефон короче.")
    @Test
    void negativePhoneShortTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+7978111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedSubText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Телефон длиннее.")
    @Test
    void negativePhoneLongTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+797811111111");
        page.clickCheckBox();
        page.clickSubmit();
        String expectedSubText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualSubText = page.getInvalidMessage();
        assertEquals(expectedSubText, actualSubText);
    }

    @DisplayName("Без согласия на обработку персональных данных.")
    @Test
    void negativeCheckboxTest() {
        page.fillCity("Москва");
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateStr = formatter.format(calendar.getTime());
        page.fillDate(dateStr);
        page.fillName("Иван");
        page.fillPhone("+79781111111");
        page.clickSubmit();
        assertTrue(page.isInvalidCheckBox());
    }
}