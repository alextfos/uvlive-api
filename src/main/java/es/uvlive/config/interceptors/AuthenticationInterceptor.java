/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.config.interceptors;

import es.uvlive.models.SessionManager;
import es.uvlive.models.UVLiveModel;
import es.uvlive.utils.DebuggingUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author atraverf
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    
    private String endPointLogin = "login";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
        /*System.out.println("Authentication Interceptor - Url: " + request.getRequestURL());
        
        String url = request.getRequestURL().toString();
        
        
        if (url.contains(endPointLogin)){
            return true;
        } else {
            try {
                String authHeader = request.getHeader("Authorization");
                if (!StringUtils.isEmpty(authHeader)) {
                    authHeader = authHeader.substring(7);

                    String jwt = Jwts.parser().setSigningKey(SessionManager.SIGNATURE_KEY)
                            .parseClaimsJws (authHeader).getBody().getSubject();
                    if (UVLiveModel.getInstance().containsUser(jwt)) {
                        DebuggingUtils.log("Authenticated user: "+jwt);
                        return true;
                    }
                    DebuggingUtils.log("Not Authenticated User: "+jwt);
                } else {
                    DebuggingUtils.log("Not header");
                }
            } catch(SignatureException signExcept) {
                DebuggingUtils.log("Not Authenticated User");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // TODO: return a 403 Error
        //return false;
        throw new Exception();*/
    }
}
