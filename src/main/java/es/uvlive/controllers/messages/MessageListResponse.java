/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.model.Message;

/**
 *
 * @author atraver
 */
public class MessageListResponse extends BaseResponse {
    
	@XmlElement(name = "messages")
	Collection<MessageResponse> messages;
	
	public MessageListResponse() {
		messages = new ArrayList();
	}

	public Collection<MessageResponse> getMessages() {
		return messages;
	}

	public void setMessages(Collection<MessageResponse> messages) {
		this.messages = messages;
	}
	
}
