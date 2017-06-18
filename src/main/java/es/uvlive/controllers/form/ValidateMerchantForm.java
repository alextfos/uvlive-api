/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

public class ValidateMerchantForm extends BaseForm {
	
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	@Override
	public boolean isValid() {
		return ValidationUtils.validateString(userName);
	}

}
