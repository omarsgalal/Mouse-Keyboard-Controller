package com.example.mousekeyboardcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppDataManager appDataManager;

    private TextView ipTextView;
    private TextView portTextView;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDataManager = AppDataManager.getInstance();

        ipTextView = findViewById(R.id.ipTextView);
        portTextView = findViewById(R.id.portTextView);
        connectButton = findViewById(R.id.connectButton);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ip = ipTextView.getText().toString();
                final int port = Integer.parseInt(portTextView.getText().toString());

                new Thread(new Runnable(){
                        public void run() {
                            try {
                                appDataManager.connectSocket(ip, port);
                                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                                startActivity(i);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
            }
        });

    }
}
