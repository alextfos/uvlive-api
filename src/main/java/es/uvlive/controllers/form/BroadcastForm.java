package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class BroadcastForm extends BaseForm {

	private String broadcastMessage;

	public String getBroadcastMessage() {
		return broadcastMessage;
	}

	public void setBroadcastMessage(String broadcastMessage) {
		this.broadcastMessage = broadcastMessage;
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.validateString(broadcastMessage);
	}
	
}
