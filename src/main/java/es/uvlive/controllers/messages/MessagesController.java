/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseController;
import es.uvlive.controllers.BaseResponse;
import es.uvlive.model.Message;
import es.uvlive.model.Tutorial;

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

/**
 *
 * @author atraver
 */
@Controller
public class MessagesController extends BaseController{
    
    @RequestMapping(value = "/messages", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    MessageListResponse getMessages(@RequestBody GetMessagesForm getMesagesForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	MessageListResponse messageListResponse = new MessageListResponse();
    	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
    	Collection<Message> messages = new ArrayList<Message>();
    	
    	try {
	    	messages = uvLiveModel.getMessages(getToken(request), getMesagesForm.getIdConversation());
	    	for (Message message : messages) {
	    		MessageResponse messageResponse = new MessageResponse();
	    		messageResponse.setIdMessage(message.getIdMessage());
	    		messageResponse.setText(message.getText());
	    		messageResponse.setTimestamp(message.getTimestamp());
	    		
	    		messageResponseList.add(messageResponse);
	    	}
	    	
	    	messageListResponse.setMessages(messageResponseList);
    	
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
    MessageListResponse getPreviousMessages(@RequestBody GetMessagesForm getMesagesForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	MessageListResponse messageListResponse = new MessageListResponse();
    	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
    	Collection<Message> messages = new ArrayList<Message>();
    	
    	try {
    		
	    	messages = uvLiveModel.getPreviousMessages(getToken(request), getMesagesForm);
	    	for (Message message : messages) {
	    		MessageResponse messageResponse = new MessageResponse();
	    		messageResponse.setIdMessage(message.getIdMessage());
	    		messageResponse.setText(message.getText());
	    		messageResponse.setTimestamp(message.getTimestamp());
	    		
	    		messageResponseList.add(messageResponse);
	    	}
	    	
	    	messageListResponse.setMessages(messageResponseList);
    	
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
    MessageListResponse getFollowingMessages(@RequestBody GetMessagesForm getMesagesForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	MessageListResponse messageListResponse = new MessageListResponse();
    	Collection<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
    	Collection<Message> messages = new ArrayList<Message>();
    	
    	try {
    		
	    	messages = uvLiveModel.getFollowingMessages(getToken(request), getMesagesForm);
	    	for (Message message : messages) {
	    		MessageResponse messageResponse = new MessageResponse();
	    		messageResponse.setIdMessage(message.getIdMessage());
	    		messageResponse.setText(message.getText());
	    		messageResponse.setTimestamp(message.getTimestamp());
	    		
	    		messageResponseList.add(messageResponse);
	    	}
	    	
	    	messageListResponse.setMessages(messageResponseList);
    	
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
            HttpServletResponse response) throws Exception
    {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
	    	// @Non-generated
	    	String token = request.getHeader("Authorization");
	    	uvLiveModel.sendMessage(token, Integer.parseInt(sendMessagesForm.getIdConversation()), sendMessagesForm.getMessage());
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
    	
        return baseResponse;
    }
    
}
