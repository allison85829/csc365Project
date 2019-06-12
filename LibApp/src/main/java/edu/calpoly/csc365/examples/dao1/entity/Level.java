package edu.calpoly.csc365.examples.dao1.entity;

public class Level {
	private Integer level_id;
	private String level_name;
	private Integer book_limit;
	private Integer time_limit;
	private Integer renew_limit;
	
	public Level(Integer level_id, String level_name,
			Integer book_limit, Integer time_limit, Integer renew_limit) {
		this.level_id = level_id;
		this.level_name = level_name;
		this.book_limit = book_limit;
		this.time_limit = time_limit;
		this.renew_limit = renew_limit;
	}
	
	public Integer getLevelId() {
		return level_id;
	}

	public void setLevelId(Integer level_id) {
		this.level_id = level_id;
	}
	
	public String getLevelName() {
		return level_name;
	}

	public void setLevelName(String level_name) {
		this.level_name = level_name;
	}
	
	public Integer getBookLimit() {
		return book_limit;
	}

	public void setBookLimit(Integer book_limit) {
		this.book_limit = book_limit;
	}
	
	public Integer getTimeLimit() {
		return time_limit;
	}

	public void setTimeLimit(Integer time_limit) {
		this.time_limit = time_limit;
	}
	
	public Integer getRenewLimit() {
		return renew_limit;
	}

	public void setRenewLimit(Integer renew_limit) {
		this.renew_limit = renew_limit;
	}
	
	@Override
	public String toString() {
		return "level_id: " + level_id
		+ ", level_name: " + level_name
		+ ", book_limit: " + book_limit
		+ ", time_limit: " + time_limit
		+ ", renew_limit: " + renew_limit;
	}
}
