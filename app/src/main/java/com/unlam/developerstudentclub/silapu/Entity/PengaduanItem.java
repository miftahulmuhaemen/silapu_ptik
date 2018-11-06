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
public class PengaduanItem implements Parcelable {

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
    String extFile = "";

    @SerializedName("ket")
    @Expose
    @Getter @Setter
    String ket;

    @SerializedName("log")
    @Expose
    @Getter @Setter
    String log;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.aduan);
        dest.writeString(this.perihal);
        dest.writeString(this.namaFile);
        dest.writeString(this.extFile);
        dest.writeString(this.ket);
        dest.writeString(this.log);
    }

    public PengaduanItem() {
    }

    protected PengaduanItem(Parcel in) {
        this.id = in.readLong();
        this.aduan = in.readString();
        this.perihal = in.readString();
        this.namaFile = in.readString();
        this.extFile = in.readString();
        this.ket = in.readString();
        this.log = in.readString();
    }

    public static final Parcelable.Creator<PengaduanItem> CREATOR = new Parcelable.Creator<PengaduanItem>() {
        @Override
        public PengaduanItem createFromParcel(Parcel source) {
            return new PengaduanItem(source);
        }

        @Override
        public PengaduanItem[] newArray(int size) {
            return new PengaduanItem[size];
        }
    };
}
