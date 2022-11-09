package ru.netology.selenide;

import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private String actualDate() {
        String date = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd MM yyyy"));
        return date;
    }

    @Test
    void happyPath() {
        open("http://localhost:9999");

        //LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd MM yyyy"));
        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate());
        $("[data-test-id='name'] input").setValue("Иван Иванов");

        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        //$("[data-test-id='notification']").shouldBe(visible, Duration.ofMillis(15000));
    }


    @Test
    void deliveryToThisCityIsNotPossible() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Рубцовск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void sendEmptyForm() {
        open("http://localhost:9999");

        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void ifNameIsEmtpy() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate());

        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void ifPhoneIsEmtpy() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate());
        $("[data-test-id='name'] input").setValue("Иван Иванов");

        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void ifDateIsEmtpy() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void ifDateOld() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("10.11.2022");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void ifWrongPhoneNumber() {
        open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("80001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

//    @Test
//    void noTickedConsent() {
//        open("http://localhost:9999");
//
//        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
//        $("[data-test-id='date'] input").doubleClick();
//        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
//        $("[data-test-id='date'] input").setValue(actualDate());
//        $("[data-test-id='name'] input").setValue("Иван Иванов");
//        $("[data-test-id='phone'] input").setValue("+70001112233");
////        $("[data-test-id='agreement']").click();
//        $$("button").findBy(text("Забронировать")).click();
//        $("[data-test-id='agreement'] .input_invalid").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
//    }

}
