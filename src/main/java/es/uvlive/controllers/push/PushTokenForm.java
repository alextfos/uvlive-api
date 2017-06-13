/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.push;

import es.uvlive.controllers.BaseForm;
import es.uvlive.utils.ValidationUtils;

public class PushTokenForm extends BaseForm {

	private String pushToken;

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.validateString(pushToken);
	}
}
