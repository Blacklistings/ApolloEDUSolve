package com.example.apolloedusolve;

import android.content.Context;
import android.os.Bundle;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private static String serverURL;
    private static CloseableHttpClient client;
    private static String username, password;
    static {
        client = HttpClients.createDefault();
    }
    public static void init(Context context) {
        if (serverURL == null) {
            serverURL = context.getResources().getString(R.string.server);
        }
        String[] data = FileSystem.readAccountInfo(context);
        if(data!=null){
            username = data[1];
            password = data[2];
        } else {
            username = "";
            password = "";
        }
    }

    public static void register(Bundle data, String password, OnReplyReceived listener){
        final ArrayList<NameValuePair> elements = new ArrayList<>();
        elements.add(new BasicNameValuePair("name", data.getString("name")));
        elements.add(new BasicNameValuePair("birthday", data.getString("birthday")));
        elements.add(new BasicNameValuePair("linkedin", data.getString("linkedin")));
        elements.add(new BasicNameValuePair("specialty", data.getString("specialty")));
        elements.add(new BasicNameValuePair("password", password));
        final HttpPost post = new HttpPost(serverURL + "register");
        post.setEntity(new UrlEncodedFormEntity(elements));
        sendRequest(post, listener);
    }

    public static void verifyLogin(String username, String password, OnReplyReceived listener){
        final ArrayList<NameValuePair> elements = new ArrayList<>();
        elements.add(new BasicNameValuePair("name", username));
        elements.add(new BasicNameValuePair("password", password));
        final HttpPost post = new HttpPost(serverURL + "login");
        post.setEntity(new UrlEncodedFormEntity(elements));
        sendRequest(post, listener);
    }

    public static void verifyLogin(OnReplyReceived listener){
        verifyLogin(username, password, listener);
    }

    public static void getUser(String username, OnReplyReceived listener){
        HttpGet get = null;
        try {
            get = new HttpGet(serverURL + "user?name="+ URLEncoder.encode(username, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendRequest(get, listener);
    }

    public static void getUser(OnReplyReceived listener){
        getUser(username, listener);
    }

    public static void sendRequest(final ClassicHttpRequest req, final OnReplyReceived listener){
        Threads.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    CloseableHttpResponse response = client.execute(req);
                    listener.onReplyReceived(response);
                } catch (IOException ex) {
                    listener.onReplyReceived(null);
                    ex.printStackTrace();
                }
            }
        });
    }

    public static boolean loggedin(){
        return !username.isEmpty();
    }

    public static void logout() {
        username = "";
        password = "";
    }

    public interface OnReplyReceived {
        void onReplyReceived(CloseableHttpResponse response);
    }
}
