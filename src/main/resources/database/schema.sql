DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_events;

CREATE TABLE users (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR(50),
                       password VARCHAR(100),
                       full_name VARCHAR(100),
                       user_role VARCHAR(50),
                       email VARCHAR(50),
                       enabled BIT,
                       created_at TIMESTAMP
);

CREATE TABLE locations (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           city VARCHAR(50),
                           street VARCHAR(50),
                           house_number VARCHAR(50),
                           postal_code CHAR(6)
);

CREATE TABLE events (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        name VARCHAR(50),
                        location_id INT,
                        status VARCHAR(50),
                        max_capacity INT,
                        current_capacity INT,
                        creation_date TIMESTAMP,
                        start_date TIMESTAMP,
                        end_date TIMESTAMP,
                        description VARCHAR(1000),
                        category_id INT,
                        deleted bit,
                        deleted_at TIMESTAMP,
                        created_by INT
);

CREATE TABLE category (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE user_event (
    user_id INT,
    event_id INT,
    comment VARCHAR(250),
    rating INT
);

ALTER TABLE events
    ADD CONSTRAINT fk_events_locations
        FOREIGN KEY (location_id)
            REFERENCES locations(id)
            ON DELETE CASCADE;

ALTER TABLE events ADD CONSTRAINT fk_events_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE;
ALTER TABLE events ADD CONSTRAINT fk_events_users FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE user_event ADD CONSTRAINT fk_user_event_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE user_event ADD CONSTRAINT fk_user_event_event FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE;