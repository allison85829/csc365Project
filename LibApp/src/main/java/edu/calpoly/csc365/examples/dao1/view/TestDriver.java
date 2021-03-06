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

	public static void main(String[] args) throws SQLException, ParseException {
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

	public static void userInputDriver() throws SQLException, ParseException{
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
			//displayCheckedOutBooks();
			displayReservedBooks();
			displayOverdueBooks();

			while (!input.equals("q")) {
				displayCheckedOutBooks();
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
		String s;

		System.out.println("\nAll Currently Checked Out Books:");

		if (rs.next() == false) {
			System.out.println("There no books currently checked out!");
		}
		else {
			rs.previous();

			System.out.printf("Student | Book | %-50s | %-30s | Checkout Date | Due Date\n", "Title", "Author");

			while(rs.next()) {
				s = rs.getString(3);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-7d | %-4d | %-50s | %-30s | %-13s | %-8s\n", rs.getInt(1),
						rs.getInt(2), s, rs.getString(4), rs.getString(5),
						rs.getString(6));
			}
		}
	}

	public static void displayCheckedOutBooks() throws  SQLException {
		ResultSet rs = getCurrentCheckoutHistoryByStudentId(cur_student_id);
		String s;

		System.out.println("\nCurrently Checked Out Books:");

		if (rs.next() == false) {
			System.out.println("You have no books currently checked out!");
		}
		else {
			rs.previous();

			System.out.printf("Book | %-50s | %-30s | Checkout Date | Due Date\n", "Title", "Author");

			while (rs.next()) {
				s = rs.getString(2);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-4d | %-50s | %-30s | %-13s | %-8s\n",
						rs.getInt(1), s, rs.getString(3), rs.getString(4),
						rs.getString(5));
			}
		}
	}

	public static void displayReservedBooks() throws  SQLException {
		ResultSet rs = getReservedBooksByStudentId(cur_student_id);
		String s;

		System.out.println("\nCurrently Reserved Books:");

		if (rs.next() == false) {
			System.out.println("You have no books currently reserved!");
		} else {
			rs.previous();

			System.out.printf("Book | %-50s | %-30s | Date Placed\n", "Title", "Author");

			while(rs.next()) {
				s = rs.getString(2);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-4d | %-50s | %-30s | %-11s\n", rs.getInt(1), s,
						rs.getString(3), rs.getString(4));
			}
		}
	}

	public static void displayOverdueBooks() throws  SQLException {
		ResultSet rs = getOverdueBooks(cur_student_id);
		String s;

		if (rs.next() != false) {
			System.out.println("\nWarning! The following books are overdue:");
			rs.previous();

			System.out.printf("Book | Title\n");

			while (rs.next()) {
				s = rs.getString(2);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-4d | %-50s\n", rs.getInt(1), s);
			}

		}
	}

	public static void displayAllOverdueBooks() throws  SQLException {
		ResultSet rs = getAllOverdueBooks();
		String s;

		if (rs.next() != false) {
			System.out.println("\nThe following students currently have overdue books:");
			rs.previous();

			System.out.printf("Student | Title\n");

			while(rs.next()) {
				s = rs.getString(2);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-7d | %-50s\n", rs.getInt(1), s);
			}

			printOutput(rs);
		}

		else {
			System.out.println("\nThere are currently no books overdue.");
		}
	}

	public static void executeStudentCommand(String input, Scanner in) throws SQLException, ParseException{
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

	public static void executeManagerCommand(String input, Scanner in) throws SQLException {
		switch(input)
		{
			case "v":
				viewMonthlyOverview(in);
				break;
			case "q":
				dm.close();
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				break;
		}
	}

	public static void searchForBook(Scanner sc) throws SQLException {
		System.out.println("Search book by title (t), author (a), or category (c)");
		sc.nextLine();
		String option = sc.nextLine();
		ResultSet rs;
		String s;
		Boolean b;
		Boolean empty = false;

		if (option.equals("t")) {

			System.out.println("Enter book title:");
			String title = sc.nextLine();

			System.out.println("\nSearch Results:");
			rs = getBookByTitle(title);

			if (rs.next() == false) {
				empty = true;
				System.out.println("No search results");
			}
			else {
				rs.previous();
				System.out.printf("Book | %-50s | %-30s | %-30s | Available\n", "Title", "Author", "Category");

				while (rs.next()) {
					s = rs.getString(2);
					if (s.length() > 50) {
						s = s.substring(0, 50);
					}

					if (rs.getInt(5) == 0) {
						b = false;
					} else {
						b = true;
					}

					System.out.printf("%-4d | %-50s | %-30s | %-30s | %-9b\n", rs.getInt(1), s,
							rs.getString(3), rs.getString(4), b);
				}
			}
		}
		else if (option.equals("a")) {

			System.out.println("Enter author name:");
			String author = sc.nextLine();

			System.out.println("\nSearch Results:");
			rs = getBookByAuthor(author);

			if (rs.next() == false) {
				empty = true;
				System.out.println("No search results");
			}
			else {
				rs.previous();
				System.out.printf("Book | %-50s | %-30s | %-30s | Available\n", "Title", "Author", "Category");

				while (rs.next()) {
					s = rs.getString(2);
					if (s.length() > 50) {
						s = s.substring(0, 50);
					}

					if (rs.getInt(5) == 0) {
						b = false;
					} else {
						b = true;
					}

					System.out.printf("%-4d | %-50s | %-30s | %-30s | %-9b\n", rs.getInt(1), s,
							rs.getString(3), rs.getString(4), b);
				}
			}
		}
		else if (option.equals("c")) {

			System.out.println("Enter category:");
			String category = sc.nextLine();

			System.out.println("\nSearch Results:");
			rs = getBookByCategory(category);

			if (rs.next() == false) {
				empty = true;
				System.out.println("No search results");
			}
			else {
				rs.previous();
				System.out.printf("Book | %-50s | %-30s | %-30s | Available\n", "Title", "Author", "Category");

				while (rs.next()) {
					s = rs.getString(2);
					if (s.length() > 50) {
						s = s.substring(0, 50);
					}

					if (rs.getInt(5) == 0) {
						b = false;
					} else {
						b = true;
					}

					System.out.printf("%-4d | %-50s | %-30s | %-30s | %-9b\n", rs.getInt(1), s,
							rs.getString(3), rs.getString(4), b);
				}
			}
		}
		else {
			System.exit(0);
		}


		int book_id;
		Book book = null;

		if (empty == false) {
			System.out.println("\nEnter option (c for checkout or r for reserve): ");
			String opt = sc.nextLine();
			System.out.println("Enter book id to checkout/reserve: ");
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
					System.out.println("You have reached the reservation limit! \n"
							+ "Cannot reserve.");
				} else {
					// check for max number of book checkout
					reserveBook(cur_student, book);
				}
			}
		}
	}

	public static void returnBook(Scanner in) throws SQLException {
		// Put code in here 
		int book_id;
		System.out.println("Enter book id for return: ");
		book_id = in.nextInt();
		in.nextLine();
		Set<Reservation> reservations = bookHasReservation(book_id);
		boolean reserved = (reservations.size() == 0) ? false : true;
		String cur_date;
		
		CheckoutHistory return_hist = getCheckoutHistReturn(cur_student_id, book_id);
		
		cur_date = simpleDateFormat.format(new Date());
		return_hist.setReturnDate(cur_date); // set the return date
		checkoutDao.update(return_hist);
		
		// if someone reserved the book, automatically checkout for that person 
		if (reserved) {
			System.out.println("reserved ");
			Reservation next_resv = (Reservation)reservations.toArray()[0];
			Student resv_student = studentDao.getById(next_resv.getStudentId());
			Level student_level = levelDao.getById(resv_student.getGradLevel());
			String due_date = calculateDueDate(student_level);
			// checkout the book for the next reservation
			CheckoutHistory new_checkout_hist = new CheckoutHistory(null, book_id,resv_student.getStudentId(), 0,
					cur_date, null,  due_date);
			checkoutDao.insert(new_checkout_hist);
	
			// remove the reservation 
			reservationDao.delete(next_resv);
			System.out.println("\nBook was returned successfully!");
		} else {
			// update book availability
			Book book = bookDao.getById(book_id);
			book.setAvailability(true);
			bookDao.update(book);

			Student cur_student  = studentDao.getById(cur_student_id);
			int num_book_checkout = cur_student.getBooksCheckedOut();
			cur_student.setBooksCheckedOut(num_book_checkout-1);
			studentDao.update(cur_student);
			System.out.println("\nBook was returned successfully!");
		}

		// displayCheckedOutBooks();
	}

	public static void renewBook(Scanner in) throws  SQLException, ParseException{
		System.out.println("Enter book id");
		int book_id = in.nextInt();
		in.nextLine();

		Student student = studentDao.getById(cur_student_id);
		int level = student.getGradLevel();
		int time_duration;
		if (level == 1) {
			time_duration = 7;
		}
		else {
			time_duration = 14;
		}

		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;





		try {
			preparedStatement = CheckoutHistoryDaoImpl.conn.prepareStatement(
					"SELECT entry_id FROM CheckoutHistories WHERE book_id = ? AND student_id = ? AND return_date IS NULL");

			preparedStatement.setInt(1, book_id);
			preparedStatement.setInt(2, cur_student_id);

			resultSet = preparedStatement.executeQuery();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}



		int val=0;

		Boolean b = resultSet.first();
		val = resultSet.getInt(1);

		CheckoutHistory checkoutHistory = checkoutDao.getById(val);

		if (checkoutHistory.getTimesRenewed() > 0) {
			System.out.println("Can't renew: renewal limit reached");
			System.exit(0);
		}

		// setting up the date format
		Calendar c = Calendar.getInstance();
		Date cur_date = simpleDateFormat.parse(checkoutHistory.getDueDate());
		String cur_date_str = simpleDateFormat.format(cur_date);

		//Setting the date to the given date
		c.setTime(cur_date);
		// add checkout duration to get the due date
		c.add(Calendar.DATE, time_duration);
		String due_date = simpleDateFormat.format(c.getTime());

		CheckoutHistory updated = new CheckoutHistory(checkoutHistory.getEntryId(),
				checkoutHistory.getBookId(), checkoutHistory.getStudentId(),
				checkoutHistory.getTimesRenewed()+1, checkoutHistory.getCheckoutDate(),
				checkoutHistory.getReturnDate(), due_date);

		checkoutDao.update(updated);
	}



	public static void viewHistory(Scanner in) throws SQLException{
		String s;
		ResultSet rs = getPreviousCheckoutHistoryByStudentId(cur_student_id);

		if (rs.next() == false) {
			System.out.println("\nYou have no previous checkout history!");
		}
		else {
			System.out.println("\nCheckout History:");
			rs.previous();

			System.out.printf("Book | %-50s | %-30s | Checkout Date | Due Date | Return Date\n", "Title", "Author");

			while(rs.next()) {
				s = rs.getString(2);
				if (s.length() > 50) {
					s = s.substring(0, 50);
				}

				System.out.printf("%-4d | %-50s | %-30s | %-13s | %-8s | %-11s\n", rs.getInt(1), s,
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
			}
		}

	}

	public static void viewMonthlyOverview(Scanner in) throws SQLException {
		ResultSet rs = getCheckoutSummary();
		String s;

		System.out.printf("%-50s | Jan | Feb | Mar | Apr | May | Jun | Jul | Aug | Sep | Oct | Nov | Dec | Year\n", "Title");
		while(rs.next()) {
			s = rs.getString(1);
			if (s.length() > 50) {
				s = s.substring(0, 50);
			}

			System.out.printf("%-50s | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %3d | %4d\n",
					s, rs.getInt(2), rs.getInt(3),
					rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
					rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),
					rs.getInt(12), rs.getInt(13),rs.getInt(14));
		}
	}

	public static void createCheckoutHisotry(Book book, int student_id) throws SQLException {
		// ------------ create checkout history -----------------
		// get the current student grad level
		Student cur_student = studentDao.getById(student_id);
		// get restriction on the student
		Level cur_student_level = levelDao.getById(cur_student.getGradLevel());

		String cur_date_str = simpleDateFormat.format(new Date());
		String due_date = calculateDueDate(cur_student_level);
		// check for book limit 
		CheckoutHistory checkout_hist = null;
		if (cur_student.getBooksCheckedOut() >= cur_student_level.getBookLimit()) {
			System.out.println("WARNING !!! You have reached the maximum number of books to check out");
		} else {
			// check for reservation on that book 
			Set<Reservation> reservations = bookHasReservation(book.getBookId());
			boolean reserved = (reservations.size() == 0) ? false : true;
			if (!book.getAvailability()) {
				System.out.println("The book is not available for checkout");
			} else if  (reserved) {
				System.out.println("The book has been reserved");
			} else {
				book.setAvailability(false);
				bookDao.update(book);
				checkout_hist = new CheckoutHistory(
						null, book.getBookId(), cur_student_id, 0, cur_date_str, null, due_date);
				checkoutDao.insert(checkout_hist);
				System.out.println("\nBook was checked out successfully! \n"
						+ "Your book is due on " + due_date);
				displayCheckedOutBooks();
			}
		}
	}
	
	public static String calculateDueDate(Level cur_student_level) {
		String due_date;
				// setting up the date format
		Calendar c = Calendar.getInstance();
		Date cur_date = new Date();
		
		//Setting the date to the given date
		c.setTime(cur_date);
		// add checkout duration to get the due date
		c.add(Calendar.DAY_OF_MONTH, cur_student_level.getTimeLimit());
		due_date = simpleDateFormat.format(c.getTime());
		
		return due_date;
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
	public static Set<Reservation> bookHasReservation(Integer book_id) {
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
		return reservations;
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
							"WHERE student_id = ? AND return_date IS NULL\n" +
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

	public static void reserveBook(Student student, Book book) throws SQLException {
		int book_limit = levelDao.getById(student.getGradLevel()).getBookLimit();
		String checkout;
		Scanner sc = new Scanner(System.in);

		if (book.getAvailability()) {
			System.out.println("\nThis book is available. Do you want to check out? (y/n)");
			checkout = sc.nextLine();
			if (checkout.equals("y")) {
				createCheckoutHisotry(book, student.getStudentId());
			}
		} else if (student.getBooksCheckedOut() != book_limit ) {
			String d = simpleDateFormat.format(new Date());
			Reservation reservation = new Reservation(null, book.getBookId(), cur_student_id, d);
			reservationDao.insert(reservation);
			System.out.println("\nReservation for book \"" + book.getTitle() + "\" is placed on "
					+ d);
		} else {
			System.out.println("\nYou have reached max book check out limit! \n"
					+ "Cannot reserve !!!");
		}
	}
	
	public static CheckoutHistory getCheckoutHistReturn(Integer student_id, Integer book_id) {
		Set<CheckoutHistory> checkout_histories = null;
		Boolean hasRsrv;
		CheckoutHistory checkout_hist = null;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM CheckoutHistories\n" + 
					"WHERE student_id=? AND book_id=? AND return_date IS NULL ORDER BY entry_id ASC LIMIT 1;");
			preparedStatement.setInt(1, student_id);
			preparedStatement.setInt(2, book_id);
			resultSet = preparedStatement.executeQuery();
			checkout_histories = unpackCheckoutHistory(resultSet);
			checkout_hist = (CheckoutHistory)checkout_histories.toArray()[0];
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
			
		return checkout_hist;
	}
	
	private static Set<CheckoutHistory> unpackCheckoutHistory(ResultSet rs) throws SQLException {
		Set<CheckoutHistory> reservations = new HashSet<CheckoutHistory>();

		while(rs.next()) {
			CheckoutHistory reservation = new CheckoutHistory(
					rs.getInt("entry_id"),
					rs.getInt("book_id"),
					rs.getInt("student_id"),
					rs.getInt("times_renewed"),
					rs.getString("checkout_date"),
					rs.getString("return_date"),
					rs.getString("due_date"));
			reservations.add(reservation);
		}
		return reservations;
	} 
}


