/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

import java.util.ArrayList;

public class LogListResponse extends BaseResponse {

    private ArrayList<LogResponse> log;

    public ArrayList<LogResponse> getLogs() {
        return log;
    }

    public void setLogs(ArrayList<LogResponse> log) {
        this.log = log;
    }
}