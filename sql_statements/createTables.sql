CREATE TABLE Books {
	book_id INTEGER(10) AUTO_CREMENT,
	title VARCHAR(50) NOT NULL,
	author VARCHAR(50) NOT NULL,
	category VARCHAR(50) NOT NULL,
	availability TINYINT(1) NOT NULL,
	PRIMARY KEY(book_id)
};

CREATE TABLE Students {
	student_id INTEGER(10) AUTO_CREMENT,
	grad_level INTEGER(10) NOT NULL,

	PRIMARY (student_id)
};

CREATE TABLE CheckoutHistories {
	entry_id INTEGER(10) AUTO_CREMENT,
	book_id INTEGER(10) NOT NULL,
	student_id INTEGER(10) NOT NULL,
	checkout_date DATE NOT NULL,
	due_date DATE NOT NULL,
	return_date DATE NOT NULL,
	times_renewed  INTEGER(10) NOT NULL,


	PRIMARY KEY (entry_id),
	FOREIGN KEY (book_id) REFERENCES Book(book_id),
	FOREIGN KEY (student_id) REFERENCES Student(student_id)
};

CREATE TABLE Reservations {
	reservation_id INTEGER(10) AUTO_CREMENT,
	book_id INTEGER(10) NOT NULL,
	student_id INTEGER(10) NOT NULL,
	date DATE NOT NULL,

	PRIMARY KEY (reservation_id),
	FOREIGN KEY (book_id) REFERENCES Book(book_id),
	FOREIGN KEY (student_id) REFERENCES Student(student_id)
};

CREATE TABLE Levels {
	level_id INTEGER(10) AUTO_CREMENT,
	level_name VARCHAR(20) NOT NULL,
	book_limit INTEGER(10) NOT NULL,
	checkout_duration INTEGER(10) NOT NULL,
	renew_limit INTEGER(10) NOT NULL,

	PRIMARY KEY (level_id)
};




