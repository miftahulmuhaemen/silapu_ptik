package com.unlam.developerstudentclub.silapu.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PerdataItem {

    @SerializedName("id")
    @Id(assignable = true)
    @Getter @Setter long id;

    @SerializedName("tujuan")
    @Expose
    @Getter @Setter
    String tujuan;

    @SerializedName("permintaan")
    @Expose
    @Getter @Setter
    String permintaan;

    @SerializedName("cara")
    @Expose
    @Getter @Setter
    String cara;

    @SerializedName("ket")
    @Expose
    @Getter @Setter
    String ket;

    @SerializedName("log")
    @Expose
    @Getter @Setter
    String log;

    @SerializedName("identitas2")
    @Expose
    @Getter @Setter
    String  identitas2;

    @SerializedName("noIdentitas2")
    @Expose
    @Getter @Setter
    String  noIdentitas2;

    @SerializedName("nama2")
    @Expose
    @Getter @Setter
    String  nama2;

    @SerializedName("gender2")
    @Expose
    @Getter @Setter
    String  gender2;

    @SerializedName("tmptlhr2")
    @Expose
    @Getter @Setter
    String  tmptlhr2;

    @SerializedName("tgllhr2")
    @Expose
    @Getter @Setter
    String  tgllhr2;

    @SerializedName("alamat2")
    @Expose
    @Getter @Setter
    String  alamat2;

    @SerializedName("telp2")
    @Expose
    @Getter @Setter
    String  telp2;

    @SerializedName("email2")
    @Expose
    @Getter @Setter
    String  email2;

}
