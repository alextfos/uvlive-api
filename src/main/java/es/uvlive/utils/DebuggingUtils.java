/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.utils;

/**
 *
 * @author atraverf
 */
public class DebuggingUtils {
    
    public static String LOGGER = "";
    
    public static void log (Object requesterObj, String logText) {
        log(requesterObj.getClass().getName()+" : "+logText);
    }
    
    /**
     * You can check this text using tail -f catalina.out on apache/logs
     * @param logText Text to print in logfile
     */
    public static void log(String logText) {
        LOGGER+=logText+"\n";
        System.out.println(logText);
    }
}
