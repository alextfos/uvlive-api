/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

public class BaseResponse {
    
	private static final int OK_CODE = 0;
	
    private int errorCode;
    
    public BaseResponse() {
    	errorCode = OK_CODE; // by default is 0
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
