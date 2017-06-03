/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers;

import es.uvlive.controllers.exceptions.UnauthorizedException;
import es.uvlive.controllers.exceptions.WrongCredentialsException;
import es.uvlive.model.UVLiveModel;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

public class BaseController {
    
	public static final int UNKNOWN_ERROR_CODE = 1;
    public static final int OK_CODE = 0;
    public static final int WRONG_CREDENTIALS_CODE = -1;
    public static final int UNAUTHORIZED_CODE = -2;
	
    public static final Key SIGNATURE_KEY =  MacProvider.generateKey();
    
    protected UVLiveModel uvLiveModel = UVLiveModel.getInstance();
    
    /*
       Debug method
    */
    public void printThreadName() {
        Thread t = Thread.currentThread();
        String name = t.getName();
        Logger.put("Thread name: " + name);
    }
    
    protected String getToken( HttpServletRequest request) {
    	if (request != null) {
    		return request.getHeader("Authorization").replaceFirst("Bearer ", "");
    	} else {
    		return "";
    	}
    }
    
    protected int getErrorCode(Exception e) {
    	int code;
    	switch (e.getMessage()) {
    		case UnauthorizedException.MESSAGE:
    			code = UNAUTHORIZED_CODE;
    			break;
    		case WrongCredentialsException.MESSAGE:
    			code = WRONG_CREDENTIALS_CODE;
    			break;
    		default:
    			code = UNKNOWN_ERROR_CODE;
    			break;
    	}
    	return code;
    }
}
