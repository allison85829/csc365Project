-- Library Manager View Queries

-- Find all books checked out in a specified year
SELECT DISTINCT title FROM Books, CheckoutHistories
WHERE YEAR(checkout_date) = input_year;

SELECT DISTINCT book_id FROM CheckoutHistories
WHERE YEAR(checkout_date) = input_year;

-- For each book, listed # times checked out in ea month of a year
SELECT book_id, COUNT(book_id) as c_amnt FROM CheckoutHistories
WHERE YEAR(checkout_date) = input_year
GROUP BY MONTH(checkout_date);