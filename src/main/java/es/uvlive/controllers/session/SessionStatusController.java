/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.session;

import es.uvlive.controllers.BaseController;
import es.uvlive.controllers.BaseForm;
import es.uvlive.controllers.BaseResponse;
import es.uvlive.models.users.User;
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
 * @author alextfos
 */
@Controller
public class SessionStatusController extends BaseController {
    
    @RequestMapping(value = "/status", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse login(@RequestBody BaseForm baseForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request.getHeader("Authorization"));
        
        StatusResponse status = new StatusResponse();
        status.setStatus(user != null);
        
        return status;
    }
}
