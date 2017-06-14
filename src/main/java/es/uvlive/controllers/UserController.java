package es.uvlive.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controllers.form.DefaultForm;
import es.uvlive.controllers.form.LoginForm;
import es.uvlive.controllers.response.BaseResponse;
import es.uvlive.controllers.response.LoginResponse;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;

@Controller
public class UserController extends BaseController {
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
       LoginResponse loginResponse = new LoginResponse();
       try {
       	if (loginForm.isValid()) {
	        	String token = uvLiveModel.login(loginForm.getUserName(),loginForm.getPassword(),loginForm.getLoginType(), loginForm.getPushToken(), getToken(request));
		        Logger.put(this, "Logging user "+ loginForm.getUserName() + ": " + (!StringUtils.isEmpty(token)?"logged":"not logged"));
		        
		        if (!StringUtils.isEmpty(token)) { // TODO If is empty?
		            loginResponse.setUser(loginForm.getUserName());
		            String str = request.getSession().getId();
		            response.setHeader("Set-Cookie", "JSESSIONID=" + str);
		            loginResponse.setToken(token);
		        }
       	} else {
       		throw new Exception();
       	}
       } catch (Exception e) {
       	loginResponse.setErrorCode(getErrorCode(e));
       }
       
       return loginResponse;
   }
   
   @RequestMapping(value = "/logout", method = RequestMethod.POST,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           headers={"Content-Type=application/json"})
   public @ResponseBody
   BaseResponse logout(@RequestBody DefaultForm defaultForm, BindingResult result,
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
