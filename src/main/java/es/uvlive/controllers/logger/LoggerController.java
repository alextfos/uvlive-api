/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.logger;

import es.uvlive.controllers.BaseController;
import es.uvlive.utils.LogRegister;
import es.uvlive.utils.Logger;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author atraver
 */
/**
 *
 * @author atraverf
 */
@Controller
public class LoggerController extends BaseController {
    
    private SecureRandom random = new SecureRandom();
    
    /**
     *
     * @param loginForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logger", method = RequestMethod.POST)
    public @ResponseBody LogListResponse login(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ArrayList<LogRegister> logs = Logger.getLogs();
        LogListResponse logListResponse = new LogListResponse();
        ArrayList<LogResponse> logArrayList = new ArrayList();
        
        for (LogRegister log: logs) {
            LogResponse logResponse = new LogResponse();
            logResponse.setTimeStamp(log.getTimeStamp());
            logResponse.setLevel(log.getLevel());
            logResponse.setClazz(log.getClazz());
            logResponse.setMessage(log.getMessage());
            logArrayList.add(logResponse);
        }
        logListResponse.setLogs(logArrayList);
        return logListResponse;
    }   
}
