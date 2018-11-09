package com.unlam.developerstudentclub.silapu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.unlam.developerstudentclub.silapu.Entity.UserData;

public class UserPreference {

    private String KEY_NAMA = "name";
    private String KEY_EMAIL = "email";
    private String KEY_PASSWORD = "password";
    private String KEY_ALAMAT = "alamat";
    private String KEY_IDENTITAS = "identitas";
    private String KEY_NO_IDENTITAS = "noidentitas";
    private String KEY_JENIS_KELAMIN = "jk";
    private String KEY_TEMPAT_LAHIR = "tmprLhr";
    private String KEY_TANGGAL_LAHIR = "tanggallahir";
    private String KEY_TELP = "telp";
    private String KEY_ACTIVE = "active";
    private String KEY_ID = "id";
    private String KEY_FILE = "file";

    private SharedPreferences preferences;

    public UserPreference(Context context) {
        String PREFS_NAME = "UserPreference";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setPreference(UserData data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_ID, data.getId());
        editor.putInt(KEY_ACTIVE, data.getActive());
        editor.putString(KEY_NAMA, data.getNama());
        editor.putString(KEY_EMAIL, data.getEmail());
        editor.putString(KEY_PASSWORD, data.getPassword());
        editor.putString(KEY_ALAMAT, data.getAlamat());
        editor.putString(KEY_IDENTITAS,data.getIdentitas());
        editor.putString(KEY_NO_IDENTITAS, data.getNoIdentitas());
        editor.putString(KEY_JENIS_KELAMIN, data.getJk());
        editor.putString(KEY_TEMPAT_LAHIR, data.getTmptLhr());
        editor.putString(KEY_TANGGAL_LAHIR, data.getTglLhr());
        editor.putString(KEY_TELP, data.getTelp());
        editor.putString(KEY_FILE, data.getFile());
        editor.apply();
    }

    public void clearePreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_ID, 0);
        editor.putInt(KEY_ACTIVE, 0);
        editor.putString(KEY_NAMA, null);
        editor.putString(KEY_EMAIL, null);
        editor.putString(KEY_PASSWORD, null);
        editor.putString(KEY_ALAMAT, null);
        editor.putString(KEY_IDENTITAS,null);
        editor.putString(KEY_NO_IDENTITAS, null);
        editor.putString(KEY_JENIS_KELAMIN, null);
        editor.putString(KEY_TEMPAT_LAHIR, null);
        editor.putString(KEY_TANGGAL_LAHIR, null);
        editor.putString(KEY_TELP, null);
        editor.putString(KEY_FILE, null);
        editor.apply();
    }

    public String getFile(){
        return preferences.getString(KEY_FILE, "");
    }

    public String getTelp(){
        return preferences.getString(KEY_TELP, "");
    }

    public String getTanggalLahir(){
        return preferences.getString(KEY_TANGGAL_LAHIR, "");
    }

    public String getTempatLahir(){
        return preferences.getString(KEY_TEMPAT_LAHIR, "");
    }

    public String getJenisKelamin(){
        return preferences.getString(KEY_JENIS_KELAMIN, "");
    }

    public String getNoIdentitas(){
        return preferences.getString(KEY_NO_IDENTITAS, "");
    }

    public String getIdentitas(){
        return preferences.getString(KEY_IDENTITAS, "");
    }

    public String getAlamat(){
        return preferences.getString(KEY_ALAMAT, "");
    }

    public String getPassword(){
        return preferences.getString(KEY_PASSWORD, "");
    }

    public String getEmail(){
        return preferences.getString(KEY_EMAIL, "");
    }

    public String getNama(){
        return preferences.getString(KEY_NAMA,"");
    }

    public int getID(){
        return preferences.getInt(KEY_ID,0);
    }

    public int getActive(){
        return preferences.getInt(KEY_ACTIVE,0);
    }

}
