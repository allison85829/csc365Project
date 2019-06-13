package edu.calpoly.csc365.examples.dao1.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;

import edu.calpoly.csc365.examples.dao1.dao.CheckoutHistoryDaoImpl;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Level;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;
import edu.calpoly.csc365.examples.dao1.entity.Student;

public class TestDriver {
	public static int cur_student_id;
	public static DaoManager dm = null;
	public static Dao<Book> bookDao = null;
	public static Dao<Student> studentDao = null;
	public static Dao<Level> levelDao = null;
	public static Dao<CheckoutHistory> checkoutDao = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cur_student_id = 1;

		try {
			dm = DaoManager.getInstance().setProperties("C:\\Users\\MAKITEH\\Desktop\\School\\properties.xml");
			bookDao = dm.getBookDao();
			studentDao = dm.getStudentDao();
			levelDao = dm.getLevelDao();
			checkoutDao = dm.getCheckoutHistoryDao();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userInputDriver();
	}

	public static void userInputDriver(){
		String input = "";

		displayWelcomeMessage();
		Scanner in = new Scanner(System.in);
		getUserId(in);

		// If id <= 0, this is manager
		if (cur_student_id <= 0) {
			displayManagerMenu();

			while (!input.equals("q")) {
				displayPrompt();
				input = in.next();
				executeManagerCommand(input, in);
			}
		}

		// Else, prompt student menu
		else {
			displayCheckedOutBooks();
			displayReservedBooks();
			displayStudentMenu();

			while (!input.equals("q")) {
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

	public static void displayCheckedOutBooks(){
		// Put code in here
	}

	public static void displayReservedBooks(){
		// Put code in here
	}

	public static void executeStudentCommand(String input, Scanner in) {
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
		// Put code in here
		int book_id;
		Book book = null;

		System.out.println("Enter book id: ");
		book_id = sc.nextInt();

		book = bookDao.getById(book_id);
		System.out.println(book);

		Set<Book> books = bookDao.getAll();
		System.out.println(books);

		sc.nextLine();
		System.out.println("Enter option (c for checkout or r for reserve): ");
		String opt = sc.nextLine();
		System.out.println("Enter the book id: ");
		book_id = sc.nextInt();

		if (opt.equals("c")) {
			// update availability
			book = bookDao.getById(book_id);
			book.setAvailability(false);
			bookDao.update(book);

			book = bookDao.getById(book_id);
			System.out.println(book);

			createCheckoutHisotry(book_id, cur_student_id);

			// closing the connection
			try {
				dm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void returnBook(Scanner in){
		// Put code in here
	}

	public static void renewBook(Scanner in){
		// Put code in here
	}

	public static void viewHistory(Scanner in){
		// Put code in here
	}

	public static void viewMonthlyOverview(Scanner in){
		// Put code in here
	}

	public static void createCheckoutHisotry(int book_id, int student_id) {
		// ------------ create checkout history -----------------
		// get the current student grad level
		Student cur_student = studentDao.getById(student_id);
		// get restriction on the student
		Level cur_student_level = levelDao.getById(cur_student.getGradLevel());

		// setting up the date format
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		Date cur_date = new Date();
		String cur_date_str = simpleDateFormat.format(cur_date);

		//Setting the date to the given date
		c.setTime(cur_date);
		// add checkout duration to get the due date
		c.add(Calendar.DAY_OF_MONTH, cur_student_level.getTimeLimit());
		String due_date = simpleDateFormat.format(c.getTime());

		CheckoutHistory checkout_hist = new CheckoutHistory(
				null, book_id, cur_student_id, 0, cur_date_str, null, due_date);
		checkoutDao.insert(checkout_hist);

		System.out.println("student level " + cur_student_level);
		System.out.println("inserted hist " + checkout_hist);
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
}


