CREATE DATABASE IF NOT EXISTS LibraryDB;

USE LibraryDB

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Issue;

CREATE TABLE Users(
	UID INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(100),
	Password VARCHAR(100),
	Admin BOOLEAN,
	PRIMARY KEY (UID)
);

CREATE TABLE Books(
	BID INT NOT NULL AUTO_INCREMENT,
	BookName VARCHAR(255) NOT NULL,
	Price INT,
	Genre VARCHAR(50),
	PRIMARY KEY (BID)
);

CREATE TABLE Issue(
	IID INT NOT NULL AUTO_INCREMENT,
	UID INT NOT NULL,
	BID INT NOT NULL,
	IssueDate VARCHAR(50),
	ReturnDate VARCHAR(50),
	Period INT,
	Fine INT,
	PRIMARY KEY (IID),
	FOREIGN KEY (UID) REFERENCES Users(UID),
	FOREIGN KEY (BID) REFERENCES Books(BID)
);


INSERT INTO Users(Username, Password, Admin) VALUES('Admin', 'Admin', TRUE);
INSERT INTO Books(BookName, Price, Genre) VALUES('Long Walk To Freedom', 500, 'Biography');
INSERT INTO BOOKS(BookName, Price, Genre) VALUES ('War and Peace', 200, 'Mystery'),  
('The Guest Book', 300, 'Fiction'), 
('The Perfect Murder',150, 'Mystery'), 
('Accidental Presidents', 250, 'Biography'), 
('The Wicked King',350, 'Fiction');
