package edu.calpoly.csc365.examples.dao1.dao;

import edu.calpoly.csc365.examples.dao1.entity.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class BookDaoImpl implements Dao<Book> {
  private Connection conn;

  public BookDaoImpl(Connection conn) {
    this.conn = conn;
  }
  
  public Book getById(int id) {
	  Book book = null;
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  
	  try {
		  preparedStatement = this.conn.prepareStatement("SELECT * FROM Books WHERE book_id=?");
		  preparedStatement.setInt(1, id);
		  resultSet = preparedStatement.executeQuery();
		  Set<Book> books = unpackResultSet(resultSet);
		  book = (Book)books.toArray()[0];
	  } catch (SQLException e) {
		  e.printStackTrace();
	  } finally {
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
	  return book;
  }

  public Set<Book> getAll() {
    Set<Book> books = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Books");
      resultSet = preparedStatement.executeQuery();
      books = unpackResultSet(resultSet);
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
    return books;
  }
  
  public Boolean insert(Book obj) {
	  return true;
  }
  
  
  public Boolean update(Book obj) {
	  Boolean successful = false;
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  try {
		  preparedStatement = this.conn.prepareStatement(
				  "UPDATE Books SET title=?, author=?, category=?, "
				  + "availability=? WHERE book_id=?");
		  preparedStatement.setString(1, obj.getTitle());
		  preparedStatement.setString(2, obj.getAuthor());
		  preparedStatement.setString(3, obj.getCategory());
		  preparedStatement.setBoolean(4, obj.getAvailability());
		  preparedStatement.setInt(5, obj.getBookId());
		  successful = preparedStatement.execute();
		  
	  } catch (SQLException e) {
		  e.printStackTrace();
	  } finally {
		  try {
			  preparedStatement.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	  return successful;
  }
  
  public Boolean delete(Book obj) {
	  Boolean successful = false;
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  try {
		  preparedStatement = this.conn.prepareStatement(
				  "DELETE FROM Books WHERE book_id=?");
		  preparedStatement.setInt(1, obj.getBookId());
		  successful = preparedStatement.execute();
		  
	  } catch (SQLException e) {
		  e.printStackTrace();
	  } finally {
		  try {
			  preparedStatement.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	  return successful;
  } 
  
  private Set<Book> unpackResultSet(ResultSet rs) throws SQLException {
    Set<Book> books = new HashSet<Book>();

    while(rs.next()) {
    	Book book = new Book(
        rs.getInt("book_id"),
        rs.getString("title"),
        rs.getString("author"),
        rs.getString("category"),
        rs.getBoolean("availability"));
    	books.add(book);
    }
    return books;
  }  
 
}
