# Insert statements for data
# Books
INSERT INTO Books (title, author, category, availability) VALUES ("Hamster Princess: Harriet the Invincible", "Ursula Vernon", "Children's Books", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Littlest Christmas Tree", "R. A. Herman", "Children's Books", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Mouse Paint", "Ellen Stoll Walsh", "Children's Books", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Amazing Spider-Man & Amazing Fantasy No.15" ,"STAN LEE & STEVE DITKO", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Complete Far Side", "Gary Larson", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Exploring Calvin and Hobbes: An Exhibition Catalogue", "Bill Watterson", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("After Nothing Comes", "Aidan Koch", "Comics & Graphic Novels", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Stars of Fortune: Book One of the Guardians Trilogy", "Nora Roberts", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("The Hobbit", "J. R. R. Tolkien", "Science Fiction & Fantasy", 1);
INSERT INTO Books (title, author, category, availability) VALUES ("Seveneves: A Novel", "Neal Stephenson", "Science Fiction & Fantasy", 1);

# Levels
INSERT INTO Levels (level_name, book_limit, checkout_duration, renew_limit) VALUES ("Undergraduate", 3, 7, 1);
INSERT INTO Levels (level_name, book_limit, checkout_duration, renew_limit) VALUES ("Graduate", 5, 14, 2);

# Students
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (1);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);
INSERT INTO Students (grad_level) VALUES (0);

# Checkout history
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (1, 4, "2019-05-10", "2019-05-24", "2019-05-20", 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (3, 4, "2019-06-01", "2019-06-15", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (5, 4, "2019-05-20", "2019-06-04", NULL, 0);
INSERT INTO CheckoutHistories (book_id, student_id, checkout_date, due_date, return_date, times_renewed) VALUES (7, 4, "2019-06-01", "2019-06-15", NULL, 0);

# Reservations
INSERT INTO Reservations (book_id, student_id, date) values (3, 8, "2019-06-05");