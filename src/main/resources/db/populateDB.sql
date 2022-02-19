DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100002),
       ('ADMIN', 100001);

INSERT INTO meals (user_id ,datetime, description, calories)
VALUES (100000, '2022-02-19 06:30:00', 'user breakfast', 1000),
       (100000, '2022-02-19 11:00:00', 'user lunch', 500),
       (100000, '2022-02-19 17:30:00', 'user dinner', 250),
       (100000, '2022-02-20 06:30:00', 'user next breakfast', 1500),
       (100000, '2022-02-20 11:00:00', 'user next lunch', 500),
       (100000, '2022-02-20 17:30:00', 'user next dinner', 1),
       (100001, '2022-02-19 06:30:00', 'admin breakfast', 1001),
       (100001, '2022-02-19 11:00:00', 'admin lunch', 501),
       (100001, '2022-02-19 17:30:00', 'admin dinner', 251),
       (100002, '2022-02-19 06:30:00', 'guest breakfast', 1002),
       (100002, '2022-02-19 11:00:00', 'guest lunch', 502),
       (100002, '2022-02-19 17:30:00', 'guest dinner', 252);
