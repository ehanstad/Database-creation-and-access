CREATE TABLE superhero_power(
  superhero_id int REFERENCES superhero,
  power_id int REFERENCES power,
  CONSTRAINT id PRIMARY KEY (superhero_id, power_id)
);