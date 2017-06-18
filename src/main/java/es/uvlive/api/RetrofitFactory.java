/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.api;

import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.requests.NotificationRequest.Notification;
import es.uvlive.api.response.NotificationResponse;
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
}
