-- Student View Queries

-- Get current checkout history of student given the ID
SELECT Books.book_id, title, author, checkout_date, due_date
FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE student_id = given_id
    AND return_date IS NULL
ORDER BY due_date;

-- Get previous checkout history of student given the ID
SELECT book_id, title, author, checkout_date, due_date 
FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE student_id = given_id
    AND return_date IS NOT NULL
ORDER BY due_date;

-- Renew a book (assuming checking if they can renew will be handled in java) based on book_id
-- Note: Determine new due date in java, grad students get 14 days added, undergrad get 7
--       Can't do that in the SQL right now because the DATEADD function is not on the server
UPDATE CheckoutHistories
SET due_date = new_date,
    times_renewed = times_renewed + 1
WHERE book_id = given_id
    AND return_date IS NULL;

-- Return a book based on book_id
-- Note: Current date also has to be determined in java
UPDATE CheckoutHistories
SET return_date = given_date
WHERE book_id = given_id
    AND return_date IS NULL;

UPDATE Books
SET availability = 1
WHERE book_id = given_id;

-- Checking for the most recent reservation
-- If there is one, check out the book based on reservation data and delete that reservation
SELECT * FROM Reservations
WHERE book_id = given_id
ORDER BY date ASC
LIMIT 1;

DELETE FROM Reservations
WHERE reservation_id = given_id;

-- Searching books based on input title or author or both
SELECT * FROM Books
WHERE title = given_title
    AND author = given_author;

SELECT * FROM Books
WHERE title = given_title;

SELECT * FROM Books
WHERE author = given_author;



























