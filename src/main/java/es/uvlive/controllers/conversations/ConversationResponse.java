/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.conversations;

import es.uvlive.controllers.BaseResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author atraverf
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conversation", propOrder = {
    "id", "name"
})
@XmlRootElement(name = "response")
public class ConversationResponse extends BaseResponse {

    @XmlElement(name = "id")
    private String id;
    
    @XmlElement(name = "name")
    private String name;
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setId (String user) {
        this.id = user;
    }
    
    public void setName (String name) {
        this.name=name;
    }
}
