package edu.calpoly.csc365.examples.dao1.entity;

public class Reservation {
	private Integer reservation_id;
	private Integer book_id;
	private Integer student_id;
	private String date;
	
	public Reservation(Integer reservation_id, Integer book_id,
			Integer student_id, String date) {
		this.reservation_id = reservation_id;
		this.book_id = book_id;
		this.student_id = student_id;
		this.date = date;
	}

	public Integer getReservationId() {
		return reservation_id;
	}

	public void setReservationId(Integer reservation_id) {
		this.reservation_id = reservation_id;
	}
	
	public Integer getBookId() {
		return book_id;
	}

	public void setId(Integer id) {
		this.book_id = id;
	}
	
	public Integer getStudentId() {
		return this.student_id;
	}
	
	public void setStudentId(Integer student_id) {
		this.student_id = student_id;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "reservation_id: " + reservation_id
		+ ", book_id: " + book_id
		+ ", student_id: " + student_id
		+ ", times_renewed: " + date;
	}
}
