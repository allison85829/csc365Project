package edu.calpoly.csc365.examples.dao1.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;

public class TestDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int book_id;
		DaoManager dm = null;
		Dao<Book> bookDao = null;

		Book book = null;

		try {
			dm = DaoManager.getInstance().setProperties("properties.xml");
			bookDao = dm.getBookDao();
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
			try {
				bookDao.update(book);

				book = bookDao.getById(book_id);
				System.out.println(book);

				dm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} 
	}
}
