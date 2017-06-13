package es.uvlive.controllers.businessman;

import es.uvlive.controllers.BaseForm;
import es.uvlive.utils.ValidationUtils;

public class BroadcastForm extends BaseForm{
	
	// TODO @Non-generated
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
