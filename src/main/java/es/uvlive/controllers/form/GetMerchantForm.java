
package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class GetMerchantForm extends BaseForm {
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return ValidationUtils.validateString(userName);
	}
}
