ALTER TABLE users
    ADD COLUMN login VARCHAR(32);

ALTER TABLE users
    ADD COLUMN password VARCHAR(255);

CREATE TABLE role (
  id UUID
    NOT NULL
    PRIMARY KEY,
  name VARCHAR(255)
    NOT NULL
);

CREATE TABLE user_role (
  role_id UUID
    NOT NULL
    REFERENCES role(id),
  user_id UUID
    NOT NULL
    REFERENCES users(id),
  PRIMARY KEY (role_id, user_id)
);

CREATE TABLE "order" (
  id UUID
    NOT NULL
    PRIMARY KEY,
  restaurant_name VARCHAR(255)
    NOT NULL,
  description VARCHAR(255),
  user_id UUID
    NOT NULL
    REFERENCES users(id),
  end_datetime TIMESTAMP NOT NULL
);

CREATE TABLE order_line_number (
  id UUID
    NOT NULL
    PRIMARY KEY,
  order_id UUID
    NOT NULL
    REFERENCES "order"(id),
  user_id UUID
    NOT NULL
    REFERENCES users(id),
  price DOUBLE PRECISION
    NOT NULL,
  paid BOOLEAN
    NOT NULL
    DEFAULT FALSE
)