/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.requests;

import es.uvlive.requests.NotificationRequest.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author alextfos
 */
public class RetrofitFactory {
    public static GoogleInterface getGoogleInterface() {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
        
        return retrofit.create(GoogleInterface.class);
    }
    
    // TODO Error policy
    public static void main(String args[]) {
        GoogleInterface googleInterface = RetrofitFactory.getGoogleInterface();
        NotificationRequest notificationRequest = new NotificationRequest();
        
        notificationRequest.setNotification(new Notification("hola","funciono"));
        notificationRequest.setTo("eOParOKQAKM:APA91bF-D598Swt-L_UgOTNg1naQzT9lfq2MIiDpIGz-G4iuQfWxg0VgmFkCGBf6Yl25_vjmbk_b869dd3B3UxwNF62UaCAjT4JAa5TdJT27xSm838jA26jnFFjaBy3z0lR_4uDPXxjy");
        Call<NotificationResponse> callback = googleInterface.sendNotification(notificationRequest);
        callback.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> rspns) {
                System.out.println("Response");
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable thrwbl) {
                System.out.println("Error: ");
                thrwbl.printStackTrace();
            }
        });
    }
}
