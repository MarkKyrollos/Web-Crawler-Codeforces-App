CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    screen_name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(100),
    country VARCHAR(100),
    organization VARCHAR(150),
    contributions INT DEFAULT 0,
    friends_count INT DEFAULT 0,
    registration_duration_days INT DEFAULT 0,
    problems_solved INT DEFAULT 0,
    streak_days INT DEFAULT 0
);
