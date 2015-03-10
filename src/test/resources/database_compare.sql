CREATE TABLE EMPLOYEE(
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20),
    last_name  VARCHAR(20),
    email_address VARCHAR(100),
    date_of_joining DATE
);

SELECT CONCAT(first_name, ' ', last_name) as name, email_address as email, date_of_joining as joining from employee
CREATE TABLE PERSON(
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    address  VARCHAR(20),
    email_detail VARCHAR(100),
    joining_date DATE
)

SELECT name as name, email_detail as email, joining_date as joining from person