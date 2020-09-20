package com.example.mousekeyboardcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MouseActivity extends AppCompatActivity {

    private AppDataManager appDataManager;
    private CustomSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        appDataManager = AppDataManager.getInstance();
        socket = appDataManager.getSocket();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("o", "lsds");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.sendJsonThreaded(jsonObject, this);

    }
}
