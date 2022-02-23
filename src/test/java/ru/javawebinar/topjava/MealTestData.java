package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_BREAKFAST_ID = START_SEQ + 3;
    public static final int ADMIN_BREAKFAST_ID = START_SEQ + 9;
    public static final int NOT_EXISTS_MEAL_ID = START_SEQ - 1;

    public static final Meal userBreakfast = new Meal(START_SEQ + 3, LocalDateTime.of(2022, Month.FEBRUARY, 19, 6, 30), "user breakfast", 1000);
    public static final Meal userLunch = new Meal(START_SEQ + 4, LocalDateTime.of(2022, Month.FEBRUARY, 19, 11, 0), "user lunch", 500);
    public static final Meal userDinner = new Meal(START_SEQ + 5, LocalDateTime.of(2022, Month.FEBRUARY, 19, 17, 30), "user dinner", 250);
    public static final Meal userNextBreakfast = new Meal(START_SEQ + 6, LocalDateTime.of(2022, Month.FEBRUARY, 20, 6, 30), "user next breakfast", 1500);
    public static final Meal userNextLunch = new Meal(START_SEQ + 7, LocalDateTime.of(2022, Month.FEBRUARY, 20, 11, 0), "user next lunch", 500);
    public static final Meal userNextDinner = new Meal(START_SEQ + 8, LocalDateTime.of(2022, Month.FEBRUARY, 20, 17, 30), "user next dinner", 1);
    public static final Meal adminBreakfast = new Meal(START_SEQ + 9, LocalDateTime.of(2022, Month.FEBRUARY, 19, 6, 30), "admin breakfast", 1001);
    public static final Meal adminLunch = new Meal(START_SEQ + 10, LocalDateTime.of(2022, Month.FEBRUARY, 19, 11, 0), "admin lunch", 501);
    public static final Meal adminDinner = new Meal(START_SEQ + 11, LocalDateTime.of(2022, Month.FEBRUARY, 19, 17, 30), "admin dinner", 251);
    public static final Meal userNightMeal = new Meal(START_SEQ + 12, LocalDateTime.of(2022, Month.FEBRUARY, 21, 0, 0), "user night meal", 1112);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.FEBRUARY, 21, 1, 59, 59), "night food", 1111);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userBreakfast);
        updated.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 19, 7, 0));
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
