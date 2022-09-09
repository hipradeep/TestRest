package com.shivamexam.testrest.retro;



import com.shivamexam.testrest.requests.RequestData;
import com.shivamexam.testrest.responses.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiServices {


    @POST("interview/fetchdata.php")
    Call<ResponseData> getTestService(@Body RequestData requestObject);
}
