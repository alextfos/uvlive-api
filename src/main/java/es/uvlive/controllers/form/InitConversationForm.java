package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class InitConversationForm extends BaseForm {
	
	private int idUser;
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public boolean isValid() {
		return	ValidationUtils.validateInt(idUser);
	}
	
}
