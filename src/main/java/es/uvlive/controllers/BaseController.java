/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers;

import javax.servlet.http.HttpServletRequest;

import es.uvlive.exceptions.*;
import org.springframework.util.StringUtils;

import es.uvlive.model.UVLiveModel;
import es.uvlive.utils.Logger;

public class BaseController {
    
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
		return ErrorManager.getErrorCode(getClass(),e);
    }
}
