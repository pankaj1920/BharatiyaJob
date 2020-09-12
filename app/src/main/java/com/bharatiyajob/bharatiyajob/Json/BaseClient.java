package com.bharatiyajob.bharatiyajob.Json;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {

    final static String BaseUrl = "http://www.bharatiyajob.com/api/";
    private static Retrofit retrofgitEndPoint = null;

    public static Retrofit getBaseClient(){
        if (retrofgitEndPoint == null){
            retrofgitEndPoint = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofgitEndPoint;
    }
}
