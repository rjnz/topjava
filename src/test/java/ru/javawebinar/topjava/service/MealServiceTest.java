package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_BREAKFAST_ID, USER_ID);
        assertMatch(meal, MealTestData.userBreakfast);
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void getNotExists() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTS_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_BREAKFAST_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void deleteNotExists() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTS_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2022, Month.FEBRUARY, 19), LocalDate.of(2022, Month.FEBRUARY, 19), USER_ID);
        MealTestData.assertMatch(all, userDinner, userLunch, userBreakfast);
    }

    @Test
    public void getBetweenNullDates() {
        List<Meal> all = service.getBetweenInclusive(null, null, USER_ID);
        MealTestData.assertMatch(all, userNextDinner, userNextLunch, userNextBreakfast, userDinner, userLunch, userBreakfast);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, userNextDinner, userNextLunch, userNextBreakfast, userDinner, userLunch, userBreakfast);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_BREAKFAST_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateForeign() {
        assertThrows(NotFoundException.class, () -> service.update(adminBreakfast, USER_ID));
    }

    @Test
    public void updateNotExists() {
        Meal notExistsMeal = getNew();
        notExistsMeal.setId(NOT_EXISTS_MEAL_ID);
        assertThrows(NotFoundException.class, () -> service.update(notExistsMeal, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Meal newMeal = getNew();
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }

    @Test
    public void createDuplicateDateTime() {
        Meal newMeal = getNew();
        newMeal.setDateTime(userBreakfast.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newMeal, USER_ID));
    }
}