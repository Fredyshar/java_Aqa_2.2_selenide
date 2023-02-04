package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardDifficultScenarioTest {

    private String dateAcrossSomeWeeks(long weeksToAdd, String pattern) {
        return LocalDate.now().plusWeeks(weeksToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void happyPath() {
        String city = "Санкт-Петербург";
        open("http://localhost:9999");
        String textDateAcrossWeeks = dateAcrossSomeWeeks(1, "LLLL YYYY").toUpperCase();

        $("[data-test-id='city'] .input__control").click();
        $("[data-test-id='city'] .input__control").setValue(city.substring(0, 2));
        $$(".menu-item").findBy(Condition.text(city)).click();
        $("[data-test-id='date'] input").click();
        String textCalendarName = $(".calendar__name").getText().toUpperCase();

        while (!textCalendarName.equals(textDateAcrossWeeks)) {
            $$(".calendar__arrow").last().click();
            textCalendarName = $(".calendar__name").getText().toUpperCase();
        }
        $$(".calendar__day").findBy(Condition.text(dateAcrossSomeWeeks(1, "d"))).click();

        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateAcrossSomeWeeks(1, "dd.MM.yyyy")))
                .shouldBe((Condition.visible));
    }

    @Test
    void happyPathAcrossThreeWeeks() {
        String city = "Санкт-Петербург";
        open("http://localhost:9999");
        String textDateAcrossWeeks = dateAcrossSomeWeeks(3, "LLLL YYYY").toUpperCase();

        $("[data-test-id='city'] .input__control").click();
        $("[data-test-id='city'] .input__control").setValue(city.substring(0, 2));
        $$(".menu-item").findBy(Condition.text(city)).click();
        $("[data-test-id='date'] .input").click();
        String textCalendarName = $(".calendar__name").getText().toUpperCase();

        while (!textCalendarName.equals(textDateAcrossWeeks)) {
            $$(".calendar__arrow").last().click();
            textCalendarName = $(".calendar__name").getText().toUpperCase();
        }
        $$(".calendar__day").findBy(Condition.text(dateAcrossSomeWeeks(3, "d"))).click();

        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateAcrossSomeWeeks(3, "dd.MM.yyyy")))
                .shouldBe((Condition.visible));
    }

    @Test
    void happyPathAcrossFifteenWeeks() {
        String city = "Санкт-Петербург";
        open("http://localhost:9999");
        String textDateAcrossWeeks = dateAcrossSomeWeeks(15, "LLLL YYYY").toUpperCase();

        $("[data-test-id='city'] .input__control").click();
        $("[data-test-id='city'] .input__control").setValue(city.substring(0, 2));
        $$(".menu-item").findBy(Condition.text(city)).click();
        $("[data-test-id='date'] .input").click();
        String textCalendarName = $(".calendar__name").getText().toUpperCase();

        while (!textCalendarName.equals(textDateAcrossWeeks)) {
            $$(".calendar__arrow").last().click();
            textCalendarName = $(".calendar__name").getText().toUpperCase();
        }
        $$(".calendar__day").findBy(Condition.text(dateAcrossSomeWeeks(15, "d"))).click();

        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+70001112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateAcrossSomeWeeks(15, "dd.MM.yyyy")))
                .shouldBe((Condition.visible));
    }
}
