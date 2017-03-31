/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseResponse;
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
public class MessagesController {
    
    @RequestMapping(value = "/messages", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse getMessages(@RequestBody GetMessagesForm getMesagesForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return new MessageListResponse();
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
        return new BaseResponse();
    }
    
}
