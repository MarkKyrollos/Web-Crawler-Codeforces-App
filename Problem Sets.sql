CREATE TABLE Problem_Sets (
    problem_id INT AUTO_INCREMENT PRIMARY KEY,
    problem_name VARCHAR(255) NOT NULL,
    tags VARCHAR(255),
    time_limit INT NOT NULL, 
    memory_limit INT NOT NULL 
);