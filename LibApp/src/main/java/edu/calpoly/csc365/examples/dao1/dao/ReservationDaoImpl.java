package edu.calpoly.csc365.examples.dao1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.calpoly.csc365.examples.dao1.entity.Book;
import edu.calpoly.csc365.examples.dao1.entity.Reservation;
import edu.calpoly.csc365.examples.dao1.entity.Reservation;

public class ReservationDaoImpl implements Dao<Reservation> {
	public static Connection conn;

	public ReservationDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	public Reservation getById(int resv_id) {
		Reservation reservation = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE reservation_id=?");
			preparedStatement.setInt(1, resv_id);
			resultSet = preparedStatement.executeQuery();
			Set<Reservation> reservations = unpackResultSet(resultSet);
			reservation = (Reservation)reservations.toArray()[0];
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
		return reservation;
	}
	
	public Set<Reservation> getAll() {
		Set<Reservation> reservations = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations");
			resultSet = preparedStatement.executeQuery();
			reservations = unpackResultSet(resultSet);
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
		return reservations;
	}
	
	public Boolean insert(Reservation obj) {
		return true;
	}
	
	public Boolean update(Reservation obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"UPDATE Reservations SET book_id=?, student_id=?, "
							+ "date=? WHERE reservation_id=?");
			preparedStatement.setInt(1, obj.getBookId());
			preparedStatement.setInt(2, obj.getStudentId());
			preparedStatement.setString(3, obj.getDate());
			preparedStatement.setInt(4, obj.getReservationId());
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
	
	public Boolean delete(Reservation obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"DELETE FROM Reservations WHERE reservation_id=?");
			preparedStatement.setInt(1, obj.getReservationId());
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

		  
	private Set<Reservation> unpackResultSet(ResultSet rs) throws SQLException {
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
}
