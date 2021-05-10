package com.example.notificationassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText email, password;
    TextView Createnewnow;
    Button LoginBtn;

    String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_e_mailAddress);
        password = findViewById(R.id.login_password);
        LoginBtn = findViewById(R.id.LoginButton);
        Createnewnow = findViewById(R.id.Create_new_now);

        getRegToken();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();

                if (emailStr.isEmpty()) {
                    email.setError("Email is Requird");
                    return;
                }

                if (passwordStr.isEmpty()) {
                    password.setError("password is Requird");
                    return;
                }

                UserModel userModel = new UserModel();
                userModel.setEmail(emailStr);
                userModel.setPassword(passwordStr);

                loginUser(userModel);

//                startActivity(new Intent(LoginActivity.this, HomePage.class));
//                finish();
            }
        });


        Createnewnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
            }
        });


    }

    private void loginUser(UserModel userModel) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", userModel.getEmail());
            jsonObject.put("password", userModel.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalHelper.showProgressDialog(this, "please wait login");
        AndroidNetworking.post("https://mcc-users-api.herokuapp.com/login")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(ResponseModel.class, new ParsedRequestListener<ResponseModel>() {
                    @Override
                    public void onResponse(ResponseModel response) {
                        if (response.status) {
                            registerNotification(userModel.getEmail(), userModel.getPassword(), fcmToken);
                        } else {
                            GlobalHelper.hideProgressDialog();
                            Toast.makeText(LoginActivity.this, "fail to Login", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        anError.printStackTrace();
                        GlobalHelper.hideProgressDialog();
                    }
                });

    }

    private void registerNotification(String email, String password, String token) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("reg_token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.put("https://mcc-users-api.herokuapp.com/add_reg_token")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(ResponseModel.class, new ParsedRequestListener<ResponseModel>() {
                    @Override
                    public void onResponse(ResponseModel response) {
                        GlobalHelper.hideProgressDialog();
                        if (response.status) {
                            Intent intent = new Intent(LoginActivity.this, HomePage.class);
                            intent.putExtra("user", response.data);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "fail to send token", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        GlobalHelper.hideProgressDialog();
                    }
                });

    }

    public void getRegToken() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    Log.e("TAG", "Failed to get token: ", task.getException());
                    return;
                }
                fcmToken = task.getResult();
                Log.e("TAG", "onComplete: " + fcmToken);
            }
        });
    }


//    private void Login(String data) {
//
//        String savedata=data;
//        String URL="https://mcc-users-api.herokuapp.com/login";
//
//        requestQueue= Volley.newRequestQueue(getApplicationContext());
//        Log.d("TAG", "requestQueue: "+requestQueue);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject objres = new JSONObject(response);
//                    Log.d("TAG", "onResponse: "+objres.toString());
//                } catch (JSONException e) {
//                    Log.d("TAG", "Server Error ");
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("TAG", "onErrorResponse: "+error);
//            }
//        })
//        {
//            @Override
//            public String getBodyContentType(){return "application/json; charset=utf-8";}
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//
//                try{
//
//                    Log.d("TAG", "savedata: "+savedata);
//                    return savedata==null?null:savedata.getBytes("utf-8");
//
//                }catch(UnsupportedEncodingException uee){
//                    return null;
//                }
//            }
//        };
//
//        requestQueue.add(stringRequest);
//    }

}