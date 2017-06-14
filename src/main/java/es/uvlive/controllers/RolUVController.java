package es.uvlive.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controllers.form.GetMessagesForm;
import es.uvlive.controllers.form.PushTokenForm;
import es.uvlive.controllers.form.SendMessagesForm;
import es.uvlive.controllers.response.BaseResponse;
import es.uvlive.controllers.response.ConversationResponse;
import es.uvlive.controllers.response.ConversationsListResponse;
import es.uvlive.controllers.response.MessageListResponse;
import es.uvlive.controllers.response.MessageResponse;
import es.uvlive.controllers.response.UserResponse;
import es.uvlive.controllers.response.UsersListResponse;
import es.uvlive.model.Message;
import es.uvlive.model.RolUV;
import es.uvlive.model.Tutorial;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

@Controller
public class RolUVController extends BaseController {
	/**
    *
    * @param loginForm
    * @param result
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/conversations", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   BaseResponse getConversations(HttpServletRequest request, HttpServletResponse response)
   {
   	ConversationsListResponse conversationListResponse = new ConversationsListResponse();
   	
   	try {
	    	Collection<Tutorial> tutorials = uvLiveModel.getTutorials(getToken(request));
	    	for (Tutorial tutorial : tutorials) {
	    		ConversationResponse conversationResponse = new ConversationResponse();
	    		conversationResponse.setIdConversation(String.valueOf(tutorial.getIdTutorial()));
	    		conversationResponse.setName(tutorial.getName());
	    		conversationListResponse.add(conversationResponse);
	    	}
   	} catch (Exception e) {
   		conversationListResponse.setErrorCode(getErrorCode(e));
   	}
   	
   	return conversationListResponse;
   }
   
   @RequestMapping(value = "/getusers", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   UsersListResponse getUsers (HttpServletRequest request, HttpServletResponse response)
   {
   	UsersListResponse usersListResponse = new UsersListResponse();
   	
   	try {
	    	String token = getToken(request);
	    	Collection<RolUV> users = uvLiveModel.getUsers(token);
	    	Collection<UserResponse> responseUsers = new ArrayList<UserResponse>();
	    	for (User user: users) {
	    		UserResponse userResponse = new UserResponse();
	    		userResponse.setUserId(user.getUserId());
	    		userResponse.setUsername(user.getUsername());
	    		userResponse.setFirstname(user.getFirstname());
	    		userResponse.setLastname(user.getLastname());
	    		responseUsers.add(userResponse);
	    	}
	    	usersListResponse.setUsers(responseUsers);
   	} catch (Exception e) {
   		usersListResponse.setErrorCode(getErrorCode(e));
   	}
   	
   	return usersListResponse;
   }
   
   @RequestMapping(value = "/messages", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   MessageListResponse getMessages(@RequestBody GetMessagesForm getMessagesForm, BindingResult result,
           HttpServletRequest request, HttpServletResponse response) throws Exception
   {
   	MessageListResponse messageListResponse = new MessageListResponse();
   	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
   	Collection<Message> messages = new ArrayList<Message>();
   	
   	try {
   		if (getMessagesForm.isValid()) {
		    	messages = uvLiveModel.getMessages(getToken(request), getMessagesForm.getIdConversation());
		    	for (Message message : messages) {
		    		MessageResponse messageResponse = new MessageResponse();
		    		messageResponse.setIdMessage(message.getIdMessage());
		    		messageResponse.setText(message.getText());
		    		messageResponse.setTimestamp(message.getTimestamp());
		    		
		    		messageResponseList.add(messageResponse);
		    	}
		    	
		    	messageListResponse.setMessages(messageResponseList);
	    	}
   	} catch (Exception e) {
   		messageListResponse.setErrorCode(getErrorCode(e));
   	}
   	
   	return messageListResponse;
   }
   
   @RequestMapping(value = "/getPreviousMessages", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   MessageListResponse getPreviousMessages(@RequestBody GetMessagesForm getMessagesForm, BindingResult result,
           HttpServletRequest request, HttpServletResponse response) throws Exception
   {
   	MessageListResponse messageListResponse = new MessageListResponse();
   	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
   	Collection<Message> messages = new ArrayList<Message>();
   	
   	try {
   		if (getMessagesForm.isValid()) {
		    	messages = uvLiveModel.getPreviousMessages(getToken(request), getMessagesForm);
		    	for (Message message : messages) {
		    		MessageResponse messageResponse = new MessageResponse();
		    		messageResponse.setIdMessage(message.getIdMessage());
		    		messageResponse.setText(message.getText());
		    		messageResponse.setTimestamp(message.getTimestamp());
		    		
		    		messageResponseList.add(messageResponse);
		    	}
		    	
		    	messageListResponse.setMessages(messageResponseList);
   		}
   	} catch (Exception e) {
   		messageListResponse.setErrorCode(getErrorCode(e));
   	}
   	
   	return messageListResponse;
   }
   
   @RequestMapping(value = "/getFollowingMessages", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   MessageListResponse getFollowingMessages(@RequestBody GetMessagesForm getMessagesForm, BindingResult result,
           HttpServletRequest request, HttpServletResponse response) throws Exception
   {
   	MessageListResponse messageListResponse = new MessageListResponse();
   	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
   	Collection<Message> messages = new ArrayList<Message>();
   	
   	try {
   		if (getMessagesForm.isValid()) {
		    	messages = uvLiveModel.getFollowingMessages(getToken(request), getMessagesForm);
		    	for (Message message : messages) {
		    		MessageResponse messageResponse = new MessageResponse();
		    		messageResponse.setIdMessage(message.getIdMessage());
		    		messageResponse.setText(message.getText());
		    		messageResponse.setTimestamp(message.getTimestamp());
		    		
		    		messageResponseList.add(messageResponse);
		    	}
		    	
		    	messageListResponse.setMessages(messageResponseList);
   		}
   	} catch (Exception e) {
   		messageListResponse.setErrorCode(getErrorCode(e));
   	}
   	
   	return messageListResponse;
   }
   
   
   @RequestMapping(value = "/send", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody BaseResponse sendMessage(
           @RequestBody SendMessagesForm sendMessagesForm,
           BindingResult result,
           HttpServletRequest request,
           HttpServletResponse response) {
   	BaseResponse baseResponse = new BaseResponse();
   	try {
	    	// @Non-generated
	    	String token = getToken(request);
	    	if (!token.isEmpty() && sendMessagesForm.isValid()) {
	    		uvLiveModel.sendMessage(token, sendMessagesForm.getIdConversation(), sendMessagesForm.getMessage());
	    	}
   	} catch (Exception e) {
   		baseResponse.setErrorCode(getErrorCode(e));
   	}
   	
       return baseResponse;
   }
   
   @RequestMapping(value = "/update/push_token", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   BaseResponse updateToken(@RequestBody PushTokenForm pushTokenForm, BindingResult result,
           HttpServletRequest request, HttpServletResponse response) {
   	BaseResponse baseResponse = new BaseResponse();
   	
   	try {
   		if (pushTokenForm.isValid()) {
	    		uvLiveModel.updateToken(getToken(request),pushTokenForm.getPushToken());
	            Logger.put(this,"Push token updated: " + pushTokenForm.getPushToken());
   		}
   	} catch (Exception e) {
   		baseResponse.setErrorCode(getErrorCode(e));
   	}
   	
       return baseResponse;
   }
}