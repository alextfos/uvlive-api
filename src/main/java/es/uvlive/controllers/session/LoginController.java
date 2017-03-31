/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.session;

import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.BaseController;
import es.uvlive.models.SessionManager;
import es.uvlive.utils.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author atraverf
 */
@Controller
public class LoginController extends BaseController {
    
    private SecureRandom random = new SecureRandom();
    
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
        // Reset old session and create new session
        boolean login = uvLiveModel.login(loginForm.getUserName(),loginForm.getPassword(),loginForm.getLoginType());
        Logger.put(this, loginForm.getPushToken());
        LoginResponse loginResponse = new LoginResponse();
        
        if (login) {
            String compactJws = Jwts.builder()
                    .setSubject(loginForm.getUserName()+"-"+generateId())
              .signWith(SignatureAlgorithm.HS512, SessionManager.SIGNATURE_KEY)
              .compact();
            
            loginResponse.setUser(loginForm.getUserName());
            String str = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID=" + str);
            //response.setHeader("Authorization", "Bearer " + compactJws);
            loginResponse.setErrorCode(BaseResponse.OK);
            loginResponse.setToken(compactJws);
        } else {
            loginResponse.setErrorCode(BaseResponse.WRONG_CREDENTIALS);
        }
        return loginResponse;
    }
    
    public String generateId() {
        return new BigInteger(130, random).toString(32);
    }
  
}
