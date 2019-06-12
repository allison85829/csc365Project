package edu.calpoly.csc365.examples.dao1.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;

import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Level;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;
import edu.calpoly.csc365.examples.dao1.entity.Student;

public class TestDriver {
	public static int cur_student_id;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int book_id;
		DaoManager dm = null;
		Dao<Book> bookDao = null;
		Dao<Student> studentDao = null;
		Dao<Level> levelDao = null;
		Dao<CheckoutHistory> checkoutDao = null;
		Book book = null;
		cur_student_id = 1;
		int num_checkout_entr = 9;
		int times_renewed = 0;

		
		try {
			dm = DaoManager.getInstance().setProperties("properties.xml");
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
			
			// ------------ create checkout history ----------------- 
			// get the current student grad level 
			Student cur_student = studentDao.getById(cur_student_id);
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
			
			num_checkout_entr++;
			CheckoutHistory checkout_hist = new CheckoutHistory(
					null, book_id, cur_student_id, 0, cur_date_str, null, due_date);
			checkoutDao.insert(checkout_hist);
			
			System.out.println("student level " + cur_student_level);
			System.out.println("inserted hist " + checkout_hist);
			
			// closing the connection
			try {
				dm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
}
