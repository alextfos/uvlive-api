/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.conversations;

import es.uvlive.controllers.BaseForm;

/**
 *
 * @author atraverf
 */
public class ConversationsForm extends BaseForm {
    private String id;
    
    public void setId(String id) {
        this.id=id;
    }
    
    public String getId() {
        return id;
    }
}
