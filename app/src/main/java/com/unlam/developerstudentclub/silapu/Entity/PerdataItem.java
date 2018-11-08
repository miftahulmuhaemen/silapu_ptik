package com.unlam.developerstudentclub.silapu.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PerdataItem implements Parcelable {

    @SerializedName("id")
    @Expose
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

    @SerializedName("email")
    @Expose
    @Getter @Setter
    String email;

    @SerializedName("key")
    @Expose
    @Getter @Setter
    String key;

    @SerializedName("ket")
    @Expose
    @Getter @Setter
    String ket;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.tujuan);
        dest.writeString(this.permintaan);
        dest.writeString(this.cara);
        dest.writeString(this.log);
        dest.writeString(this.identitas2);
        dest.writeString(this.noIdentitas2);
        dest.writeString(this.nama2);
        dest.writeString(this.gender2);
        dest.writeString(this.tmptlhr2);
        dest.writeString(this.tgllhr2);
        dest.writeString(this.alamat2);
        dest.writeString(this.telp2);
        dest.writeString(this.email2);
        dest.writeString(this.email);
        dest.writeString(this.key);
        dest.writeString(this.ket);
    }

    public PerdataItem() {
    }

    protected PerdataItem(Parcel in) {
        this.id = in.readLong();
        this.tujuan = in.readString();
        this.permintaan = in.readString();
        this.cara = in.readString();
        this.log = in.readString();
        this.identitas2 = in.readString();
        this.noIdentitas2 = in.readString();
        this.nama2 = in.readString();
        this.gender2 = in.readString();
        this.tmptlhr2 = in.readString();
        this.tgllhr2 = in.readString();
        this.alamat2 = in.readString();
        this.telp2 = in.readString();
        this.email2 = in.readString();
        this.email = in.readString();
        this.key = in.readString();
        this.ket = in.readString();
    }

    public static final Parcelable.Creator<PerdataItem> CREATOR = new Parcelable.Creator<PerdataItem>() {
        @Override
        public PerdataItem createFromParcel(Parcel source) {
            return new PerdataItem(source);
        }

        @Override
        public PerdataItem[] newArray(int size) {
            return new PerdataItem[size];
        }
    };
}
