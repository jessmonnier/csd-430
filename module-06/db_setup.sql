-- Create database and user
CREATE DATABASE IF NOT EXISTS csd430;
USE csd430;

CREATE USER IF NOT EXISTS 'student1'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON csd430.* TO 'student1'@'localhost';

-- Drop old tables
DROP TABLE IF EXISTS jess_library_data;
DROP TABLE IF EXISTS jess_library_genres;

-- Create genre table
CREATE TABLE jess_library_genres (
	name VARCHAR(100) PRIMARY KEY
);

-- Create library table
CREATE TABLE jess_library_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    pub_year INT,
    description TEXT,
	checked_out BOOL DEFAULT False,
	out_date DATE,
	primary_genre VARCHAR(100),
	sub_genre VARCHAR(100),
	FOREIGN KEY (primary_genre) REFERENCES jess_library_genres(name) ON UPDATE CASCADE,
	FOREIGN KEY (sub_genre) REFERENCES jess_library_genres(name) ON UPDATE CASCADE
);

-- Insert genres
INSERT INTO jess_library_genres (name) VALUES 
("Science Fiction"), ("Fantasy"), ("Horror"), ("Romance"), 
("Literary Fiction"), ("Thriller"), ("Mystery"), ("Adventure"), 
("Young Adult"), ("Children's Fiction"), ("Historical Fiction"), 
("Biography/Memoir"), ("True Crime"), ("Self-Help"), ("History"), 
("Travel"), ("Philosophy"), ("Politics"), ("Cooking/Food"), 
("Psychology"), ("Science"), ("Business & Economics"), ("Religion"), 
("Health & Wellness"), ("Graphic Novel"), ("Poetry");

-- Insert library data; ChatGPT used for help generating titles/info
INSERT INTO jess_library_data (title, author, pub_year, description, primary_genre, sub_genre) VALUES
-- Fiction classics
("Pride and Prejudice", "Austen, Jane", 1813, "A classic novel about manners, marriage, and social standing.", "Literary Fiction", "Romance"),
("1984", "Orwell, George", 1949, "Dystopian novel about totalitarianism and surveillance.", "Science Fiction", "Thriller"),
("To Kill a Mockingbird", "Lee, Harper", 1960, "A novel dealing with racial injustice in the American South.", "Literary Fiction", "Historical Fiction"),
("Moby-Dick", "Melville, Herman", 1851, "Epic tale of a captain's obsession with a white whale.", "Adventure", "Literary Fiction"),
("Frankenstein", "Shelley, Mary", 1818, "Gothic novel about a scientist who creates life.", "Horror", "Science Fiction"),
("The Great Gatsby", "Fitzgerald, F. Scott", 1925, "Story of the American Dream and disillusionment.", "Literary Fiction", "Romance"),

-- Nonfiction
("The Diary of a Young Girl", "Frank, Anne", 1947, "Writings from a Jewish girl hiding during WWII.", "Biography/Memoir", "History"),
("A Brief History of Time", "Hawking, Stephen", 1988, "An accessible introduction to cosmology and physics.", "Science", "Philosophy"),
("The Art of War", "Tzu, Sun", -500, "Ancient treatise on military strategy and tactics.", "Philosophy", "History"),
("Thinking, Fast and Slow", "Kahneman, Daniel", 2011, "Explores how humans think and make decisions.", "Psychology", "Self-Help"),
("The Power of Habit", "Duhigg, Charles", 2012, "Examines the science of habit formation and change.", "Self-Help", "Psychology"),
("Steve Jobs", "Isaacson, Walter", 2011, "Biography of Apple co-founder and innovator.", "Biography/Memoir", "Business & Economics"),

-- Fiction / Popular
("The Hobbit", "Tolkien, J.R.R.", 1937, "Fantasy adventure of Bilbo Baggins in Middle-earth.", "Fantasy", "Adventure"),
("Gideon the Ninth", "Muir, Tamsyn", 2019, "A blend of science fiction, fantasy, and gothic mystery featuring necromancers and deadly puzzles.", "Science Fiction", "Fantasy"),
("The Catcher in the Rye", "Salinger, J.D.", 1951, "Story of teenage alienation and rebellion.", "Literary Fiction", "Young Adult"),
("The Girl with the Dragon Tattoo", "Larsson, Stieg", 2005, "Mystery thriller involving a journalist and hacker.", "Mystery", "Thriller"),
("Gone Girl", "Flynn, Gillian", 2012, "Psychological thriller about a missing wife.", "Thriller", "Mystery"),
("The Hunger Games", "Collins, Suzanne", 2008, "Dystopian novel about survival and rebellion.", "Science Fiction", "Young Adult"),
("Anne of Green Gables", "Montgomery, L.M.", 1908, "Coming-of-age story of an imaginative orphan girl.", "Children's Fiction", "Literary Fiction"),
("The Shining", "King, Stephen", 1977, "Horror novel about a haunted hotel and a man's descent into madness.", "Horror", "Thriller"),

-- Adding a few more by genre to make sure each is represented
-- True Crime
("In Cold Blood", "Capote, Truman", 1966, "A pioneering true crime novel about a brutal family murder.", "True Crime", "Literary Fiction"),

-- Self-Help (additional)
("How to Win Friends and Influence People", "Carnegie, Dale", 1936, "Classic advice on interpersonal skills and leadership.", "Self-Help", "Business & Economics"),

-- Travel
("On the Road", "Kerouac, Jack", 1957, "A novel about cross-country road trips and self-discovery.", "Travel", "Literary Fiction"),

-- Politics
("The Communist Manifesto", "Marx, Karl and Engels, Friedrich", 1848, "Foundational political text advocating communism.", "Politics", "Philosophy"),

-- Cooking/Food
("Salt, Fat, Acid, Heat", "Nosrat, Samin", 2017, "A guide to mastering the elements of good cooking.", "Cooking/Food", "Health & Wellness"),

-- Religion
("The Bible", "Various Authors", NULL, "Sacred scriptures central to Christianity.", "Religion", NULL),

-- Health & Wellness (additional)
("Why We Sleep", "Walker, Matthew", 2017, "Exploring the vital importance of sleep for health.", "Health & Wellness", "Science"),

-- Graphic Novel
("Maus", "Spiegelman, Art", 1991, "A graphic novel about the Holocaust using animal allegories.", "Graphic Novel", "Biography/Memoir"),

-- Poetry
("Leaves of Grass", "Whitman, Walt", 1855, "A landmark collection of American poetry.", "Poetry", "Literary Fiction"),

-- Adding a few more to ensure coverage and variety

-- Historical Fiction (additional)
("The Book Thief", "Zusak, Markus", 2005, "A story set in Nazi Germany narrated by Death.", "Historical Fiction", "Young Adult"),

-- Mystery (additional)
("The Hound of the Baskervilles", "Doyle, Arthur Conan", 1902, "Sherlock Holmes investigates a supernatural hound.", "Mystery", "Adventure"),

-- Adventure (additional)
("Treasure Island", "Stevenson, Robert Louis", 1883, "Classic pirate adventure novel.", "Adventure", "Children's Fiction");