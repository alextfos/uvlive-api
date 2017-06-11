/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import es.uvlive.controllers.exceptions.TokenExpiredException;
import es.uvlive.controllers.exceptions.UnauthorizedException;
import es.uvlive.controllers.exceptions.UserDefinedException;
import es.uvlive.controllers.exceptions.UserNotDefinedException;
import es.uvlive.controllers.exceptions.WrongCredentialsException;
import es.uvlive.model.UVLiveModel;
import es.uvlive.utils.Logger;

public class BaseController {
    
	public static final int UNKNOWN_ERROR_CODE = 1;
    public static final int OK_CODE = 0;
    public static final int WRONG_CREDENTIALS_CODE = -1;
    public static final int UNAUTHORIZED_CODE = -2;
    public static final int TOKEN_EXPIRED = -3;
    public static final int USER_DEFINED = -4;
    public static final int USER_NOT_DEFINED = -5;
    
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
    	if (request != null && request.getHeader("Authorization") != null) {
    		return request.getHeader("Authorization").replaceFirst("Bearer ", "");
    	} else {
    		return "";
    	}
    }
    
    protected int getErrorCode(Exception e) {
    	System.err.println("Generic error treatment: "+e.getMessage());
    	e.printStackTrace();
    	int code = UNKNOWN_ERROR_CODE;
    	if (!StringUtils.isEmpty(e.getMessage())) {
	    	switch (e.getMessage()) {
	    		case UnauthorizedException.MESSAGE:
	    			code = UNAUTHORIZED_CODE;
	    			break;
	    		case WrongCredentialsException.MESSAGE:
	    			code = WRONG_CREDENTIALS_CODE;
	    			break;
	    		case TokenExpiredException.MESSAGE:
	    			code = TOKEN_EXPIRED;
	    			break;
	    		case UserDefinedException.MESSAGE:
	    			code = USER_DEFINED;
	    			break;
	    		case UserNotDefinedException.MESSAGE:
	    			code = USER_NOT_DEFINED;
	    			break;
	    	}
    	}
    	return code;
    }
}
