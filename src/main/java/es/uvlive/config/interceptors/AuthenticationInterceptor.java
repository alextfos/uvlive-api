/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author atraverf
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter{
    
    private String endPointLogin="login";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre-handle "+handler.getClass());
        System.out.println(request.getRequestURL());
        
        String url = request.getRequestURL().toString();
        if(url.contains(endPointLogin) || !request.getSession().isNew()){
            return true;
        } 
        throw new Exception();
    }
}
