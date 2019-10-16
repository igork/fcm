package com.test.fcm;

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

class Data {

    //@SerializedName("title")
    //@Expose
    private String title;
    //@SerializedName("body")
    //@Expose
    private String body;
    //@SerializedName("text")
    //@Expose
    private String text;
    //@SerializedName("message")
    //@Expose
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

class Notification {

    //@SerializedName("title")
    //@Expose
    private String title;
    //@SerializedName("body")
    //@Expose
    private String body;
    //@SerializedName("text")
    //@Expose
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


public class FCMessage {

    //@SerializedName("to")
    //@Expose
    private String to;
    //@SerializedName("notification")
    //@Expose
    private Notification notification;
    //@SerializedName("data")
    //@Expose
    private Data data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}



