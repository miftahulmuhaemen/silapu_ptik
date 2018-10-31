package com.unlam.developerstudentclub.silapu.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PengaduanItem {

    @SerializedName("id")
    @Id(assignable = true)
    @Getter @Setter long id;

    @SerializedName("aduan")
    @Expose
    @Getter @Setter
    String aduan;

    @SerializedName("perihal")
    @Expose
    @Getter @Setter
    String perihal;

    @SerializedName("namaFile")
    @Expose
    @Getter @Setter
    String namaFile;

    @Getter @Setter
    String extFile;

    @SerializedName("ket")
    @Expose
    @Getter @Setter
    String ket;

    @SerializedName("log")
    @Expose
    @Getter @Setter
    String log;



}
