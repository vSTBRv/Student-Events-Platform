insert into users (username, email, password, full_name, user_role, enabled, created_at) values ('student','student@mail.to', '$2a$10$LuZc1rx/MaiUz3jyg8Swr.WXq7ii.GKrW2opHXyiyV1AHaHRDyey2', 'testStudent','STUDENT',1,CURRENT_TIMESTAMP );
insert into users (username, email, password, full_name, user_role, enabled, created_at) values ('admin','admin@mail.to', '$2a$10$/UkOuFk7hC5G6gGnsZ6OkOwJCriQn6RR4J2cZgujWC2j4BhQANA86', 'testAdmin','ADMIN',1,CURRENT_TIMESTAMP );
insert into users (username, email, password, full_name, user_role, enabled, created_at) values ('org','org@mail.to' ,'$2a$10$mOqJBpHZuFbff.M2utwUpOpgBrbYLdTI2.y4pPpkCinpiwq0e22U6', 'testOrg','ORGANIZATION',1,CURRENT_TIMESTAMP );

INSERT INTO category (name) VALUES ('test1'),('test2'),('test3');

INSERT INTO locations (city, street, house_number, postal_code) VALUES
                                                                    ('Warszawa', 'Koszykowa', '86', '00-123'),
                                                                    ('Kraków', 'Wielopole', '15A', '31-072'),
                                                                    ('Gdańsk', 'Długa', '22', '80-827');
INSERT INTO events (name, location_id, status, max_capacity, creation_date, start_date, end_date, comments, category_id, deleted, deleted_at) VALUES
                                                                                                                                                  ('Hackathon Uczelniany', 1, 'PLANNED', 50, CURRENT_TIMESTAMP, '2025-05-10 09:00:00', '2025-05-10 18:00:00', '24h kodowania, pizza, nagrody!',1,0,NULL),
                                                                                                                                                  ('Wieczór planszówek', 2, 'PLANNED', 30, CURRENT_TIMESTAMP, '2025-05-12 17:30:00', '2025-05-12 22:00:00', 'Integracja i gry planszowe dla studentów.',2,0,NULL),
                                                                                                                                                  ('Bieg po Kampusie', 3, 'PLANNED', 100, CURRENT_TIMESTAMP, '2025-05-15 12:00:00', '2025-05-15 14:00:00', 'Zawody biegowe dla każdego poziomu.',3,0,NULL);

