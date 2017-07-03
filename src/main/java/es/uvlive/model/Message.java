package es.uvlive.model;

import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicInteger;

public class Message {

	private static AtomicInteger nextId;
	
	private int idMessage;
	private RolUV rolUV;
	private String text;
	private int timestamp;
	private String owner;
	private Conversation conversation;

	public static void initMessageId(int maxId) {
		nextId = new AtomicInteger(maxId);
	}

	public Message() {
		idMessage = nextId.incrementAndGet();
	}
	
	public RolUV getRolUV() {
		return rolUV;
	}
	
	public void setRolUV(RolUV rolUV) {
		this.rolUV = rolUV;
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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Message) {
			return ((Message)o).idMessage == this.idMessage;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Conversation getConversation() {
		return conversation;
	}
}