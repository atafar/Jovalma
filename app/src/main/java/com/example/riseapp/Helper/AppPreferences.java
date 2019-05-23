package com.example.riseapp.Helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

@SuppressLint("Registered")
public class AppPreferences extends Application {

    private static SharedPreferences settings ;
    private static SharedPreferences.Editor editor;

    //Settings
    public static SharedPreferences getSettings() {
        return settings;
    }

    public static void setSettings(SharedPreferences settings) {
        AppPreferences.settings = settings;
    }

    //Settings editor
    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    public static void setEditor(SharedPreferences.Editor editor) {
        AppPreferences.editor = editor;
    }
}
