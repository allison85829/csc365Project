package edu.calpoly.csc365.examples.dao1.view;

import java.util.Scanner;

import edu.calpoly.csc365.examples.dao1.entity.Book;

import edu.calpoly.csc365.examples.dao1.dao.CachedDao;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.dao.Dao;
import edu.calpoly.csc365.examples.dao1.dao.DaoManager;
import edu.calpoly.csc365.examples.dao1.entity.Customer;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class BookFrame  {
  DaoManager daoManager;
  BookTableModel bookTableModel;

  JTable table; // The table for displaying data
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DaoManager daoManager = DaoManager.getInstance().setProperties(args[0]);
//
////		CachedRowSet cachedRowSet = getContentsOfCustomerTable();
//	    CachedRowSet cachedRowSet = daoManager.getCustomerCachedDao().getCachedRowSet();
//	    //CachedDao<Customer> customerCachedDao = getCustomerDao();
//	    BookTableModel bookTableModel = new BookTableModel(cachedRowSet);
	    
	    
		
		
		
//		int book_id;
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter book id: ");
//		book_id = sc.nextInt();
//		
//		this.
//		
//		try {
//			
//			bookTableModel.insertRow(book_id);
//			bookTableModel.customerRowSet.acceptChanges(
//            daoManager.getConnection());
//        } catch (SQLException sqle) {
//          System.out.println(sqle.getMessage());
//          sqle.printStackTrace();
//        }
//		
	}

}
