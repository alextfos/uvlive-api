/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.models;

/**
 *
 * @author atraverf
 */
public class Conversation {
    private String idConversation;
    private String name;
    
    public Conversation(String idConversation,String name) {
        this.idConversation=idConversation;
        this.name = name;
    }
    
    public String getIdConversation() {
        return idConversation;
    }
    
    public String getName() {
        return name;
    }
}
