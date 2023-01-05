package ru.netology;

import lombok.Value;
//import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class DataHelper {
    private DataHelper() {
    }

    public static String nowWithDaysShift(int days_count) {
        return LocalDate.now().plusDays(days_count).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String nowWithYearsShift(int years_count) {
        return LocalDate.now().plusYears(years_count).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getInvalidCity() {
        return "Оха";
    }

    @Value
    public static class CardOrderInputInfo {
        String city;
        String date;
        String name;
        String phone;
        Boolean isAgreement;
    }

    public static CardOrderInputInfo getValidCardOrderInputInfo() {
        Faker faker = new Faker(Locale.forLanguageTag("ru"));
        var cities = getValidCities();
        var city = cities[(new Random()).nextInt(cities.length)];
        var date = nowWithDaysShift(3 + (new Random()).nextInt(29)); // [3, ..., 31]
        var name = faker.name().fullName();
        var phone = faker.phoneNumber().phoneNumber();
        phone = phone.replaceAll("\\(", "");
        phone = phone.replaceAll("\\)", "");
        phone = phone.replaceAll("-", "");
        CardOrderInputInfo cardOrderInputInfo = new CardOrderInputInfo(city, date, name, phone, true);
        return cardOrderInputInfo;
    }

    public static String[] getValidCities() {
        return new String[]{
                "Абакан",
                "Анадырь",
                "Архангельск",
                "Астрахань",
                "Барнаул",
                "Белгород",
                "Биробиджан",
                "Благовещенск",
                "Брянск",
                "Великий Новгород",
                "Владивосток",
                "Владикавказ",
                "Владимир",
                "Волгоград",
                "Вологда",
                "Воронеж",
                "Гатчина",
                "Горно-Алтайск",
                "Грозный",
                "Екатеринбург",
                "Иваново",
                "Ижевск",
                "Иркутск",
                "Йошкар-Ола",
                "Казань",
                "Калининград",
                "Калуга",
                "Кемерово",
                "Киров",
                "Кострома",
                "Красногорск",
                "Краснодар",
                "Красноярск",
                "Курган",
                "Курск",
                "Кызыл",
                "Липецк",
                "Магадан",
                "Магас",
                "Майкоп",
                "Махачкала",
                "Москва",
                "Мурманск",
                "Нальчик",
                "Нарьян-Мар",
                "Нижний Новгород",
                "Новосибирск",
                "Омск",
                "Орёл",
                "Оренбург",
                "Пенза",
                "Пермь",
                "Петрозаводск",
                "Петропавловск-Камчатский",
                "Псков",
                "Ростов-на-Дону",
                "Рязань",
                "Салехард",
                "Самара",
                "Санкт-Петербург",
                "Санкт-Петербург",
                "Саранск",
                "Саратов",
                "Севастополь",
                "Симферополь",
                "Смоленск",
                "Ставрополь",
                "Сыктывкар",
                "Тамбов",
                "Тверь",
                "Томск",
                "Тула",
                "Тюмень",
                "Улан-Удэ",
                "Ульяновск",
                "Уфа",
                "Хабаровск",
                "Ханты-Мансийск",
                "Чебоксары",
                "Челябинск",
                "Черкесск",
                "Чита",
                "Элиста",
                "Южно-Сахалинск",
                "Якутск",
                "Ярославль"
        };
    }

    public static CardOrderInputInfo getValidCardOrderInputInfoWithoutCity() {
        CardOrderInputInfo info = getValidCardOrderInputInfo();
        return new CardOrderInputInfo(null, info.getDate(), info.getName(), info.getPhone(), info.getIsAgreement());
    }

    public static CardOrderInputInfo getValidCardOrderInputInfoWithoutDate() {
        CardOrderInputInfo info = getValidCardOrderInputInfo();
        return new CardOrderInputInfo(info.getCity(), null, info.getName(), info.getPhone(), info.getIsAgreement());
    }

    public static CardOrderInputInfo getValidCardOrderInputInfoWithoutName() {
        CardOrderInputInfo info = getValidCardOrderInputInfo();
        return new CardOrderInputInfo(info.getCity(), info.getDate(), null, info.getPhone(), info.getIsAgreement());
    }

    public static CardOrderInputInfo getValidCardOrderInputInfoWithoutPhone() {
        CardOrderInputInfo info = getValidCardOrderInputInfo();
        return new CardOrderInputInfo(info.getCity(), info.getDate(), info.getName(), null, info.getIsAgreement());
    }

    public static CardOrderInputInfo getValidCardOrderInputInfoWithoutAgreement() {
        CardOrderInputInfo info = getValidCardOrderInputInfo();
        return new CardOrderInputInfo(info.getCity(), info.getDate(), info.getName(), info.getPhone(), false);
    }

//    @Test
//    void test() {
//        String date = nowWithDaysShift(-2);
//        String date2 = nowWithYearsShift(-2);
//        String ff = "" + getValidCardOrderInputInfo();
//        System.out.println();
//    }
}
