package es.uvlive.model;

public class Student extends RolUV {
	
	public static final String LOGIN_TYPE = "Student";
	
	// TODO @Non-generated
	private boolean blocked;

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}