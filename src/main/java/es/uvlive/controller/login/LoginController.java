/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.login;

import es.uvlive.controller.BaseResponse;
import es.uvlive.controller.BaseController;
import es.uvlive.utils.DebuggingUtils;
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
 * @author atraverf
 */
@Controller
public class LoginController extends BaseController {
    
    /**
     *
     * @param loginForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse login(@RequestBody LoginForm loginForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Thread t = Thread.currentThread();
        String name = t.getName();
        System.out.println("Nombre del hilo:=" + name);
        /*
        *    Reset old session and create new session
        */
        boolean login = uvLiveModel.login(loginForm.getUserName(),loginForm.getPassword(),loginForm.getLoginType());
        DebuggingUtils.log("Session ID:"+request.getSession().getId());
        DebuggingUtils.log("Is new session: "+request.getSession().isNew());
        request.getSession().invalidate();
        
        if(login){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(loginForm.getUserName());
            //response.setHeader("Set-Cookie", "controlador");
            String str = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID="+str);
            return loginResponse;
        }
        request.getSession().invalidate();
        throw new Exception();
    }
}
