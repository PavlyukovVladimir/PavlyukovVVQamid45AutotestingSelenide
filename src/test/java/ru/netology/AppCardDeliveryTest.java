package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.text.ParseException;
import java.util.Random;


@ExtendWith({ScreenShooterExtension.class})
public class AppCardDeliveryTest {
    private final String baseUrl = "http://localhost:9999";
    private OrderCardDeliveryPage page;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = baseUrl;
//        Configuration.holdBrowserOpen = false;  // false не оставляет браузер открытым по завершению теста
        Configuration.reportsFolder = "build/reports/tests/test/screenshoots";
        Selenide.open("");
        page = new OrderCardDeliveryPage();
    }

    @Disabled("Включать для тщательной проверки")
    @DisplayName("Проверка административных центров.")
    @ParameterizedTest(name = "{0}")
    @CsvFileSource(files = "src/test/resources/CapitalsRF.csv")
    void allValidCapitalsTest(String capital) {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutCity())
                .fillCity(capital)
                .clickSubmit()
                .checkNotificationMessage();
    }

    @DisplayName("Позитивный тест, города подставляются случайно")
    @Test
    void mainPositiveTest() {
        String dateStr = DataHelper.nowWithDaysShift(3);
        page
                .fillForm(DataHelper.getValidCardOrderInputInfo())
                .clickSubmit()
                .checkNotificationMessage();
    }

    @DisplayName("Не заполнено поле город.")
    @Test
    void negativeEmptyCityTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutCity())
                .clickSubmit()
                .checkCitySubText("Поле обязательно для заполнения");
    }

    @DisplayName("Город РФ не админ центр.")
    @Test
    void negativeNotAdministrativeCenterCityTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutCity())
                .fillCity(DataHelper.getInvalidCity())
                .clickSubmit()
                .checkCitySubText("Доставка в выбранный город недоступна");
    }

    @DisplayName("На день раньше допустимой даты.")
    @Test
    void negativeEarlierByADayThanTheMinDateTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .fillDate(DataHelper.nowWithDaysShift(2))
                .clickSubmit()
                .checkDateSubText("Заказ на выбранную дату невозможен");
    }

    @DisplayName("Минимально допустимая дата.")
    @Test
    void minimumAllowedDayTest() {
        String dateStr = DataHelper.nowWithDaysShift(3);
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .fillDate(dateStr)
                .clickSubmit()
                .checkNotificationMessage(dateStr);
    }

    @DisplayName("На день позже допустимой даты.")
    @Test
    void dayLaterThanTheMinimumAllowedDayTest() {
        String dateStr = DataHelper.nowWithDaysShift(4);
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .fillDate(dateStr)
                .clickSubmit()
                .checkNotificationMessage(dateStr);
    }

    @DisplayName("Не заполненное поле даты.")
    @Test
    void negativeEmptyDateTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .clearDate()
                .clickSubmit()
                .checkDateSubText("Неверно введена дата");
    }

    @DisplayName("На год позже допустимой даты.")
    @Test
    void earLaterThanTheMinimumAllowedDayTest() {
        String dateStr = DataHelper.nowWithYearsShift(1);
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .fillDate(dateStr)
                .clickSubmit()
                .checkNotificationMessage(dateStr);
    }

    @DisplayName("Имя с ё.")
    @Test
    void nameWithSmallYoTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("Неумёха")
                .clickSubmit()
                .checkNotificationMessage();
    }

    @DisplayName("Имя с Ё.")
    @Test
    void nameWithLageYoTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("Ёжик")
                .clickSubmit()
                .checkNotificationMessage();
    }

    @DisplayName("Имя с пробелами и тире.")
    @Test
    void nameWithDashesAndSpacesTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("По утру зубодробительно-скучающий")
                .clickSubmit()
                .checkNotificationMessage();
    }

    @DisplayName("Имя \"-\"")
    @Test
    void negativeNameDashTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("-")
                .clickSubmit()
                .checkNameSubText("В имени кроме тире должны быть буквы.");
    }

    @DisplayName("Имя начинающееся на \"-\"")
    @Test
    void negativeNameWithFirstDashTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("-Мандрагора")
                .clickSubmit()
                .checkNameSubText("Имя не может начинаться на тире.");
    }

    @DisplayName("Имя заканчивающееся на \"-\"")
    @Test
    void negativeNameWithLastDashTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .fillName("Пенелопа Армани-")
                .clickSubmit()
                .checkNameSubText("Имя не может заканчиваться на тире.");
    }

    @DisplayName("Не заполненное имя")
    @Test
    void negativeEmptyNameTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutName())
                .clickSubmit()
                .checkNameSubText("Поле обязательно для заполнения");
    }

    @DisplayName("Телефон не заполнен.")
    @Test
    void negativeEmptyPhoneTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutPhone())
                .clickSubmit()
                .checkPhoneSubText("Поле обязательно для заполнения");
    }

    @DisplayName("Телефон без +.")
    @Test
    void negativePhoneWithoutPlusTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutPhone())
                .fillPhone("79781111111")
                .clickSubmit()
                .checkPhoneSubText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
    }

    @DisplayName("Телефон короче.")
    @Test
    void negativePhoneShortTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutPhone())
                .fillPhone("+7978111111")
                .clickSubmit()
                .checkPhoneSubText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
    }

    @DisplayName("Телефон длиннее.")
    @Test
    void negativePhoneLongTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutPhone())
                .fillPhone("+797811111111")
                .clickSubmit()
                .checkPhoneSubText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
    }

    @DisplayName("Без согласия на обработку персональных данных.")
    @Test
    void negativeCheckboxTest() {
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutAgreement())
                .clickSubmit()
                .checkAgreementInvalidIndication();
    }

    @DisplayName("Выбор даты на 7 дней позже текущей через виджет календаря.")
    @Test
    void calendarWidgetTest() throws ParseException {
        String dateStr = DataHelper.nowWithDaysShift(7);
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutDate())
                .fillDateByWidget(dateStr)
                .clickSubmit()
                .checkNotificationMessage(dateStr);
    }

    @DisplayName("Ввод города с помощью выпадающего списка.")
    @Test
    void popupListTest() {
        String[] cities = DataHelper.getValidCities();
        page
                .fillForm(DataHelper.getValidCardOrderInputInfoWithoutCity())
                .fillCityByPopupList(cities[new Random().nextInt(cities.length)])
                .clickSubmit()
                .checkNotificationMessage();
    }
}