INSERT INTO power (id, name, description) VALUES (1, 'punch', 'the ability to hit enemies');
INSERT INTO power (id, name, description) VALUES (2, 'build gadgets', 'the ability to build gadgets');
INSERT INTO power (id, name, description) VALUES (3, '', '');
INSERT INTO power (id, name, description) VALUES (4, '', '');

INSERT INTO superhero_power (id, superhero_id, power_id) VALUES (1, 1, 1);
INSERT INTO superhero_power (id, superhero_id, power_id) VALUES (2, 2, 1);
INSERT INTO superhero_power (id, superhero_id, power_id) VALUES (3, 3, 1);
INSERT INTO superhero_power (id, superhero_id, power_id) VALUES (4, 3, 2);