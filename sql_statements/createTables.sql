CREATE TABLE Books (
	book_id INT(10) AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
	author VARCHAR(50) NOT NULL,
	category VARCHAR(50) NOT NULL,
	availability TINYINT(1) NOT NULL,
	PRIMARY KEY(book_id)
);

CREATE TABLE Students (
	student_id INT(10) AUTO_INCREMENT,
	grad_level INT(10) NOT NULL,
	PRIMARY KEY(student_id)
);

CREATE TABLE CheckoutHistories (
	entry_id INT(10) AUTO_INCREMENT,
	book_id INT(10) NOT NULL,
	student_id INT(10) NOT NULL,
	checkout_date DATE NOT NULL,
	due_date DATE,
	return_date DATE NOT NULL,
	times_renewed  INT(10) NOT NULL,

	FOREIGN KEY (book_id) REFERENCES Books(book_id),
	FOREIGN KEY (student_id) REFERENCES Students(student_id),
	PRIMARY KEY (entry_id)
);

CREATE TABLE Reservations (
	reservation_id INT(10) AUTO_INCREMENT,
	book_id INT(10) NOT NULL,
	student_id INT(10) NOT NULL,
	date DATE NOT NULL,

	FOREIGN KEY (book_id) REFERENCES Books(book_id),
	FOREIGN KEY (student_id) REFERENCES Students(student_id),
	PRIMARY KEY (reservation_id)
);

CREATE TABLE Levels (
	level_id INT(10) AUTO_INCREMENT,
	level_name VARCHAR(20) NOT NULL,
	book_limit INT(10) NOT NULL,
	checkout_duration INT(10) NOT NULL,
	renew_limit INT(10) NOT NULL,

	PRIMARY KEY (level_id)
);




