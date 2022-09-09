package com.shivamexam.testrest.retro;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "https://www.maishainfotech.com/";

    private static final Gson gson = new GsonBuilder().setLenient().create();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }



    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient(username, password))
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }



    private static OkHttpClient provideOkHttpClient(String username, String password) {

        return new OkHttpClient.Builder().
                readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .build();
    }


    public static  class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }


}