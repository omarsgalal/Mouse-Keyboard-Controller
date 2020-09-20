package com.example.mousekeyboardcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CustomSocket {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JSONObject jsonToSend;
    private Context context;

    public CustomSocket(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream());
    }

    public void sendString(String message) {
        output.write(message);
        output.flush();
    }

    // in this method you need to make your own thread to call the function from
    public void sendJson(JSONObject message, Context context) {
        this.sendString(message.toString());
    }

    // you can call this method from the main thread
    public void sendJsonThreaded(JSONObject message, Context context) {
        jsonToSend = message;
        this.context = context;
        new Thread(new SenderThread()).start();
    }

    public String receiveString() throws IOException {
        String message = input.readLine();
        return message;
    }

    public JSONObject receiveJSON() throws IOException, JSONException {
        JSONObject jsonResponse = new JSONObject(this.receiveString());
        return jsonResponse;
    }

    public void receiveACKThreaded() throws InterruptedException {
        Thread t = new Thread(new Runnable(){
            public void run() {
                try {
                    receiveString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();
    }

    public JSONArray receiveJSONArray() throws IOException, JSONException {
        return new JSONArray(this.receiveString());
    }

    public int getPort() {
        return socket.getPort();
    }

    public String getIP() {
        return socket.getRemoteSocketAddress().toString();
    }

    public void finalize() throws IOException {
        socket.close();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }


    class SenderThread implements Runnable{
        public void run() {
            sendJson(jsonToSend, context);
        }
    }
}
