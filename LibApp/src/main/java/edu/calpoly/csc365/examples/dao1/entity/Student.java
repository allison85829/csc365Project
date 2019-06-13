package edu.calpoly.csc365.examples.dao1.entity;

public class Student {
	private Integer student_id;
	private Integer grad_level;
	private Integer books_checked_out;
	
	public Student(Integer student_id, Integer grad_level, Integer books_checked_out) {
		this.student_id = student_id;
		this.grad_level = grad_level;
		this.books_checked_out = books_checked_out;
	}
	
	public Integer getStudentId() {
		return this.student_id;
	}
	
	public void setStudentId(Integer student_id) {
		this.student_id = student_id;
	}
	
	public Integer getGradLevel() {
		return this.grad_level;
	}
	
	public void setGradLevel(Integer grad_level) {
		this.grad_level = grad_level;
	}
	
	public Integer getBooksCheckedOut() {
		return this.books_checked_out;
	}
	
	public void setBooksCheckedOut(Integer books_checked_out) {
		this.books_checked_out = books_checked_out;
	}
	
	@Override
	public String toString() {
		return "student_id: " + student_id.toString() 
		+ ", grad_level: " + grad_level.toString()
		+ ", books_checked_out: " + books_checked_out.toString();
	}
}
