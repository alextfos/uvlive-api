/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.requests;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author alextfos
 */
public class SendPush {
    
    public void sendPush(String pushToken) {
        NotificationRequest notification = new NotificationRequest();
        notification.setTo(pushToken);
        notification.setNotification(new NotificationRequest.Notification("Title", "Notification body"));
        Call<NotificationResponse> callResponse = RetrofitFactory.getGoogleInterface().sendNotification(notification);
        callResponse.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> rspns) {
                // TODO
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable thrwbl) {
                // TODO
            }
        });
    }
}
