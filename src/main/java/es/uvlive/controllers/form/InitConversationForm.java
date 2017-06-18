package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class InitConversationForm extends BaseForm {
	
	private String idUser;
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public boolean isValid() {
		return	ValidationUtils.validateString(idUser);
	}
	
}
