/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.conversations;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.BaseController;
import es.uvlive.model.RolUV;
import es.uvlive.model.Tutorial;
import es.uvlive.model.User;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author atraverf
 */
@Controller
public class ConversationsController extends BaseController {
    
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
	    		conversationResponse.setId(String.valueOf(tutorial.getIdTutorial()));
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
    
}
