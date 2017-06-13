/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseForm;
import es.uvlive.utils.ValidationUtils;

public class SendMessagesForm extends BaseForm {
	// TODO @Non-generated Class
	
	private int idConversation;
	private String message;
	
	public int getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public boolean isValid() {
		return ValidationUtils.validateInt(idConversation) || ValidationUtils.validateString(message);
	}
}
