package es.uvlive.model;

import java.util.*;

public class Tutorial {

	// TODO Check in VP
	Collection<RolUV> rolUVs;
	private Collection<Message> messages;
	private int idTutorial;
	// @Non-generated
	private String name;

	// @Non-generated
	public Tutorial() {
		// @Non-generated
		// Required empty constructor
	}
	
	// @Non-generated
	public Collection<RolUV> getRolUVs() {
		return rolUVs;
	}

	// @Non-generated
	public void setRolUVs(Collection<RolUV> rolUVs) {
		this.rolUVs = rolUVs;
	}

	// @Non-generated
	public Collection<Message> getMessages() {
		return messages;
	}

	// @Non-generated
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	// @Non-generated
	public int getIdTutorial() {
		return idTutorial;
	}

	// @Non-generated
	public void setIdTutorial(int idTutorial) {
		this.idTutorial = idTutorial;
	}

	// @Non-generated
	public String getName() {
		return name;
	}

	// @Non-generated
	public void setName(String name) {
		this.name = name;
	}
}