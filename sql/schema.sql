CREATE DATABASE library;

CREATE EXTENSION pgcrypto;

CREATE TABLE roles (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  role VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE users (
  id       UUID PRIMARY KEY,
  email    VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255)        NOT NULL
);

CREATE TABLE user_roles (
  id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  role_id UUID NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);

CREATE TABLE covers (
  id   UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  url  VARCHAR(500) NOT NULL
);

CREATE TABLE genres (
  id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  genre VARCHAR(255) NOT NULL
);

CREATE TABLE books (
  id          UUID PRIMARY KEY,
  name        VARCHAR(255)  NOT NULL,
  page        INTEGER       NOT NULL,
  url         VARCHAR(500),
  create_date TIMESTAMP     NOT NULL,
  date_added  TIMESTAMP              DEFAULT now(),
  cover       UUID,
  description VARCHAR(3000) NOT NULL DEFAULT 'empty',

  FOREIGN KEY (cover) REFERENCES covers (id)
);

CREATE TABLE books_genre (
  id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  book_id  UUID NOT NULL,
  genre_id UUID NOT NULL,

  FOREIGN KEY (book_id) REFERENCES books (id),
  FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE authors (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL
);

CREATE TABLE authors_books (
  id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  book_id   UUID NOT NULL,
  author_id UUID NOT NULL,

  FOREIGN KEY (book_id) REFERENCES books (id),
  FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE users_books_rating (
  id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID    NOT NULL,
  book_id UUID    NOT NULL,
  rating  INTEGER NOT NULL DEFAULT 0,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (book_id) REFERENCES books (id)
);

INSERT INTO roles (role) VALUES ('user');
INSERT INTO roles (role) VALUES ('admin');

INSERT INTO genres (genre) VALUES ('Фантастика');
INSERT INTO genres (genre) VALUES ('Фэнтези');
INSERT INTO genres (genre) VALUES ('Приключения');
INSERT INTO genres (genre) VALUES ('Детективы');
INSERT INTO genres (genre) VALUES ('Роман');
INSERT INTO genres (genre) VALUES ('Наука');
INSERT INTO genres (genre) VALUES ('Образование');
INSERT INTO genres (genre) VALUES ('Поэзия');
INSERT INTO genres (genre) VALUES ('Проза');
INSERT INTO genres (genre) VALUES ('Спорт');
INSERT INTO genres (genre) VALUES ('Триллеры');
INSERT INTO genres (genre) VALUES ('Комедия');
INSERT INTO genres (genre) VALUES ('Мистика');
INSERT INTO genres (genre) VALUES ('Экономика');
INSERT INTO genres (genre) VALUES ('Драма');
INSERT INTO genres (genre) VALUES ('Учебники');
INSERT INTO genres (genre) VALUES ('Трагедия');
INSERT INTO genres (genre) VALUES ('История');
INSERT INTO genres (genre) VALUES ('Компьютерная литература');
INSERT INTO genres (genre) VALUES ('Боевик');


ALTER TABLE books ADD rating INT DEFAULT 0 NULL;
ALTER TABLE books ADD votes INT DEFAULT 0 NULL;
ALTER TABLE books ADD average_rating FLOAT DEFAULT 0 NULL;
ALTER TABLE genres ADD count_book INT DEFAULT 0 NULL;