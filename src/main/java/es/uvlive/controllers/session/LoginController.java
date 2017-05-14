/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.session;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.BaseController;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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
        String token = request.getHeader("Authorization");
        token = uvLiveModel.login(loginForm.getUserName(),loginForm.getPassword(),loginForm.getLoginType(),token);
        Logger.put(this, "Logging user "+ loginForm.getUserName() + ": " + (!StringUtils.isEmpty(token)?"logged":"not logged"));
        LoginResponse loginResponse = new LoginResponse();
        
        if (!StringUtils.isEmpty(token)) {
            loginResponse.setUser(loginForm.getUserName());
            String str = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID=" + str);
            loginResponse.setErrorCode(BaseResponse.OK);
            loginResponse.setToken(token);
        } else {
            loginResponse.setErrorCode(BaseResponse.WRONG_CREDENTIALS);
        }
        return loginResponse;
    }
}
