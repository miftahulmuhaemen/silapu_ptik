package com.unlam.developerstudentclub.silapu.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ApiDefaultResponse {

    @SerializedName("status")
    @Expose @Setter @Getter
    private Boolean status;

    @SerializedName("msg")
    @Expose @Setter @Getter
    private String msg;

}
