-- Library Manager View Queries

-- Find all books checked out in a specified year
SELECT DISTINCT title FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE YEAR(checkout_date) = 2019;

SELECT DISTINCT book_id FROM CheckoutHistories
WHERE YEAR(checkout_date) = 2019;

-- For each book, listed # times checked out in ea month of a year
SELECT book_id, COUNT(book_id) as c_amnt FROM CheckoutHistories
WHERE YEAR(checkout_date) = 2019
GROUP BY MONTH(checkout_date);

-- Usage history for the year by book with months for columns
(SELECT title,
    SUM(CASE WHEN MONTH(checkout_date) = 1  THEN 1 ELSE 0 END) AS 'Jan',
    SUM(CASE WHEN MONTH(checkout_date) = 2 THEN 1 ELSE 0 END) AS 'Feb',
    SUM(CASE WHEN MONTH(checkout_date) = 3 THEN 1 ELSE 0 END) AS 'Mar',
    SUM(CASE WHEN MONTH(checkout_date) = 4 THEN 1 ELSE 0 END) AS 'Apr',
    SUM(CASE WHEN MONTH(checkout_date) = 5 THEN 1 ELSE 0 END) AS 'May',
    SUM(CASE WHEN MONTH(checkout_date) = 6 THEN 1 ELSE 0 END) AS 'Jun',
    SUM(CASE WHEN MONTH(checkout_date) = 7 THEN 1 ELSE 0 END) AS 'Jul',
    SUM(CASE WHEN MONTH(checkout_date) = 8 THEN 1 ELSE 0 END) AS 'Aug',
    SUM(CASE WHEN MONTH(checkout_date) = 9 THEN 1 ELSE 0 END) AS 'Sep',
    SUM(CASE WHEN MONTH(checkout_date) = 10 THEN 1 ELSE 0 END) AS 'Oct',
    SUM(CASE WHEN MONTH(checkout_date) = 11 THEN 1 ELSE 0 END) AS 'Nov',
    SUM(CASE WHEN MONTH(checkout_date) = 12 THEN 1 ELSE 0 END) AS 'Dec',
    COUNT(*) AS 'Year'
FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE YEAR(checkout_date) = 2019
GROUP BY CheckoutHistories.book_id)
UNION
SELECT '' AS 'title', '' AS 'Jan', '' AS 'Feb', '' AS 'Mar', '' AS 'Apr', '' AS 'May', '' AS 'Jun', '' AS 'Jul', '' AS 'Aug', '' AS 'Sep', '' AS 'Oct', '' AS 'Nov', '' AS 'Dec', '' AS 'Year'
UNION
(SELECT 'Totals' AS title,
    SUM(CASE WHEN MONTH(checkout_date) = 1  THEN 1 ELSE 0 END) AS 'Jan',
    SUM(CASE WHEN MONTH(checkout_date) = 2 THEN 1 ELSE 0 END) AS 'Feb',
    SUM(CASE WHEN MONTH(checkout_date) = 3 THEN 1 ELSE 0 END) AS 'Mar',
    SUM(CASE WHEN MONTH(checkout_date) = 4 THEN 1 ELSE 0 END) AS 'Apr',
    SUM(CASE WHEN MONTH(checkout_date) = 5 THEN 1 ELSE 0 END) AS 'May',
    SUM(CASE WHEN MONTH(checkout_date) = 6 THEN 1 ELSE 0 END) AS 'Jun',
    SUM(CASE WHEN MONTH(checkout_date) = 7 THEN 1 ELSE 0 END) AS 'Jul',
    SUM(CASE WHEN MONTH(checkout_date) = 8 THEN 1 ELSE 0 END) AS 'Aug',
    SUM(CASE WHEN MONTH(checkout_date) = 9 THEN 1 ELSE 0 END) AS 'Sep',
    SUM(CASE WHEN MONTH(checkout_date) = 10 THEN 1 ELSE 0 END) AS 'Oct',
    SUM(CASE WHEN MONTH(checkout_date) = 11 THEN 1 ELSE 0 END) AS 'Nov',
    SUM(CASE WHEN MONTH(checkout_date) = 12 THEN 1 ELSE 0 END) AS 'Dec',
    COUNT(*) AS 'Year'
FROM CheckoutHistories
WHERE YEAR(checkout_date) = 2019);

-- Get list of all currently checked out books (both do the same thing)
SELECT title FROM Books WHERE availability = 0;

SELECT title FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE return_date IS NULL;

-- Display all overdue books
SELECT student_id, title, due_date FROM CheckoutHistories
JOIN Books ON CheckoutHistories.book_id = Books.book_id
WHERE return_date IS NULL
    AND CURDATE() > due_date;