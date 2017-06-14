/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LogListResponse", propOrder = {
        "logs"
    })
    @XmlRootElement(name = "response")
public class LogListResponse extends BaseResponse {

    @XmlElement(name = "logs")
    private ArrayList<LogResponse> log;

    public ArrayList<LogResponse> getLogs() {
        return log;
    }
    public void setLogs(ArrayList<LogResponse> log) {
        this.log = log;
    }
}