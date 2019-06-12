-- Library Manager View Queries

-- Find all books checked out in a specified year
SELECT DISTINCT title FROM Books, CheckoutHistories
WHERE YEAR(checkout_date) = 2019;

SELECT DISTINCT book_id FROM CheckoutHistories
WHERE YEAR(checkout_date) = 2019;

-- For each book, listed # times checked out in ea month of a year
SELECT book_id, COUNT(book_id) as c_amnt FROM CheckoutHistories
WHERE YEAR(checkout_date) = 2019
GROUP BY MONTH(checkout_date);