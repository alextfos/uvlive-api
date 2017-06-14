/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.uvlive.model.RolUV;
import es.uvlive.model.User;

/**
 *
 * @author atraver
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conversation", propOrder = {
    "idMessage", "text", "timestamp", ""
})
@XmlRootElement(name = "response")
public class MessageResponse extends BaseResponse {
    
	@XmlElement(name = "idMessage")
	private int idMessage;
	@XmlElement(name = "text")
	private String text;
	@XmlElement(name = "timestamp")
	private String timestamp;
	//private User user;
	
	public MessageResponse() {
		//user = new User();
	}
	public int getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/*
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	*/

}
