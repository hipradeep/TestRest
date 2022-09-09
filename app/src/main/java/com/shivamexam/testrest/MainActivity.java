package com.shivamexam.testrest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shivamexam.testrest.requests.RequestData;
import com.shivamexam.testrest.responses.ResponseData;
import com.shivamexam.testrest.retro.ApiServices;
import com.shivamexam.testrest.retro.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText et_mail;
    Button btn_send_req;
    ProgressBar progressBar;
    TextView tv_response;
    ApiServices apiServices;
    TableLayout tl_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_mail=findViewById(R.id.et_mail);
        btn_send_req=findViewById(R.id.btn_send_req);
        progressBar=findViewById(R.id.progressBar);
        tv_response=findViewById(R.id.tv_response);
        tl_table=findViewById(R.id.tl_table);

         apiServices = ServiceGenerator.createService(ApiServices.class, "interviewmaisha", "interview@maisha@12701");




        et_mail.setSelection(et_mail.getText().toString().trim().length());

        btn_send_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=et_mail.getText().toString().trim();
                //progressBar.setVisibility(View.GONE);
                tl_table.setVisibility(View.GONE);

                if (!email.isEmpty()){
                    if ( Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                        requestForResponse(email);
                    }else{
                        Toast.makeText(MainActivity.this, "Enter valid email!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    et_mail.setError("Please Enter email!");
                }
            }
        });

    }

    private void requestForResponse(String email) {
        progressBar.setVisibility(View.VISIBLE);
        RequestData requestData=new RequestData();
        requestData.setEmail(email);

        Call<ResponseData> call=apiServices.getTestService(requestData);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressBar.setVisibility(View.GONE);
                tl_table.setVisibility(View.VISIBLE);

                assert response.body() != null;
                if (!response.body().getResponse().isEmpty() && response.body().getResponse() !=null){
                    tv_response.setText(response.body().getResponse());
                }

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Log.e("TAG", t.getMessage());
            }
        });

    }
}