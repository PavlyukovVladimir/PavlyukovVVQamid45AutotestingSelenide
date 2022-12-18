package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

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
        cityElement.$("input").setValue(city);
    }

    public void clearDate() {
        dateElement.$("input").click();
        Actions actions = new Actions(dateElement.$("input").getWrappedDriver());
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
        dateElement.$("input").setValue(date);
    }

    public void fillName(@NotNull String name) {
        nameElement.$("input").setValue(name);
    }

    public void fillPhone(@NotNull String phone) {
        phoneElement.$("input").setValue(phone);
    }

    public void clickCheckBox() {
//        agreementElement.$(".checkbox__box").click();
        agreementElement.click();
    }

    public boolean isInvalidCheckBox() {
        boolean check = agreementElement.is(Condition.cssClass("input_invalid"));
        return check;
    }

    public void clickSubmit() {
        submitElement.click();
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
}
