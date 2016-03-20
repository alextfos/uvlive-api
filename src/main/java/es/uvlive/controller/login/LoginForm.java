/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.login;

/**
 *
 * @author atraverf
 */
public class LoginForm {
    private String userName;
    private String password;
    private String loginType;
    
    public String getUserName(){
        return userName;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getLoginType(){
        return loginType;
    }
    
    public void setUserName(String userName){
        this.userName=userName;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public void setLoginType(String login){
        this.loginType=login;
    }
}
