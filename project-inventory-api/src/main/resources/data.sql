DELETE FROM pantry WHERE name IN ('Cereal', 'Peanut Butter', 'Rice', 'Banana');
DELETE FROM fridge WHERE name IN ('Milk', 'Apple');
DELETE FROM freezer WHERE name IN ('Chicken Breast', 'Ice Cream');

INSERT INTO pantry (name, date_added) VALUES ('Cereal', '2026-05-01');
INSERT INTO pantry (name, date_added) VALUES ('Peanut Butter', '2026-05-01');
INSERT INTO pantry (name, date_added) VALUES ('Rice', '2026-05-01');
INSERT INTO pantry (name, quantity, date_added) VALUES ('Banana', 6, '2026-05-01');

INSERT INTO fridge (name, date_added, expiration_date) VALUES ('Milk', '2026-05-01', '2026-05-15');
INSERT INTO fridge (name, quantity, date_added) VALUES ('Apple', 3, '2026-05-01');

INSERT INTO freezer (name, quantity, date_added, expiration_date) VALUES ('Chicken Breast', 2, '2025-11-08', '2026-05-08');
INSERT INTO freezer (name, date_added) VALUES ('Ice Cream', '2026-05-01');