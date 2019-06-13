package edu.calpoly.csc365.examples.dao1.dao;

import edu.calpoly.csc365.examples.dao1.entity.Customer;
import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Student;
import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;
import edu.calpoly.csc365.examples.dao1.entity.Reservation;
import edu.calpoly.csc365.examples.dao1.entity.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DaoManager {

  private static String driver = null;
  private static String url = null;
  private static String user = null;
  private static String pass = null;

  private static class DAOManagerHolder {
    public static final ThreadLocal<DaoManager> instance;

    static
    {
      ThreadLocal<DaoManager> manager;
      try
      {
        manager = new ThreadLocal<DaoManager>(){
          @Override
          protected DaoManager initialValue() {
            try
            {
              return new DaoManager();
            }
            catch(Exception e)
            {
              return null;
            }
          }
        };
      }
      catch(Exception e) {
        manager = null;
      }
      instance = manager;
    }
  }

  //Private
  private static Connection conn;

  private DaoManager() throws Exception {
    try
    {
      //this.conn = ConnectionFactory.getConnection(this.driver, this.url, this.user, this.pass);
    }
    catch(Exception e) { throw e; }
  }

  public static DaoManager getInstance() {
    return DAOManagerHolder.instance.get();
  }

  public static Connection getConnection() {
//    return this.conn;
	  return conn;
  }

  public static void open() throws SQLException {
    try
    {
      if(conn == null || conn.isClosed())
        conn = ConnectionFactory.getConnection(driver, url, user, pass);
    }
    catch(SQLException e) { throw e; }
  }

  public void close() throws SQLException {
    try
    {
      if(this.conn != null && !this.conn.isClosed())
        this.conn.close();
    }
    catch(SQLException e) { throw e; }
  }

  public Object transaction(DaoCommand command){
    try{
      this.conn.setAutoCommit(false);
      Object returnValue = command.execute(this);
      this.conn.commit();
      return returnValue;
    } catch(Exception e){
      try {
        this.conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } finally {
      try {
        this.conn.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public Dao<Customer> getCustomerDao() {
    return new CustomerDaoImpl(this.conn);
  }
  
  public Dao<Book> getBookDao() {
    return new BookDaoImpl(this.conn);
  }  
  
  public Dao<Student> getStudentDao() {
	  return new StudentDaoImpl(this.conn);
  }  
  
  public Dao<CheckoutHistory> getCheckoutHistoryDao() {
	  return new CheckoutHistoryDaoImpl(this.conn);
  }

  public Dao<Reservation> getReservationDao() {
	  return new ReservationDaoImpl(this.conn);
  }
  
  public Dao<Level> getLevelDao() {
	  return new LevelDaoImpl(this.conn);
  }
  
  public CachedDao<Customer> getCustomerCachedDao() {
    return new CustomerCachedDaoImpl(this.conn);
  }

  public DaoManager setProperties(String fileName) throws IOException, SQLException {
    Properties prop = new Properties();
    FileInputStream fis = null;
    fis = new FileInputStream(fileName);
    prop.loadFromXML(fis);

    driver = prop.getProperty("driver");
    url = prop.getProperty("url");
    user = prop.getProperty("user");
    pass = prop.getProperty("pass");
    this.open();
    return this;
  }
}
