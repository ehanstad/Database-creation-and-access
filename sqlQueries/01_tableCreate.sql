DROP TABLE IF EXISTS superhero;
DROP TABLE IF EXISTS assistant;
DROP TABLE IF EXISTS power;

CREATE TABLE superhero (
  id SERIAL PRIMARY KEY,
  name varchar(50) NOT NULL,
  alias varchar(50),
  origin varchar(50)
);

CREATE TABLE assistant (
  id SERIAL PRIMARY KEY,
  name varchar(50) NOT NULL
);

CREATE TABLE power (
  id SERIAL PRIMARY KEY,
  name varchar(50) NOT NULL,
  description varchar(50) NOT NULL
);