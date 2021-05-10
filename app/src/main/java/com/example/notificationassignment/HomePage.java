package com.example.notificationassignment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        userModel = (UserModel) getIntent().getSerializableExtra("user");
        if (userModel != null) {
            System.out.println("Log first name " + userModel.getFirstName());
            System.out.println("Log second name " + userModel.getSecondName());
            System.out.println("Log email " + userModel.getEmail());
            Toast.makeText(this, "Hello " + userModel.getFirstName(), Toast.LENGTH_SHORT).show();
        }


    }


}
