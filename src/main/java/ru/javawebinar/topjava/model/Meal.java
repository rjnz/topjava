package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m JOIN FETCH m.user WHERE m.user.id=?1 ORDER BY m.id DESC"),
        @NamedQuery(name = Meal.BETWEEN_INCLUSIVE, query = "SELECT m FROM Meal m JOIN FETCH m.user " +
                "WHERE m.user.id=?1 AND m.dateTime >= ?2 AND m.dateTime < ?3 ORDER BY m.id DESC")
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_user_id_unique_date_time")})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BETWEEN_INCLUSIVE = "Meal.getBetweenInclusive";

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
