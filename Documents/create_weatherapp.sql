/*
 This is Script sets up the DB and the User WeatherAPP will be using to store it's data persistently.
 Alternatively these parameters could be changed in the application.propertes file to suit an existing user /DB
 spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/weatherAPP
 spring.datasource.username=WeatherAPP
 spring.datasource.password=WeatherAPP 
 */

CREATE DATABASE IF NOT EXISTS weatherapp;

USE weatherapp;

CREATE USER
    IF NOT EXISTS 'WeatherAPP' @'localhost' IDENTIFIED BY 'WeatherAPP';

GRANT ALL PRIVILEGES ON weatherapp.* TO 'WeatherAPP'@'localhost';

FLUSH PRIVILEGES;