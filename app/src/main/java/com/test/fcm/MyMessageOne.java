package com.test.fcm;

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

public class MyMessageOne {

    //@SerializedName("id")
    //@Expose
    private String id;

    //@SerializedName("ip")
    //@Expose
    private String ip;

   // @SerializedName("headers")
    //@Expose
    private String headers;

    //@SerializedName("time")
    //@Expose
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}