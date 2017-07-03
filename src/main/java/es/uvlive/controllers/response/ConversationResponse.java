/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

public class ConversationResponse {
	private int idConversation;
	private String name;

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
}
