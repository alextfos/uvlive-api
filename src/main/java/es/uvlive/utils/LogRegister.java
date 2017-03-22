/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.utils;

public class LogRegister {
    private String timeStamp;
    private String level;
    private String clazz;
    private String message;

    public LogRegister(String timeStamp, String level, String clazz, String message) {
        this.timeStamp = timeStamp;
        this.level = level;
        this.clazz = clazz;
        this.message = message;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "[" + timeStamp + "][" + level + "]["+ clazz + "]: " + message;
    }
}