/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

public class MessageResponse extends BaseResponse {
    
	private int idMessage;
	private String text;
	private int timestamp;
	private String owner;

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

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}
}
