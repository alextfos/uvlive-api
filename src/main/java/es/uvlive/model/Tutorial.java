package es.uvlive.model;

import java.util.*;

import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.response.NotificationResponse;
import es.uvlive.utils.Logger;
import es.uvlive.api.GoogleInterface;
import es.uvlive.api.RetrofitFactory;
import es.uvlive.api.requests.NotificationRequest.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tutorial {

	// TODO Check in VP
	Collection<RolUV> rolUVs;
	private Collection<Message> messages;
	private int idTutorial;
	// @Non-generated
	private String name;

	// @Non-generated
	public Tutorial() {
		this.messages = new ArrayList<Message>();
		rolUVs = new ArrayList<>();
	}
	
	// @Non-generated
	public Collection<RolUV> getRolUVs() {
		return rolUVs;
	}

	// @Non-generated
	public void setRolUVs(Collection<RolUV> rolUVs) {
		this.rolUVs = rolUVs;
	}
	
	public void addRolUV(RolUV rolUV) {
		rolUVs.add(rolUV);
	}

	// @Non-generated
	public Collection<Message> getMessages() {
		return messages;
	}

	// @Non-generated
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	// @Non-generated
	public int getIdTutorial() {
		return idTutorial;
	}

	// @Non-generated
	public void setIdTutorial(int idTutorial) {
		this.idTutorial = idTutorial;
	}

	// @Non-generated
	public String getName() {
		return name;
	}

	// @Non-generated
	public void setName(String name) {
		this.name = name;
	}
	
	// @Non-generated
	public void sendMessage(RolUV currentUser, Message message) {
		// TODO no
		if(messages != null && message != null) {
			messages.add(message);
		}
		for (final RolUV rolUV: rolUVs) {
			if (!currentUser.equals(rolUV)) {
				String pushToken = rolUV.getPushToken();
				GoogleInterface googleInterface = RetrofitFactory.getGoogleInterface();
		        NotificationRequest notificationRequest = new NotificationRequest();
		        
		        notificationRequest.setNotification(new Notification("GET","MESSAGES"));
		        notificationRequest.setTo(pushToken);
		        Call<NotificationResponse> callback = googleInterface.sendNotification(notificationRequest);
		        callback.enqueue(new Callback<NotificationResponse>() {
		            @Override
		            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> rspns) {
		                NotificationResponse response = rspns.body();
		                if (response.getFailure() > 0) {
		                	try {
		                		rolUV.removePushToken();
		                	} catch (Exception e) {
		                		e.printStackTrace();
		                		Logger.putError(Tutorial.this,e);
		                	}
		                }
		            }
	
		            @Override
		            public void onFailure(Call<NotificationResponse> call, Throwable thrwbl) {
		                System.out.println("Error: ");
		                thrwbl.printStackTrace();
		            }
		        });
			}
		}
	}
}