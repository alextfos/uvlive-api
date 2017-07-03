/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class GetMessagesForm extends BaseForm {
	
	private int idConversation;
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
		return ValidationUtils.validateInt(idConversation);
	}
	
}
