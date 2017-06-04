package es.uvlive.model;

public class Message {

	private RolUV rolUV;
	private int idMessage;
	// @TODO check in VP (Hint: int message)
	private String text;
	private String timestamp;
	
	// TODO @Non-generated
	public RolUV getRolUV() {
		return rolUV;
	}
	
	// TODO @Non-generated
	public void setRolUV(RolUV rolUV) {
		this.rolUV = rolUV;
	}
	
	// TODO @Non-generated
	public int getIdMessage() {
		return idMessage;
	}

	// TODO @Non-generated
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	// TODO @Non-generated
	public String getText() {
		return text;
	}

	// TODO @Non-generated
	public void setText(String text) {
		this.text = text;
	}

	// TODO @Non-generated
	public String getTimestamp() {
		return timestamp;
	}

	// TODO @Non-generated
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}