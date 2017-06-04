/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseForm;

/**
 *
 * @author atraver
 */
public class SendMessagesForm extends BaseForm {
	// TODO @Non-generated Class
	
	private String idConversation;
	private String message;
	
	public String getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(String idConversation) {
		this.idConversation = idConversation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
