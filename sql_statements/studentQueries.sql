-- Student View Queries

-- Get current checkout history of student given the ID
SELECT Books.book_id, title, author, checkout_date, due_date
FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE student_id = given_id
    AND return_date IS NULL
ORDER BY due_date;

-- Get previous checkout history of student given the ID
SELECT Books.book_id, title, author, checkout_date, due_date, return_date
FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE student_id = given_id
    AND return_date IS NOT NULL
ORDER BY due_date;

-- Renew a book (assuming checking if they can renew will be handled in java) based on book_id
--   For grad students, add 14 days
UPDATE CheckoutHistories
SET due_date = DATE_ADD(due_date, INTERVAL 14 DAY),
    times_renewed = times_renewed + 1
WHERE book_id = 3
    AND return_date IS NULL;

--   For undergrad students, add 7 days
UPDATE CheckoutHistories
SET due_date = DATE_ADD(due_date, INTERVAL 7 DAY),
    times_renewed = times_renewed + 1
WHERE book_id = 3
    AND return_date IS NULL;

-- Return a book based on book_id
UPDATE CheckoutHistories
SET return_date = CURDATE()
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



























