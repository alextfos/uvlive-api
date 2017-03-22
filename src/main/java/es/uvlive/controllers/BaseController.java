/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers;

import es.uvlive.models.SessionManager;
import es.uvlive.models.UVLiveModel;
import es.uvlive.models.users.User;
import es.uvlive.utils.Logger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import org.springframework.util.StringUtils;

/**
 *
 * @author atraverf
 */
public class BaseController {
    protected UVLiveModel uvLiveModel = UVLiveModel.getInstance();
    
    /*
       Debug method
    */
    public void printThreadName() {
        Thread t = Thread.currentThread();
        String name = t.getName();
        Logger.put("Thread name: " + name);
    }
    
    protected User getUser(String authHeader) {
        String jwt = "";
        try {
            if (!StringUtils.isEmpty(authHeader)) {
                authHeader = authHeader.substring(7);

                jwt = Jwts.parser().setSigningKey(SessionManager.SIGNATURE_KEY)
                        .parseClaimsJws (authHeader).getBody().getSubject();
                if (!StringUtils.isEmpty(UVLiveModel.getInstance().getUser(jwt))) {
                    Logger.put(this," Authenticated user: "+jwt);
                }
            }
        } catch(SignatureException signExcept) {
            // Not logged. Nothing to do.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uvLiveModel.getUser(jwt);
    }
}
