/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.login;

import es.uvlive.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.json.JSONObject;
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
    BaseResponse createSession(@RequestBody LoginForm loginForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response)
    {
        //JSONObject json = new JSONObject();
        //json.put("hola", "pepito");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(loginForm.getUserName());
        return loginResponse;
    }
}
