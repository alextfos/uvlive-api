/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

public class LoginResponse extends BaseResponse {

    private String token;
    private String ownerField;
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }

	public void setOwnerField(String ownerField) {
		this.ownerField = ownerField;
	}

	public String getOwnerField() {
		return ownerField;
	}
}
