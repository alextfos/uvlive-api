/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.conversations;

import es.uvlive.controller.BaseResponse;
import es.uvlive.controller.BaseController;
import es.uvlive.controller.conversations.ConversationsForm;
import es.uvlive.controller.conversations.ConversationsResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controller.login.*;

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
    BaseResponse createSession(@RequestBody ConversationsForm loginForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response)
    {
        ConversationsResponse conversationsResponse = new ConversationsResponse();
        conversationsResponse.setId("Hola");
        return conversationsResponse;
        /*
        Resetear la sesion antigua y crear una nueva.
        */
        /*
        boolean login = uvLiveModel.login(loginForm.getUserName(),loginForm.getPassword(),loginForm.getLoginType());
        
        if(login){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(loginForm.getUserName());
            //response.setHeader("Set-Cookie", "controlador");
            String str = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID="+str);
            return loginResponse;
        }else{
            //TODO: Ver que hacer
            return new LoginResponse();
        }
*/
    }
}
