package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    {
        List<User> users = Arrays.asList(
                new User(null, "Vasya", "pupkin@mail.ru", "111", Role.USER, Role.ADMIN),
                new User(null, "Kolya", "kolya@mail.ru", "123", Role.USER),
                new User(null, "Bebra", "bebra@mail.ru", "777", Role.USER),
                new User(null, "Zebra", "zebra@mail.ru", "666", Role.USER),
                new User(null, "Zebra", "zebra_official@mail.ru", "999", Role.USER)
        );
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Optional<User> userOptional = repository.values().stream()
                .filter(user -> user.getEmail().equals(email.toLowerCase(Locale.ROOT)))
                .findFirst();
        return userOptional.orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }
}
