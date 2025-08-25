CREATE DATABASE IF NOT EXISTS jsp;
USE jsp;
CREATE USER IF NOT EXISTS 'student1'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON jsp.* to 'student1'@'localhost';

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS party;
CREATE TABLE party (
	name VARCHAR(100) PRIMARY KEY,
    meet_day ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    start_time TIME NOT NULL
);

CREATE TABLE member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    character_name VARCHAR(100) NOT NULL,
    character_class VARCHAR(50),
    character_race VARCHAR(50),
    phone_number VARCHAR(20),
    strength INT,
    intelligence INT,
    charisma INT,
    constitution INT,
    dexterity INT,
    wisdom INT,
    party_name VARCHAR(100),
    FOREIGN KEY (party_name) REFERENCES party(name) ON DELETE CASCADE
);

INSERT INTO party (name, meet_day, start_time) VALUES
('Critical Crew', 'SUNDAY', '12:00:00');

INSERT INTO member (name, character_name, character_class, character_race, phone_number,
    strength, intelligence, charisma, constitution, dexterity, wisdom, party_name)
VALUES
('Alice Smith', 'Thalara', 'Wizard', 'Elf', '123-456-7890', 8, 18, 12, 10, 14, 16, 'Critical Crew'),
('Bob Jones', 'Grug', 'Barbarian', 'Orc', '234-567-8901', 18, 6, 10, 16, 12, 8, 'Critical Crew');