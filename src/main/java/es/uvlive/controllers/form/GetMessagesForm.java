/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class GetMessagesForm extends BaseForm {
	
	private int idConversation;
	private long timestamp;
	
	public int getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setIdMessage(int timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.validateInt(idConversation);
	}
	
}
