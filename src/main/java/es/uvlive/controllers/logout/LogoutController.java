/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.logout;

import es.uvlive.controllers.BaseController;
import es.uvlive.controllers.BaseForm;
import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.session.LoginForm;
import es.uvlive.model.UVLiveModel;

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
public class LogoutController extends BaseController {
	
	protected UVLiveModel uvLiveModel = UVLiveModel.getInstance();
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse logout(@RequestBody BaseForm baseForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
    		uvLiveModel.logout(getToken(request));
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
        return baseResponse;
    }
}
