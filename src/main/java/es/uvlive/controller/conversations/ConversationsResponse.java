/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.conversations;

import es.uvlive.controller.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user", "sessionId", "isInputTypeNumberSupported"
})
@XmlRootElement(name = "response")
public class ConversationsResponse extends BaseResponse {

    @XmlElement(name = "id")
    private String id;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String user) {
        this.id = user;
    }
}

