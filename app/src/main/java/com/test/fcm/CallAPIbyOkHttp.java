package com.test.fcm;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallAPIbyOkHttp {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json,String serverKey) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","")
                .addHeader("Authorization",serverKey)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String fcmJson(String to) {
        return
              "{" +
                       "\"to\": \"dAb1DXZThmE:APA91bFd4frkrjLTMpb8s543Kbu-skxqUHAgG51v8J_TCSNEng-Bx8aTFNpImeuW4qbc3kG21iewe04tA6AymLlhgHRsS7GHxB2fuSkYAZ5BkEXn8lWsvDkkD0YdQNZb46BalQmSHurE\"," +
                       "\"priority\": \"high\"," +
                       "\"notification\": {\"title\": \"Notification Title\",\"body\": \"Notification Body\",\"text\": \"Notification Text\" }," +
                       "\"data\": {\"title\": \"Data Title\",\"body\": \"Data Body\",\"text\": \"Data Text\" } }";

    }

    String fcmJson() {
        return
                "{\"to\": \"/topics/weather\"," +
                "\"notification\": {\"title\": \"Notification Title\",\"body\" : \"Notification 766777777\",\"text\":  \"Notification Text\"}," +
                "\"data\":{\"title\": \"Data Title\",\"body\" : \"Data Body\",\"text\":  \"Data Text\",\"message\": \"This is a 8222 Firebase Cloud Messaging Topic Message!\"}}";
    }

    public static void main(String[] args) throws IOException {
        /*
        CallAPIbyOkHttp example = new CallAPIbyOkHttp();
        String json = example.bowlingJson("Jesse", "Jake");
        String response = example.post("http://www.roundsapp.com/post", json);
        System.out.println(response);
        */

        CallAPIbyOkHttp example = new CallAPIbyOkHttp();
        String json = example.fcmJson();
        //example.fcmJson("dAb1DXZThmE:APA91bFd4frkrjLTMpb8s543Kbu-skxqUHAgG51v8J_TCSNEng-Bx8aTFNpImeuW4qbc3kG21iewe04tA6AymLlhgHRsS7GHxB2fuSkYAZ5BkEXn8lWsvDkkD0YdQNZb46BalQmSHurE");
        String response = example.post("https://fcm.googleapis.com/fcm/send", json,MainActivity.server_key);
        System.out.println(response);


    }
}
