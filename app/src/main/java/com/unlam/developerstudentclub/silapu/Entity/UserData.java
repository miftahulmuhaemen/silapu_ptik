package com.unlam.developerstudentclub.silapu.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unlam.developerstudentclub.silapu.BuildConfig;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

public class UserData {

    @SerializedName("id")
    @Expose
    @Setter @Getter
    private Integer id;

    @SerializedName("nama")
    @Expose
    @Setter @Getter
    private String nama;

    @SerializedName("email")
    @Expose
    @Setter @Getter
    private String email;

    @SerializedName("password")
    @Expose
    @Setter @Getter
    private String password;

    @SerializedName("alamat")
    @Expose
    @Setter @Getter
    private String alamat;

    @SerializedName("identitas")
    @Expose
    @Setter @Getter
    private String identitas;

    @SerializedName("jk")
    @Expose
    @Setter @Getter
    private String jk;

    @SerializedName("tmptLhr")
    @Expose
    @Setter @Getter
    private String tmptLhr;

    @SerializedName("tglLhr")
    @Expose
    @Setter @Getter
    private String tglLhr;

    @SerializedName("noIdentitas")
    @Expose
    @Setter @Getter
    private String noIdentitas;

    @SerializedName("telp")
    @Expose
    @Setter @Getter
    private String telp;

    @SerializedName("active")
    @Expose
    @Setter @Getter
    private Integer active;

    @Setter @Getter
    private String filepath;

}
