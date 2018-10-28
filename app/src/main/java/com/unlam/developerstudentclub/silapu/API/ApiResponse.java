package com.unlam.developerstudentclub.silapu.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse<T> {

    @SerializedName("status")
    @Expose @Setter @Getter
    private Boolean status;

    @SerializedName("msg")
    @Expose @Setter @Getter
    private String msg;

    @SerializedName("account")
    @Expose @Setter @Getter
    private T account;

}
