/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.conversations;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.models.Conversation;
import es.uvlive.models.users.User;
import es.uvlive.utils.Logger;
import java.util.ArrayList;

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
        User user = getUser(request.getHeader("Authorization"));
        Logger.put(user + ": " + request.getHeader("Authorization"));
        ArrayList<Conversation> array = uvLiveModel.getConversations();
        Conversation tmp = array.get(0);
        
        ConversationsListResponse conversationsResponse = new ConversationsListResponse(array);
        //ConversationsListResponse conversationsResponse = new ConversationsListResponse();
        //ConversationResponse cr = new ConversationResponse();
        //cr.setId(tmp.getIdConversation());
        //cr.setName(tmp.getName());
        //conversationsResponse.add(cr);
        return conversationsResponse;
        //return conversationsResponse;
    }
}
