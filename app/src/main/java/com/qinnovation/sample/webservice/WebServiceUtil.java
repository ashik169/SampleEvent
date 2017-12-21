package com.qinnovation.sample.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qinnovation on 12/17/17.
 */

public class WebServiceUtil {

    private final Retrofit retrofit;

    private static WebServiceUtil webServiceUtil;

    private WebServiceUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.122:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static WebServiceUtil getInstance() {
        if (webServiceUtil == null) {
            synchronized (WebServiceUtil.class) {
                if (webServiceUtil == null) {
                    webServiceUtil = new WebServiceUtil();
                }
            }
        }
        return webServiceUtil;
    }



    public <T> T createInstance(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
