package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_BREAKFAST_ID = START_SEQ + 3;
    public static final int ADMIN_BREAKFAST_ID = START_SEQ + 9;

    public static final Meal userBreakfast = new Meal(100003, LocalDateTime.parse("2022-02-19T06:30:00"), "user breakfast", 1000);
    public static final Meal userLunch = new Meal(100004, LocalDateTime.parse("2022-02-19T11:00:00"), "user lunch", 500);
    public static final Meal userDinner = new Meal(100005, LocalDateTime.parse("2022-02-19T17:30:00"), "user dinner", 250);
    public static final Meal userNextBreakfast = new Meal(100006, LocalDateTime.parse("2022-02-20T06:30:00"), "user next breakfast", 1500);
    public static final Meal userNextLunch = new Meal(100007, LocalDateTime.parse("2022-02-20T11:00:00"), "user next lunch", 500);
    public static final Meal userNextDinner = new Meal(100008, LocalDateTime.parse("2022-02-20T17:30:00"), "user next dinner", 1);
    public static final Meal adminBreakfast = new Meal(100009, LocalDateTime.parse("2022-02-19T06:30:00"), "admin breakfast", 1001);
    public static final Meal adminLunch = new Meal(100010, LocalDateTime.parse("2022-02-19T11:00:00"), "admin lunch", 501);
    public static final Meal adminDinner = new Meal(100011, LocalDateTime.parse("2022-02-19T17:30:00"), "admin dinner", 251);
    public static final Meal guestNextBreakfast = new Meal(100012, LocalDateTime.parse("2022-02-19T06:30:00"), "guest next breakfast", 1002);
    public static final Meal guestNextLunch = new Meal(100013, LocalDateTime.parse("2022-02-19T11:00:00"), "guest next lunch", 502);
    public static final Meal guestNextDinner = new Meal(100014, LocalDateTime.parse("2022-02-19T17:30:00"), "guest next dinner", 252);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("2022-02-21T01:59:59"), "night food", 1111);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userBreakfast);
        updated.setId(100003);
        updated.setDateTime(LocalDateTime.parse("2022-02-19T07:00:00"));
        updated.setDescription("updated user breakfast");
        updated.setCalories(1001);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
