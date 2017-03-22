/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.logger;

import es.uvlive.controllers.BaseResponse;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LogResponse", propOrder = {
        "timeStamp","level","clazz","message"
    })
    @XmlRootElement(name = "response")
public class LogResponse extends BaseResponse {
    
    @XmlElement(name = "timeStamp")
    private String timeStamp;
    
    @XmlElement(name = "level")
    private String level;
    
    @XmlElement(name = "clazz")
    private String clazz;
    
    @XmlElement(name = "message")
    private String message;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}