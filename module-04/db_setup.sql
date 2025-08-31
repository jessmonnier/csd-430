-- Create database and user
CREATE DATABASE IF NOT EXISTS jsp;
USE jsp;

CREATE USER IF NOT EXISTS 'student1'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON jsp.* TO 'student1'@'localhost';

-- Drop old tables
DROP TABLE IF EXISTS books;

-- Create books table
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    pubyear INT,
    cover VARCHAR(255),
    description TEXT
);

-- Insert book data
INSERT INTO books (title, author, pubyear, cover, description) VALUES
('The Locked Tomb Series', 'Tamsyn Muir', 2019, 'locked_tomb.jpg',
 'This book series is scifantasy featuring space-faring necromancers. I really like the author''s writing style because she immerses the reader mercilessly into the world and you have to figure things out as you go. Plus, she''s phenomenal at describings things and characterizing everyone in the books. The first book follows Gideon, who agrees to become cavalier to the necromancer Harrowhark (a young woman Gideon kinda hates) for the chance to get off world. They go to what remains of Earth for Harrowhark to train to become a lyctor, the powerful right hands of the necromancer God.'),

('The Broken Earth Trilogy', 'N.K. Jemisin', 2015, 'broken_earth.jpg',
 'These books take place during an apocalypse on a world where some people can control seismic activity. The author writes in a way that makes the world and characters feel so real. It''s a darker series with a lot of hard themes and topics. The first book starts off with Essun grieving her young son, who was killed by his father when he realized the boy had the feared ability to control seismic activity. But when she realizes her husband left town with their daughter, Essun knows she has to go find them and protect her daughter, who also has that power.'),

('His Dark Materials', 'Philip Pullman', 1995, 'darkmaterials.jpg',
 'This series was made into an HBO show! It explores alternate realities and what it means to grow up. The main character, Lyra, is from a reality where part of your soul lives outside your body as a daemon, an animal tied to you that can talk and can even change shape up until you become an adult and it settles into one form. When kids start going missing, including Lyra''s best friend, she begins a journey to find them that will end up changing the fabric of every reality.'),

('Graceling', 'Kristin Cashore', 2008, 'graceling.jpg',
 'This book tells the story of Katsa. She''s a Graceling, which means she has different colored eyes and a special, supernatural gift. She''s grown up with the Grace of killing, and her uncle, a king, uses her as a bully and assassin. Katsa hates being used this way but is afraid to defy the king, so she rebels in small ways, which leads her to rescue an old man from a dungeon. She soon meets the man''s grandson, Po, who has been searching for his grandfather. When she agrees to help Po uncover the mystery of what happened to his sister and niece in a neighboring kingdom, her whole life gets flipped upside down.'),

('Skyward', 'Brandon Sanderson', 2018, 'skyward.jpg',
 'I like the entire Skyward series a lot, but the first book is easiest to explain on its own. It''s about Spensa, one of the last surviving humans in the universe. She lives on Detritus, a planet with shielding in the atmosphere that helps protect them from the Krell, mysterious aliens who seem intent on wiping the humans out. Spensa wants to become a pilot like her father before her, but getting into pilot school proves to be challenging when everyone but her believes that her father had a mental break and turned traitor, killing a number of his fellow pilots, when she was just a little girl.');