package com.example.mousekeyboardcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TouchpadActivity extends AppCompatActivity {

    private AppDataManager appDataManager;
    private CustomSocket customSocket;

    private TextView touchpad;
    private Button leftButton;
    private Button rightButton;

    int X;
    int Y;

    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpad);

        appDataManager = AppDataManager.getInstance();
        customSocket = appDataManager.getSocket();

        touchpad = findViewById(R.id.touchpadID);
        leftButton = findViewById(R.id.leftButtonID);
        rightButton = findViewById(R.id.rightButtonID);

        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if (System.currentTimeMillis() - time < 150) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("type", "click");
                            customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                            customSocket.receiveACKThreaded();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    X = x;
                    Y = y;

                    time = System.currentTimeMillis();
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    int dx = x - X;
                    int dy = y - Y;
                    X = x;
                    Y = y;

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("type", "mouse_move");
                        jsonObject.put("dx", dx);
                        jsonObject.put("dy", dy);
                        customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                        customSocket.receiveACKThreaded();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                JSONObject jsonObject = new JSONObject();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    try {
                        jsonObject.put("type", "mouse_left_release");
                        customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                        customSocket.receiveACKThreaded();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    try {
                        jsonObject.put("type", "mouse_left_press");
                        customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                        customSocket.receiveACKThreaded();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });


        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                JSONObject jsonObject = new JSONObject();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    try {
                        jsonObject.put("type", "mouse_right_release");
                        customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                        customSocket.receiveACKThreaded();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    try {
                        jsonObject.put("type", "mouse_right_press");
                        customSocket.sendJsonThreaded(jsonObject, TouchpadActivity.this);
                        customSocket.receiveACKThreaded();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }
}
