/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.logger;

import es.uvlive.controllers.BaseController;
import es.uvlive.controllers.BaseResponse;
import es.uvlive.controllers.session.LoginForm;
import es.uvlive.utils.DebuggingUtils;
import java.security.SecureRandom;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping(value = "/logger", method = RequestMethod.GET)
    public @ResponseBody String login(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LogResponse logResponse = new LogResponse();
        logResponse.setLog(DebuggingUtils.LOGGER);
        //return logResponse;
        return DebuggingUtils.LOGGER;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "log"
    })
    @XmlRootElement(name = "response")
    public class LogResponse extends BaseResponse {

        @XmlElement(name = "log")
        private String log;

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }


    }
    
}
