/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author atraverf
 */
public class Logger {
    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_VERBOSE = "VERBOSE";
    public static final String UNDEFINED = "Undefined";
    
    private static ArrayList<LogRegister> logger = new ArrayList<>();
    
    public static void put(Object requesterObj, String message) {
        put(LEVEL_VERBOSE,requesterObj.getClass().getName(), message);
    }
    
    public static void put(String message) {
        put(LEVEL_VERBOSE, UNDEFINED, message);
    }
    
    public static void putError(Object requesterObj, String message) {
        put(LEVEL_ERROR, requesterObj.getClass().getName(), message);
    }
    
    public static void putError(String message) {
        put(LEVEL_ERROR, UNDEFINED, message);
    }
    
    /**
     * You can check this text using tail -f catalina.out on apache/logs
     * @param logText Text to print in logfile
     */
    public static void put(String level, String clazz, String message) {
        LogRegister logRegister = new LogRegister(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),level,clazz,message);
        logger.add(logRegister);
        System.out.println(logRegister);
    }
    
    public static ArrayList<LogRegister> getLogs() {
        return logger;
    }
    
    public static ArrayList<String> getStringLogs() {
        ArrayList<String> stringArray = new ArrayList<>();
        
        for (LogRegister log: logger) {
            stringArray.add(log.toString());
        }
        
        return stringArray;
    }
}
