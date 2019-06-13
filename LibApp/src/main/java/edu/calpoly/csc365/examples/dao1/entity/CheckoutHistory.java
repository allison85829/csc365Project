package edu.calpoly.csc365.examples.dao1.entity;

public class CheckoutHistory {
	private Integer entry_id;
	private Integer book_id;
	private Integer student_id;
	private Integer times_renewed;
	private String checkout_date;
	private String return_date;
	private String due_date;
	
	public CheckoutHistory(Integer entry_id, Integer book_id, 
			Integer student_id, Integer times_renewed,
			String checkout_date, String return_date, String due_date ) {
		this.entry_id = entry_id;
		this.book_id = book_id;
		this.student_id = student_id;
		this.times_renewed = times_renewed;
		this.checkout_date  = checkout_date;
		this.return_date  = return_date;
		this.due_date  = due_date;
	}

	public CheckoutHistory(Integer book_id,
						   Integer student_id, Integer times_renewed,
						   String checkout_date, String return_date, String due_date ) {
		this.entry_id = entry_id;
		this.book_id = book_id;
		this.student_id = student_id;
		this.times_renewed = times_renewed;
		this.checkout_date  = checkout_date;
		this.return_date  = return_date;
		this.due_date  = due_date;
	}
	
	public Integer getEntryId() {
		return this.entry_id;
	}
	
	public void setEntryId(Integer entry_id) {
		this.entry_id = entry_id;
	}
	
	public Integer getBookId() {
		return this.book_id;
	}
	
	public void setBookId(Integer book_id) {
		this.book_id = book_id;
	}
	
	public Integer getStudentId() {
		return this.student_id;
	}
	
	public void setStudentId(Integer student_id) {
		this.student_id = student_id;
	}
	
	public Integer getTimesRenewed() {
		return this.times_renewed;
	}
	
	public void setTimesRenewed(Integer times_renewed) {
		this.times_renewed = times_renewed;
	}
	
	public String getCheckoutDate() {
		return this.checkout_date;
	}
	
	public void setTimesRenewed(String checkout_date) {
		this.checkout_date = checkout_date;
	}
	
	public String getReturnDate() {
		return this.return_date;
	}
	
	public void setReturnDate(String return_date) {
		this.return_date = return_date;
	}
	
	public String getDueDate() {
		return this.due_date;
	}
	
	public void setDueDate(String due_date) {
		this.due_date = due_date;
	}
	
	@Override
	public String toString() {
		return "entry_id: " + entry_id
		+ ", book_id: " + book_id
		+ ", student_id: " + student_id
		+ ", times_renewed: " + times_renewed 
		+ ", checkout_date: " + checkout_date
		+ ", return_date: " + return_date
		+ ", due_date: " + due_date;
	}
	
}
