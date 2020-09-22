package com.example.retrofitrxjava.loginV3.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponse extends ModelResponse implements Parcelable {
    @SerializedName("data")
    @Expose
    private Data data;

    protected LoginResponse(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(data, i);
    }

    @Getter
    @Setter
    public static class Data implements Parcelable {

        @SerializedName("MSSV")
        @Expose
        private String id;
        @SerializedName("hoTen")
        @Expose
        private String name;
        @SerializedName("khoa")
        @Expose
        private String khoa;
        @SerializedName("lop")
        @Expose
        private String classRoom;
        @SerializedName("trangThaiHocTap")
        @Expose
        private String status;
        private String token;
        private String password;
        private String mediumScore;

        public Data() {
        }

        public Data(Parcel in) {
            id = in.readString();
            name = in.readString();
            khoa = in.readString();
            classRoom = in.readString();
            status = in.readString();
            token = in.readString();
            password = in.readString();
            mediumScore = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(name);
            parcel.writeString(khoa);
            parcel.writeString(classRoom);
            parcel.writeString(status);
            parcel.writeString(token);
            parcel.writeString(password);
            parcel.writeString(mediumScore);
        }
    }

}
