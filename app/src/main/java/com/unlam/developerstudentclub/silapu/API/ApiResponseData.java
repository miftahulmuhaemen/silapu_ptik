package com.unlam.developerstudentclub.silapu.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ApiResponseData<T> {

    @SerializedName("status")
    @Expose
    @Setter
    @Getter
    private Boolean status;

    @SerializedName("msg")
    @Expose @Setter @Getter
    private String msg;

    @SerializedName("count")
    @Expose @Setter @Getter
    private int count;

    @SerializedName("data")
    @Expose @Setter @Getter
    private List<T> data;

}
