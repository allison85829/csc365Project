package edu.calpoly.csc365.examples.dao1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.calpoly.csc365.examples.dao1.entity.Level;

public class LevelDaoImpl implements Dao<Level> {
	private Connection conn;

	public LevelDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	public Level getById(int id) {
		Level level = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Levels WHERE level_id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			Set<Level> levels = unpackResultSet(resultSet);
			level = (Level)levels.toArray()[0];
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
		return level;
	}

	public Set<Level> getAll() {
		Set<Level> levels = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Levels");
			resultSet = preparedStatement.executeQuery();
			levels = unpackResultSet(resultSet);
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
		return levels;
	}

	public Boolean insert(Level obj) {
		return true;
	}


	public Boolean update(Level obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"UPDATE Levels SET level_name=?, book_limit=?, "
					+ "checkout_duration=?, renew_limit=? WHERE level_id=?");
			preparedStatement.setString(1, obj.getLevelName());
			preparedStatement.setInt(2, obj.getBookLimit());
			preparedStatement.setInt(3, obj.getTimeLimit());
			preparedStatement.setInt(4, obj.getRenewLimit());
			preparedStatement.setInt(5, obj.getLevelId());
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

	public Boolean delete(Level obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"DELETE FROM Levels WHERE level_id=?");
			preparedStatement.setInt(1, obj.getLevelId());
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

	private Set<Level> unpackResultSet(ResultSet rs) throws SQLException {
		Set<Level> levels = new HashSet<Level>();

		while(rs.next()) {
			Level level = new Level(
					rs.getInt("level_id"),
					rs.getString("level_name"),
					rs.getInt("book_limit"),
					rs.getInt("checkout_duration"),
					rs.getInt("renew_limit"));
			levels.add(level);
		}
		return levels;
	}  


	
}
