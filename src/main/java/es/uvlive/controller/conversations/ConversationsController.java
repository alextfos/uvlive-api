/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.conversations;

import es.uvlive.controller.BaseResponse;
import es.uvlive.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.model.Conversation;
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
    BaseResponse getConversations(@RequestBody ConversationsForm loginForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response)
    {
        
        Thread t = Thread.currentThread();
        String name = t.getName();
        System.out.println("Nombre del hilo:=" + name);
        
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
