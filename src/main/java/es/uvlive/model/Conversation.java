package es.uvlive.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.uvlive.api.GoogleInterface;
import es.uvlive.api.RetrofitFactory;
import es.uvlive.api.requests.NewMessagesOperation;
import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.response.NotificationResponse;
import es.uvlive.eda.ElasticList;
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.utils.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Conversation implements ElasticList.OnFillBufferCallback<Message> {

	private Collection<RolUV> rolUVs;
	private ElasticList<Message> messages;
	private int idConversation;
	private String name;

	public Conversation(int idConversation) {
		this.idConversation = idConversation;
		this.messages = new ElasticList<>(UVLiveModel.LIST_SIZE,UVLiveModel.BUFFER_SIZE,this);
		try {
			Collection<Message> oldMessages = new MessageDAO().getMessages(idConversation,UVLiveModel.LIST_SIZE);
			messages.addAll(oldMessages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rolUVs = new ArrayList<>();
	}
	
	public void addRolUV(RolUV rolUV) {
		rolUVs.add(rolUV);
	}

	public int getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void notifyUsersNewMessage(RolUV currentUser, Message message) {
		if(messages != null && message != null) {
			messages.add(message);
		}
		for (final RolUV rolUV: rolUVs) {
			if (!currentUser.equals(rolUV)) {
				String pushToken = rolUV.getPushToken();
				GoogleInterface googleInterface = RetrofitFactory.getGoogleInterface();
		        NotificationRequest notificationRequest = new NotificationRequest();
		        
		        notificationRequest.setData(new NewMessagesOperation("GET","MESSAGES",idConversation));
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
		                		Logger.putError(Conversation.this,e);
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

	public boolean containsTimestamp(int timestamp) {
		return messages.getFirstElement().getTimestamp() >= timestamp &&
				messages.getLastElement().getTimestamp() <= timestamp;
	}

	public int getFirstMessageTimestamp() {
		return messages.getFirstElement().getTimestamp();
	}

	public int getLastMessageTimestamp() {
		return messages.getLastElement().getTimestamp();
	}

	public List<Message> getFollowingMessages(int timestamp, int pageSize) {
		List<Message> messageList = messages.subList(0,messages.size());
		for (Message message: messageList) {
			if (message.getTimestamp() == timestamp) {
				int position = messageList.indexOf(message);
				return messageList.subList((position-pageSize)<0?0:position-pageSize,position);
			}
		}
		return new ArrayList();
	}

	public List<Message> getPreviousMessages(int timestamp, int pageSize) {
		List<Message> messageList = messages.subList(0,messages.size());
		for (Message message: messageList) {
			if (message.getTimestamp() == timestamp) {
				int position = messageList.indexOf(message);
				return messageList.subList(position+1,(position+pageSize)>messageList.size()?messageList.size():position+pageSize);
			}
		}
		return new ArrayList();
	}

	public List<Message> getLastMessages(int pageSize) {
		return messages.subList(messages.size()-pageSize<0?0:messages.size()-pageSize,messages.size());
	}

	@Override
	public void onBufferFilled(List<Message> elements) {
		try {
			new MessageDAO().saveMessages(elements);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}