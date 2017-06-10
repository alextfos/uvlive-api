package es.uvlive.model;

public class User {
	
	// @Non-generated
	private int userId;
	
	private String firstname;
	private String lastname;
	
	// TODO check in VP
	private String username;
	
	// @Non-generated
	public int getUserId() {
		return userId;
	}
	
	// @Non-generated
	public void setUserId(int userId) {
		this.userId = userId;
	}

	// @Non-generated
	public String getFirstname() {
		return firstname;
	}

	// @Non-generated
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	// @Non-generated
	public String getLastname() {
		return lastname;
	}

	// @Non-generated
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof User && ((User)o).userId == this.userId;
	}


	// @Non-generated
	public String getUsername() {
		return username;
	}

	// @Non-generated
	public void setUsername(String username) {
		this.username = username;
	}
}