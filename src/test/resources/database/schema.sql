DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;

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
                           houseNumber VARCHAR(50),
                           postalCode CHAR(6)
);

CREATE TABLE events (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        name VARCHAR(50),
                        location_id INT,
                        status VARCHAR(50),
                        maxCapacity INT,
                        creationDate TIMESTAMP,
                        startDate TIMESTAMP,
                        endDate TIMESTAMP,
                        comments VARCHAR(1000)
);

ALTER TABLE events
    ADD CONSTRAINT fk_events_locations
        FOREIGN KEY (location_id)
            REFERENCES locations(id)
            ON DELETE CASCADE;
