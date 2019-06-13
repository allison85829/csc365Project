-- Insert statements for data
-- Books
INSERT INTO Books (title, author, category, availability) VALUES ("Hamster Princess: Harriet the Invincible", "Ursula Vernon", "Children's Books", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Littlest Christmas Tree", "R. A. Herman", "Children's Books", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Mouse Paint", "Ellen Stoll Walsh", "Children's Books", 0);
INSERT INTO Books (title, author, category, availability) VALUES ("The Amazing Spider-Man & Amazing Fantasy No.15" ,"STAN LEE & STEVE DITKO", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Complete Far Side", "Gary Larson", "Comics & Graphic Novels", 0);
INSERT INTO Books (title, author, category, availability) VALUES ("Exploring Calvin and Hobbes: An Exhibition Catalogue", "Bill Watterson", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("After Nothing Comes", "Aidan Koch", "Comics & Graphic Novels", 0);
INSERT INTO Books (title, author, category, availability) VALUES ("Stars of Fortune: Book One of the Guardians Trilogy", "Nora Roberts", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Hobbit", "J. R. R. Tolkien", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Seveneves: A Novel", "Neal Stephenson", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Abhorsen", "Garth Nix", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Six of Crows", "Leigh Bardugo", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Lightning Thief", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Lightning Thief", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Lightning Thief", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Maximum Ride", "James Patterson", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Maximum Ride", "James Patterson", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Sabriel", "Garth Nix", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Lirael", "Garth Nix", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Dreamdark: Blackbringer", "Laini Taylor", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Dreamdark: Silksinger", "Laini Taylor", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Lost Hero", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Son of Neptune", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Mark of Athena", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The House of Hades", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Blood of Olympus", "Rick Riordan", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Fablehaven: Rise of the Evening Star", "Brandon Mull", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Fablehaven: Secrets of the Dragon Sanctuary", "Brandon Mull", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Saving the World and Other Extreme Sports", "James Patterson", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("School's Out Forever", "James Patterson", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("THe Final Warning", "James Patterson", "Science Fiction & Fantasy", 1);

-- Levels
INSERT INTO Levels (level_name, book_limit, checkout_duration, renew_limit) VALUES ("Undergraduate", 3, 7, 1);
INSERT INTO Levels (level_name, book_limit, checkout_duration, renew_limit) VALUES ("Graduate", 5, 14, 2);

-- Students
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (2);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);

-- Checkout history
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (1, 4, "2019-01-10", "2019-01-24", "2019-01-20", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (2, 2, "2019-01-10", "2019-01-24", "2019-01-20", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (16, 14, "2019-02-13", "2019-02-27", "2019-01-26", 1);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (9, 10, "2019-03-21", "2019-03-28", "2019-03-24", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (13, 10, "2019-03-21", "2019-03-28", "2019-03-24", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (1, 9, "2019-03-29", "2019-04-13", "2019-04-13", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (5, 7, "2019-04-01", "2019-04-08", "2019-04-07", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (5, 8, "2019-04-10", "2019-04-17", "2019-04-18", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (9, 4, "2019-05-10", "2019-05-24", "2019-05-20", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (5, 4, "2019-05-20", "2019-06-04", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (15, 10, "2019-05-25", "2019-06-01", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (27, 10, "2019-05-25", "2019-06-01", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (28, 10, "2019-05-25", "2019-06-01", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (3, 4, "2019-06-01", "2019-06-15", NULL, 0);
--
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (6, 4, "2019-06-01", "2019-06-15", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (12, 4, "2019-06-01", "2019-06-15", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (7, 4, "2019-06-01", "2019-06-15", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (2, 14, "2019-06-05", "2019-06-19", NULL, 0);

-- Reservations
INSERT INTO Reservations (book_id, student_id, date) values (3, 8, "2019-06-05");
INSERT INTO Reservations (book_id, student_id, date) values (3, 1, "2019-06-09");
INSERT INTO Reservations (book_id, student_id, date) values (15, 2, "2019-06-05");
INSERT INTO Reservations (book_id, student_id, date) values (5, 11, "2019-06-10");

-- Making sure counts are correct based off of insert data
UPDATE Students SET books_checked_out = 3 WHERE student_id = 4;
UPDATE Students SET books_checked_out = 3 WHERE student_id = 10;



/** Tests ordered by section
    1. Check out a book
        - Show that the search functionality to check out a book
        - Show that the student's page is updated
        - Search the book to show that it is no longer available
        - Try to check out a book that is not available - should fail
        - Try to have an undergrad student check out more than 3 books (student 10)
        - Try to have a grad student check out more than 5 books (student 4)
    2. Return a book
        - Show the updated return date
        - Search the book to show that it is now available
    3. Reserve a book
        - Use the search to bring up a book that is not currently available
        - Reserve that book, show wherever reserves are displayed
        - Show that when that book is returned it automatically checks out to the person who reserved it
    4. Renew a book
        - Show that a book renewed by an undergrad student gets renewed for 7 days (student 10)
        - Show that a book renewed by a grad student gets renewed for 14 days (student 4)
        - Try to have an undergrad student renew a book that has already been renewed (student 10)
        - Try to have a grad student renew a book that has already been renewed (student 4)
        - Try to have a student renew a book that is reserved by another (student 10, book 15)
    5. Library manager interface
        - Show the month-by-month usage of the library for the year
        - Show the list of all books that are checked out
        - Show the list of all books that are overdue


    Tests ordered by login id
    1. Undergrad student (student 4)
        - Show the inital screen with books currently checked out, and check out history
        - 
    2. Grad student (student 4)
        - Show the inital screen with overdue books, books currently checked out, and check out history
        - 
*/
