/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.trader;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.session.LoginForm;
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
public class ExistsUserNameController {
    
    
    @RequestMapping(value = "/validate_user_name", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse validateUserName(@RequestBody ValidateTraderForm traderForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return new ValidateTraderResponse();
    }
}
