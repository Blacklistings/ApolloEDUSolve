package com.example.apolloedusolve;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountRegisterData {
    public static String name = "", birthday = "", linkedin = "", password = "", specialty = "";
    public static JSONObject getJSON(){
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("birthday", birthday);
            object.put("linkedin", linkedin);
            object.put("password", password);
            object.put("specialty", specialty);
            System.out.println(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
}