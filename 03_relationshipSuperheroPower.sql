CREATE TABLE superhero_power(
  id int PRIMARY KEY,
  superhero_id int REFERENCES superhero,
  power_id int REFERENCES power
);