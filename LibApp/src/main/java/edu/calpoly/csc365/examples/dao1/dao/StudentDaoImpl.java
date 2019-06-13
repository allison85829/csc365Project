package edu.calpoly.csc365.examples.dao1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.calpoly.csc365.examples.dao1.entity.Student;

public class StudentDaoImpl implements Dao<Student> {
	private Connection conn;

	public StudentDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	public Student getById(int id) {
		Student student = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Students WHERE student_id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			Set<Student> students = unpackResultSet(resultSet);
			student = (Student)students.toArray()[0];
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
		return student;
	}

	public Set<Student> getAll() {
		Set<Student> students = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = this.conn.prepareStatement("SELECT * FROM Students");
			resultSet = preparedStatement.executeQuery();
			students = unpackResultSet(resultSet);
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
		return students;
	}

	public Boolean insert(Student obj) {
		return true;
	}


	public Boolean update(Student obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"UPDATE Students SET grad_level=?, books_checked_out=? WHERE student_id=?");
			preparedStatement.setInt(1, obj.getGradLevel());
			preparedStatement.setInt(2, obj.getBooksCheckedOut());
			preparedStatement.setInt(3, obj.getStudentId());
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

	public Boolean delete(Student obj) {
		Boolean successful = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = this.conn.prepareStatement(
					"DELETE FROM Students WHERE student_id=?");
			preparedStatement.setInt(1, obj.getStudentId());
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

	private Set<Student> unpackResultSet(ResultSet rs) throws SQLException {
		Set<Student> students = new HashSet<Student>();

		while(rs.next()) {
			Student student = new Student(
					rs.getInt("student_id"),
					rs.getInt("grad_level"), 
					rs.getInt("books_checked_out"));
			students.add(student);
		}
		return students;
	}  


	
}
