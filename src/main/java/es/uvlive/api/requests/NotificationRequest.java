/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.api.requests;

/**
 *
 * @author alextfos
 */
public class NotificationRequest {
    private Notification notification;
    private String to;
    private Operation data;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Operation getData() {
        return data;
    }

    public void setData(Operation data) {
        this.data = data;
    }
}
