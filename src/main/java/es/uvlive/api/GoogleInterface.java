/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.api;

import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.response.NotificationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * @author alextfos
 */
public interface GoogleInterface {
    @Headers({
        "Content-Type: application/json",
        "Authorization: key=AIzaSyBfIkN65G5X2j7B3omyRgv6xIHqK-i9Jt4"
    })
    @POST("fcm/send")
    public Call<NotificationResponse> sendNotification(@Body NotificationRequest notification);
}
