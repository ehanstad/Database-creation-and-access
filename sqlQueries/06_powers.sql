INSERT INTO power (id, name, description) VALUES (1, 'punch', 'the ability to hit enemies');
INSERT INTO power (id, name, description) VALUES (2, 'build gadgets', 'the ability to build gadgets');
INSERT INTO power (id, name, description) VALUES (3, 'flying', 'the ability to fly');
INSERT INTO power (id, name, description) VALUES (4, 'sticky', 'climb on buildings');

INSERT INTO superhero_power (superhero_id, power_id) VALUES (1, 1);
INSERT INTO superhero_power (superhero_id, power_id) VALUES (2, 1);
INSERT INTO superhero_power (superhero_id, power_id) VALUES (3, 1);
INSERT INTO superhero_power (superhero_id, power_id) VALUES (3, 2);
INSERT INTO superhero_power (superhero_id, power_id) VALUES (2, 3);
INSERT INTO superhero_power (superhero_id, power_id) VALUES (1, 4);