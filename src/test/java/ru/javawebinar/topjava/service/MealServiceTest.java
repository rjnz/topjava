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
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.MealTestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.web.MealTestData.*;
import static ru.javawebinar.topjava.web.UserTestData.USER_ID;

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
    MealService service;

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
    public void delete() {
        service.delete(USER_BREAKFAST_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.parse("2022-02-19"), LocalDate.parse("2022-02-19"),USER_ID);
        MealTestData.assertMatch(all, userBreakfast, userLunch, userDinner);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, userBreakfast, userLunch, userDinner, userNextBreakfast, userNextLunch, userNextDinner);
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
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Meal newMeal = getNew();
        newMeal.setId(created.getId());
        assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }

    @Test
    public void createDuplicateDateTime() {
        Meal newMeal = getNew();
        newMeal.setDateTime(LocalDateTime.parse("2022-02-19T06:30:00"));    //user's breakfast
        assertThrows(DuplicateKeyException.class, () -> service.create(newMeal, USER_ID));
    }
}