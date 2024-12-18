CREATE TABLE Contests (
    contest_id INT AUTO_INCREMENT PRIMARY KEY,
    contest_name VARCHAR(255) NOT NULL,
    contest_date DATE NOT NULL,
    division ENUM('Div.1', 'Div.2') NOT NULL,
    writer VARCHAR(255),
    problem_count INT NOT NULL
);