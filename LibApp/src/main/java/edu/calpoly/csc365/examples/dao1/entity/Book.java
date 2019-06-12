package edu.calpoly.csc365.examples.dao1.entity;

public class Book {
  private Integer book_id;
  private String title;
  private String author;
  private String category;
  private boolean availability;
  
  public Book(Integer book_id, String title, 
		  String author, String category, boolean availability ) {
	this.book_id = book_id;
	this.title = title;
	this.author = author;
	this.category = category;
	this.availability  = availability ;
  }
  
  public Integer getBookId() {
    return book_id;
  }

  public void setId(Integer id) {
    this.book_id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
  
  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean getAvailability() {
    return this.availability;
  }

  public void setAvailability(boolean availability) {
    this.availability  = availability;
  }
  
  @Override
  public String toString() {
    return "book_id: " + book_id.toString() 
    + ", title: " + title
    + ", author: " + author
     + ", category: " + category 
     + ", availability: " + availability;
  }
}
