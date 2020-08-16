package com.example.apolloedusolve;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileSystem {
    private FileSystem(){}

    public static void writeAccountInfo(String username, String password, Context context){
        writeToFile(username + "\n" + password, context, "config.txt");
    }

    public static String[] readAccountInfo(Context context){
        String in = readFromFile(context, "config.txt");
        if(in.isEmpty()){
            return null;
        } else {
            return in.split("\n");
        }
    }

    public static void clearAccountInfo(Context context){
        writeToFile("", context, "config.txt");
    }

    public static void writeToFile(String data, Context context, String path) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(path, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static String readFromFile(Context context, String path) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
