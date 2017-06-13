/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseForm;
import es.uvlive.utils.ValidationUtils;

public class GetMessagesForm extends BaseForm {
	
	// @Non-generated model
	
	private int idConversation;

	// The limit Message
	private int idMessage;
	
	public int getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.validateInt(idConversation) || ValidationUtils.validateInt(idMessage);
	}
	
}
