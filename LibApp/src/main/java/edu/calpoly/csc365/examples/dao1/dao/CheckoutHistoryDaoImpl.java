package edu.calpoly.csc365.examples.dao1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.calpoly.csc365.examples.dao1.entity.CheckoutHistory;

public class CheckoutHistoryDaoImpl implements Dao<CheckoutHistory> {
	public static Connection conn;

	public CheckoutHistoryDaoImpl(Connection conn) {
		this.conn = conn;
	}

	public CheckoutHistory getById(int entry_id) {
		CheckoutHistory checkout_hist = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM CheckoutHistories WHERE entry_id=?");
			preparedStatement.setInt(1, entry_id);
			resultSet = preparedStatement.executeQuery();
			Set<CheckoutHistory> checkout_hists = unpackResultSet(resultSet);
			checkout_hist = (CheckoutHistory)checkout_hists.toArray()[0];
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
		return checkout_hist;
	}

	public Set<CheckoutHistory> getAll() {
		Set<CheckoutHistory> checkoutHistory = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM CheckoutHistories");
			resultSet = preparedStatement.executeQuery();
			checkoutHistory = unpackResultSet(resultSet);
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
		return checkoutHistory;
	}

	public Boolean insert(CheckoutHistory obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"INSERT INTO CheckoutHistories (book_id, student_id, checkout_date,  due_date," +
					"return_date, times_renewed) VALUES (?, ?, ?, ?, ?, ?)");

			preparedStatement.setInt(1, obj.getBookId());
			preparedStatement.setInt(2, obj.getStudentId());
			preparedStatement.setString(3, obj.getCheckoutDate());
			preparedStatement.setString(4, obj.getDueDate());
			preparedStatement.setString(5, obj.getReturnDate());
			preparedStatement.setInt(6, obj.getTimesRenewed());
		
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

	public Boolean update(CheckoutHistory obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"UPDATE CheckoutHistories SET book_id=?, student_id=?, "
							+ "times_renewed=?, checkout_date=?, "
							+ "return_date=?, due_date=? WHERE entry_id=?");
			preparedStatement.setInt(1, obj.getBookId());
			preparedStatement.setInt(2, obj.getStudentId());
			preparedStatement.setInt(3, obj.getTimesRenewed());
			preparedStatement.setString(4, obj.getCheckoutDate());
			preparedStatement.setString(5, obj.getReturnDate());
			preparedStatement.setString(6, obj.getDueDate());
			preparedStatement.setInt(7, obj.getEntryId());
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

	public Boolean delete(CheckoutHistory obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"DELETE FROM CheckoutHistorys WHERE reservation_id=?");
			preparedStatement.setInt(1, obj.getEntryId());
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


	private Set<CheckoutHistory> unpackResultSet(ResultSet rs) throws SQLException {
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
