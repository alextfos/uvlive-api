/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

import javax.xml.bind.annotation.XmlElement;

public class LoginResponse extends BaseResponse {

    @XmlElement(name = "token")
    private String token;
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
}
