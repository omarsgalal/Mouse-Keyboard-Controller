package com.example.mousekeyboardcontroller;


import java.io.IOException;

public class AppDataManager {

    // singleton instance
    private static AppDataManager singleInstance = null;

    private CustomSocket socket;

    private AppDataManager() {

    }

    public static AppDataManager getInstance() {
        if (singleInstance == null) {
            synchronized(AppDataManager.class) {
                singleInstance = new AppDataManager();
            }
        }
        return singleInstance;
    }

    public CustomSocket connectSocket(String ip, int port) throws IOException {
        socket = new CustomSocket(ip, port);
        return socket;
    }

    public CustomSocket getSocket() {
        return socket;
    }
}
