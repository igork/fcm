package com.test.fcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.view.View;
import android.widget.Button;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.os.Build.VERSION_CODES.O;



public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity.FCM";

    static String server_key;

    static String topic = "weather";

    static String token =  null;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //awaitingDriverRequesting.dismiss(); progressive dialog

            if (intent.getExtras()!=null){
                String from = intent.getExtras().getString("from");
                String data = intent.getExtras().getString("data");
                String notification = intent.getExtras().getString("notification");

                String msg =
                        "from: " + from + "\n" +
                        "data: " + data + "\n" +
                        "notification: " + notification;

                Log.d(TAG + ".INTENT",msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        FCMController fcm = new FCMController(this);

        server_key = fcm.getServieKey();



        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

        if (getIntent().getExtras() != null) {
           String msg = "";
           for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                msg = msg + "Key: " + key + " Value: " + value + "\n";
                Log.d(TAG,msg);
               Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }

        // [END handle_data_extras]

        Button subscribeButton = findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                subscribe();
            }

        });



        final Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                // Get token
                // [START retrieve_current_token]
                if(token==null){
                    retriveToken();
                }

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                // [END retrieve_current_token]
            }

        });

        Button sendMessage = findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    new RetrieveFeedTask().execute("");
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Object> {

        private Exception exception;

        protected Object doInBackground(String... urls) {
            String response = null;
            try {
                try {
                    CallAPIbyOkHttp example = new CallAPIbyOkHttp();
                    String json = example.fcmJson(); //token);
                    Log.d(TAG, json);
                    response = example.post("https://fcm.googleapis.com/fcm/send", json,server_key);
                    System.out.println(response);
                    Log.d(TAG, response);
                } catch (Exception e){
                    e.printStackTrace();
                }

            } catch (Exception e) {
                this.exception = e;
                return e;
            } finally {
            }
            return response;
        }

        protected void onPostExecute(Object feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    public void retriveToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void subscribe(){
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);

                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                });
        // [END subscribe_topics]
    }
}
