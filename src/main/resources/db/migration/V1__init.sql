CREATE TABLE day
(
    id          int         NOT NULL PRIMARY KEY,
    date_of_day date        NOT NULL,
    user_name   varchar(50) NOT NULL
);

CREATE TABLE hour
(
    id       int          NOT NULL PRIMARY KEY,
    time     varchar(2)   NOT NULL,
    day_id   int          NOT NULL,
    activity varchar(255) NOT NULL,
    FOREIGN KEY (day_id) REFERENCES day (id) ON DELETE CASCADE
);