/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

public class ConversationResponse {
	private int idConversation;
	private String name;
	private String participant1;
	private String participant2;

	public ConversationResponse() {

	}

	public ConversationResponse(int idConversation, String name) {
		this.idConversation = idConversation;
		this.name = name;
	}

	public int getIdConversation() {
		return idConversation;
	}

	public String getName() {
		return name;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParticipant1() {
		return participant1;
	}

	public void setParticipant1(String participant1) {
		this.participant1 = participant1;
	}

	public String getParticipant2() {
		return participant2;
	}

	public void setParticipant2(String participant2) {
		this.participant2 = participant2;
	}
}
