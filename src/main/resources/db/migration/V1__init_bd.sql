CREATE TABLE IF NOT EXISTS teams (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     team_name VARCHAR(255) NOT NULL UNIQUE,
                                     balance DECIMAL(13, 2) NOT NULL,
                                     commission_rate DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS players (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        player_name VARCHAR(255) NOT NULL,
        team_id BIGINT,
        date_of_birth DATE NOT NULL,
        start_work_date DATE NOT NULL,
        FOREIGN KEY (team_id) REFERENCES teams(id)
);

