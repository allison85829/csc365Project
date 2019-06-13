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
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Level;
import edu.calpoly.csc365.examples.dao1.entity.Reservation;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;
import edu.calpoly.csc365.examples.dao1.entity.Student;

public class TestDriver {
	public static int cur_student_id;
	public static DaoManager dm = null;
	public static Connection conn = null;
	
	public static Dao<Book> bookDao = null;
	public static Dao<Student> studentDao = null;
	public static Dao<Level> levelDao = null;
	public static Dao<CheckoutHistory> checkoutDao = null;
	
	public static PreparedStatement preparedStatement = null;
	public static ResultSet resultSet = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int book_id;

		Book book = null;
		cur_student_id = 14;
	
		try {
			dm = DaoManager.getInstance().setProperties("/Users/allyquan/cs/CSC365/properties.xml");
			conn = DaoManager.getConnection();
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

		Scanner sc = new Scanner(System.in);
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
			createCheckoutHisotry(book_id, cur_student_id);
			
			// closing the connection
			try {
				dm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (opt.equals("r")) {
			// insert into reservation table 

		}
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

		// check for book limit 
		CheckoutHistory checkout_hist = null;
		if (cur_student.getBooksCheckedOut() >= cur_student_level.getBookLimit()) {
			System.out.println("WARNING !!! You have reached the maximum number of books to check out");
		} else {
			// check for reservation on that book 
			if (hasReservation(book_id)) {
				System.out.println("The book has been reserved");
			} else {
				checkout_hist = new CheckoutHistory(
						null, book_id, cur_student_id, 0, cur_date_str, null, due_date);
				checkoutDao.insert(checkout_hist);
				System.out.println("Check out successfully \n"
						+ "Your book is due on " + due_date);
			}
		}
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
	public static Boolean hasReservation(Integer book_id) {
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
	

}
