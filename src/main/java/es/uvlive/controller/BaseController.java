/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller;

import es.uvlive.model.UVLiveModel;

/**
 *
 * @author atraverf
 */
public class BaseController {
    protected UVLiveModel uvLiveModel = UVLiveModel.getUVLiveModel();
    protected int numUser;
    protected String typeUser;
    
    public void setNumUser(int num){
        numUser=num;
    }
    
    public void setTypeUser(String type){
        typeUser=type;
    }
}
