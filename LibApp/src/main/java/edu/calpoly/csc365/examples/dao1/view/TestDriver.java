package edu.calpoly.csc365.examples.dao1.view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;

import edu.calpoly.csc365.examples.dao1.dao.CheckoutHistoryDaoImpl;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.dao.ReservationDaoImpl;
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Level;
import edu.calpoly.csc365.examples.dao1.entity.Reservation;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;
import edu.calpoly.csc365.examples.dao1.entity.Student;

public class TestDriver {
	public static int cur_student_id;
	public static DaoManager dm = null;
	public static Connection conn = null;
	public static PreparedStatement preparedStatement = null;
	public static ResultSet resultSet = null;
	
	public static Dao<Book> bookDao = null;
	public static Dao<Student> studentDao = null;
	public static Dao<Level> levelDao = null;
	public static Dao<CheckoutHistory> checkoutDao = null;
	public static Dao<Reservation> reservationDao = null;
	
	// setting up the date format
	public static String pattern = "yyyy-MM-dd";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		cur_student_id = 1;


		try {
			dm = DaoManager.getInstance().setProperties("properties.xml");
			conn = DaoManager.getConnection();
			bookDao = dm.getBookDao();
			studentDao = dm.getStudentDao();
			levelDao = dm.getLevelDao();
			checkoutDao = dm.getCheckoutHistoryDao();
			reservationDao = dm.getReservationDao();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userInputDriver();
	}

	public static void userInputDriver() throws SQLException{
		String input = "";

		displayWelcomeMessage();
		Scanner in = new Scanner(System.in);
		getUserId(in);

		// If id <= 0, this is manager
		if (cur_student_id <= 0) {
			displayAllCheckedOutBooks();
			displayAllOverdueBooks();

			while (!input.equals("q")) {
				displayManagerMenu();
				displayPrompt();
				input = in.next();
				executeManagerCommand(input, in);
			}
		}

		// Else, prompt student menu
		else {
			displayCheckedOutBooks();
			displayReservedBooks();
			displayOverdueBooks();

			while (!input.equals("q")) {
				displayStudentMenu();
				displayPrompt();
				input = in.next();
				executeStudentCommand(input, in);
			}
		}

		in.close();
	}

	public static void getUserId(Scanner in) {
		System.out.printf("\nPlease enter your id: ");
		cur_student_id = in.nextInt();
	}

	public static void displayPrompt() {
		System.out.printf("\nEnter a command: ");
	}

	public static void displayWelcomeMessage() {
		System.out.println("******************************************************************************\n");
		System.out.println("                    Welcome to the Library Checkout System");
		System.out.println("\n******************************************************************************");
	}

	public static void displayStudentMenu() {
		System.out.println("\nMenu Options");
		System.out.println("    s: Search for book");
		System.out.println("    t: Return a book");
		System.out.println("    n: Renew a book");
		System.out.println("    v: View Checkout History");
		System.out.println("    q: Quit Library Checkout System");
	}

	public static void displayManagerMenu() {
		System.out.println("\nMenu Options");
		System.out.println("    v: View Monthly Checkout Overview");
		System.out.println("    q: Quit Library Checkout System");
	}

	public static void displayAllCheckedOutBooks() throws  SQLException{

		ResultSet rs = getAllCheckedOutBooks(cur_student_id);

		System.out.println("\nAll Currently Checked Out Books:");

		if (rs.next() == false) {
			System.out.println("There no books currently checked out!");
		}
		else {
			rs.previous();
			printOutput(rs);
		}
	}

	public static void displayCheckedOutBooks() throws  SQLException{

		ResultSet rs = getCurrentCheckoutHistoryByStudentId(cur_student_id);

		System.out.println("\nCurrently Checked Out Books:");

		if (rs.next() == false) {
			System.out.println("You have no books currently checked out!");
		}
		else {
			rs.previous();
			printOutput(rs);
		}
	}

	public static void displayReservedBooks() throws  SQLException {

		ResultSet rs = getReservedBooksByStudentId(cur_student_id);

		System.out.println("\nCurrently Reserved Books:");

		if (rs.next() == false) {
			System.out.println("You have no books currently reserved!");
		} else {
			rs.previous();
			printOutput(rs);
		}
	}

	public static void displayOverdueBooks() throws  SQLException {
		ResultSet rs = getOverdueBooks(cur_student_id);

		if (rs.next() != false) {
			System.out.println("\nWarning! The following books are overdue:");
			rs.previous();
			printOutput(rs);
		}
	}

	public static void displayAllOverdueBooks() throws  SQLException {
		ResultSet rs = getAllOverdueBooks();

		if (rs.next() != false) {
			System.out.println("\nThe following students currently have overdue books:");
			rs.previous();
			printOutput(rs);
		}

		else {
			System.out.println("\nThere are currently no books overdue.");
		}
	}

	public static void executeStudentCommand(String input, Scanner in) throws SQLException{
		switch(input)
		{
			case "s":
				searchForBook(in);
				break;
			case "t":
				returnBook(in);
				break;
			case "n":
				renewBook(in);
				break;
			case "v":
				viewHistory(in);
				break;
			case "q":
				dm.close();
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				break;
		}
	}

	public static void executeManagerCommand(String input, Scanner in) {
		switch(input)
		{
			case "v":
				viewMonthlyOverview(in);
				break;
			case "q":
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				break;
		}
	}

	public static void searchForBook(Scanner sc){
		System.out.println("Search book by title (t), author (a), or category (c)");
		sc.nextLine();
		String option = sc.nextLine();

		if (option.equals("t")) {

			System.out.println("Enter book title");
			String title = sc.nextLine();

			printOutput(getBookByTitle(title));
		}
		else if (option.equals("a")) {

			System.out.println("Enter author name");
			String author = sc.nextLine();

			printOutput(getBookByAuthor(author));
		}
		else if (option.equals("c")) {

			System.out.println("Enter category");
			String category = sc.nextLine();

			printOutput(getBookByCategory(category));
		}
		else {
			System.exit(0);
		}


		int book_id;
		Book book = null;

		System.out.println("Enter option (c for checkout or r for reserve): ");
		String opt = sc.nextLine();
		System.out.println("Enter the book id: ");
		book_id = sc.nextInt();
		book = bookDao.getById(book_id);
		Student cur_student = studentDao.getById(cur_student_id);
		
		
		if (opt.equals("c")) {
			// update availability
			book.setAvailability(false);
			bookDao.update(book);

			createCheckoutHisotry(book, cur_student_id);


		} else if (opt.equals("r")) {
			// insert into reservation table 
			if (studentHasReservation(cur_student_id)) {
				System.out.println("You has reached the reservation limit \n"
						+ "Cannot reserve");
			} else {
				// check for max number of book checkout 
				reserveBook(cur_student, book);
			}
		}




	}

	public static void returnBook(Scanner in){
		// Put code in here
	}

	public static void renewBook(Scanner in){
		// Put code in here
	}

	public static void viewHistory(Scanner in) throws SQLException{
		ResultSet rs = getPreviousCheckoutHistoryByStudentId(cur_student_id);
		if (rs.next() == false) {
			System.out.println("no previous checkout history");
		}
		else {
			printOutput(rs);
		}

	}

	public static void viewMonthlyOverview(Scanner in){
		printOutput(getCheckoutSummary());
	}

	public static void createCheckoutHisotry(Book book, int student_id) {
		// ------------ create checkout history -----------------
		// get the current student grad level
		Student cur_student = studentDao.getById(student_id);
		// get restriction on the student
		Level cur_student_level = levelDao.getById(cur_student.getGradLevel());

		// setting up the date format
		Calendar c = Calendar.getInstance();
		Date cur_date = new Date();
		String cur_date_str = simpleDateFormat.format(cur_date);

		//Setting the date to the given date
		c.setTime(cur_date);
		// add checkout duration to get the due date
		c.add(Calendar.DAY_OF_MONTH, cur_student_level.getTimeLimit());
		String due_date = simpleDateFormat.format(c.getTime());

		// check for book limit 
		CheckoutHistory checkout_hist = null;
		if (cur_student.getBooksCheckedOut() >= cur_student_level.getBookLimit()) {
			System.out.println("WARNING !!! You have reached the maximum number of books to check out");
		} else {
			// check for reservation on that book 
			if (bookHasReservation(book.getBookId())) {
				System.out.println("The book has been reserved");
			} else {
				checkout_hist = new CheckoutHistory(
						null, book.getBookId(), cur_student_id, 0, cur_date_str, null, due_date);
				checkoutDao.insert(checkout_hist);
				System.out.println("Check out successfully \n"
						+ "Your book is due on " + due_date);
			}
		}
	}
	

	public static ResultSet getCheckoutBooks(Dao<CheckoutHistory> historyDao) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT title FROM Books WHERE availability = 0");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}


		return resultSet;
	}
	
	// ALL UNPACK FUNCTIONS 
	private static Set<Reservation> unpackReservation(ResultSet rs) throws SQLException {
		Set<Reservation> reservations = new HashSet<Reservation>();

		while(rs.next()) {
			Reservation reservation = new Reservation(
					rs.getInt("reservation_id"),
					rs.getInt("Reservation_id"),
					rs.getInt("student_id"),
					rs.getString("date") );
			reservations.add(reservation);
		}
		return reservations;
	}  
	
	// check if a the book has some one reserve on it 
	public static Boolean bookHasReservation(Integer book_id) {
		Set<Reservation> reservations = null;
		Boolean hasRsrv;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM Reservations\n" + 
					"WHERE book_id =? ORDER BY date ASC LIMIT 1;");
			preparedStatement.setInt(1, book_id);
			resultSet = preparedStatement.executeQuery();
			reservations = unpackReservation(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// if the set is not empty 
		hasRsrv = (reservations.size() == 0) ?  false : true;		
		return hasRsrv;
	}

	public static void printOutput(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//------------------------ manager queries ----------------------------------------
	public static ResultSet getCheckoutBooks() {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT title FROM Books WHERE availability = 0");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getOverdueBooks(Integer student_id) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT Books.book_id, title FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"JOIN Students ON CheckoutHistories.student_id = Students.student_id\n" +
							"WHERE return_date IS NULL AND CURDATE() > due_date AND Students.student_id = ?\n" +
							"ORDER BY title, Books.book_id");
			preparedStatement.setInt(1, student_id);
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getAllOverdueBooks() {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT Students.student_id, title FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"JOIN Students ON CheckoutHistories.student_id = Students.student_id\n" +
							"WHERE return_date IS NULL AND CURDATE() > due_date\n" +
							"ORDER BY Students.student_id");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getCheckoutSummary() {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"(SELECT title,\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 1  THEN 1 ELSE 0 END) AS 'Jan',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 2 THEN 1 ELSE 0 END) AS 'Feb',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 3 THEN 1 ELSE 0 END) AS 'Mar',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 4 THEN 1 ELSE 0 END) AS 'Apr',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 5 THEN 1 ELSE 0 END) AS 'May',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 6 THEN 1 ELSE 0 END) AS 'Jun',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 7 THEN 1 ELSE 0 END) AS 'Jul',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 8 THEN 1 ELSE 0 END) AS 'Aug',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 9 THEN 1 ELSE 0 END) AS 'Sep',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 10 THEN 1 ELSE 0 END) AS 'Oct',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 11 THEN 1 ELSE 0 END) AS 'Nov',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 12 THEN 1 ELSE 0 END) AS 'Dec',\n" +
							"    COUNT(*) AS 'Year'\n" +
							"FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"WHERE YEAR(checkout_date) = 2019\n" +
							"GROUP BY CheckoutHistories.book_id)\n" +
							"UNION\n" +
							"SELECT '' AS 'title', '' AS 'Jan', '' AS 'Feb', '' AS 'Mar', '' AS 'Apr', '' AS 'May', '' AS 'Jun', '' AS 'Jul', '' AS 'Aug', '' AS 'Sep', '' AS 'Oct', '' AS 'Nov', '' AS 'Dec', '' AS 'Year'\n" +
							"UNION\n" +
							"(SELECT 'Totals' AS title,\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 1  THEN 1 ELSE 0 END) AS 'Jan',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 2 THEN 1 ELSE 0 END) AS 'Feb',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 3 THEN 1 ELSE 0 END) AS 'Mar',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 4 THEN 1 ELSE 0 END) AS 'Apr',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 5 THEN 1 ELSE 0 END) AS 'May',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 6 THEN 1 ELSE 0 END) AS 'Jun',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 7 THEN 1 ELSE 0 END) AS 'Jul',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 8 THEN 1 ELSE 0 END) AS 'Aug',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 9 THEN 1 ELSE 0 END) AS 'Sep',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 10 THEN 1 ELSE 0 END) AS 'Oct',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 11 THEN 1 ELSE 0 END) AS 'Nov',\n" +
							"    SUM(CASE WHEN MONTH(checkout_date) = 12 THEN 1 ELSE 0 END) AS 'Dec',\n" +
							"    COUNT(*) AS 'Year'\n" +
							"FROM CheckoutHistories\n" +
							"WHERE YEAR(checkout_date) = 2019)");
			resultSet = preparedStatement.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	//----------------------------------------student queries--------------------------------------------------------

	public static ResultSet getCurrentCheckoutHistoryByStudentId(Integer student_id) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT Books.book_id, title, author, checkout_date, due_date\n" +
							"FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"WHERE student_id = ?\n" +
							"    AND return_date IS NULL\n" +
							"ORDER BY due_date");

			preparedStatement.setInt(1, student_id);
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getReservedBooksByStudentId(Integer student_id) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = ReservationDaoImpl.conn.prepareStatement(
					"SELECT Books.book_id, title, author, date\n" +
							"FROM Reservations\n" +
							"JOIN Books ON Reservations.book_id = Books.book_id\n" +
							"WHERE student_id = ?\n" +
							"ORDER BY date");

			preparedStatement.setInt(1, student_id);
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getPreviousCheckoutHistoryByStudentId(Integer student_id) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT Books.book_id, title, author, checkout_date, due_date, return_date\n" +
							"FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"WHERE student_id = ?\n" +
							"    AND return_date IS NOT NULL\n" +
							"ORDER BY due_date");

			preparedStatement.setInt(1, student_id);
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getAllCheckedOutBooks(Integer student_id) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT Students.student_id, Books.book_id, title, author, checkout_date, due_date\n" +
							"FROM CheckoutHistories\n" +
							"JOIN Books ON CheckoutHistories.book_id = Books.book_id\n" +
							"JOIN Students ON CheckoutHistories.student_id = Students.student_id\n" +
							"ORDER BY Students.student_id");

			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getBookByTitle(String title) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT * FROM Books WHERE title LIKE ?");

			preparedStatement.setString(1, "%"+title+"%");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getBookByAuthor(String author) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT * FROM Books WHERE author LIKE ?");

			preparedStatement.setString(1, "%"+author+"%");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ResultSet getBookByCategory(String category) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT * FROM Books WHERE category LIKE ?");

			preparedStatement.setString(1, "%"+category+"%");
			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static Boolean studentHasReservation(Integer student_id) {
		Set<Reservation> reservations = null;
		Boolean hasRsrv;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM Reservations\n" + 
					"WHERE student_id=? ORDER BY date ASC LIMIT 1;");
			preparedStatement.setInt(1, student_id);
			resultSet = preparedStatement.executeQuery();
			reservations = unpackReservation(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// if the set is not empty 
		hasRsrv = (reservations.size() == 0) ?  false : true;		
		return hasRsrv;
	}

	public static void reserveBook(Student student, Book book) {
		int book_limit = levelDao.getById(student.getGradLevel()).getBookLimit();
		String checkout;
		Scanner sc = new Scanner(System.in);
		
		if (student.getBooksCheckedOut() == book_limit ) {
			String d = simpleDateFormat.format(new Date());
			Reservation reservation = new Reservation(null, book.getBookId(), cur_student_id, d);
			reservationDao.insert(reservation);
			System.out.println("Reservation for book \"" + book.getTitle() + "\" is placed on "
					+ d);
		} else if (book.getAvailability()) {
			System.out.println("This book is available. Do you want to check out? (y/n)");
			checkout = sc.nextLine();
			if (checkout.equals("y")) {
				createCheckoutHisotry(book, student.getStudentId());
			} 
		} else {
			System.out.println("You have reached max book check out \n"
					+ "Cannot reserve !!!");
		}
	}
}


