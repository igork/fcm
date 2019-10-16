package com.test.fcm;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class FCMController implements Controller{

    private String fileOfSettings = "fb_services.json";

    private String serverKey;

    Context context;
    AppCompatActivity activity;

    private String channelName = "weather";

    public FCMController(AppCompatActivity activity){
        initialize(activity);
    }

    private void initialize(AppCompatActivity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();

        String settings = getSettingsFromFile(fileOfSettings);
        if (parseSettings(settings)) {
            //initializePN();
            //initializeChannel();
        }
    }

    public String getServieKey(){
        return serverKey;
    }

    public String getChannelName(){
        return channelName;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void publish(Object o) {

    }

    private boolean parseSettings(String settings){
        if (settings!=null) {
            try {
                JSONObject jsonObject = new JSONObject(settings);

                serverKey = jsonObject.getString("serverKey");
                channelName = jsonObject.getString("channelName");

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getSettingsFromFile(String fileName){

        try {
            return getSettingsFromFile(context.getAssets().open(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getSettingsFromFile(InputStream is){

        String json;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //Log.e("data", json);
        return json;
    }
}
