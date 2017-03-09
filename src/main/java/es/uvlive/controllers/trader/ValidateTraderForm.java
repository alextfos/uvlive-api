/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.trader;

import es.uvlive.controllers.BaseForm;

/**
 *
 * @author alextfos
 */
public class ValidateTraderForm extends BaseForm {
    private static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        ValidateTraderForm.userName = userName;
    }
    
    
}
