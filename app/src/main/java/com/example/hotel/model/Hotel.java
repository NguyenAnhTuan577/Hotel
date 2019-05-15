package com.example.hotel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hotel implements Parcelable {

    // m mún gửi một object tới activity
    //thì object đó phải là 1 Parcelable

    public String mAvatar;
    public String mName;
    public float mRate;

    public String mAddress;
    public String mDetail;
    public String mPrice;

    public Hotel(String mAvatar, String mName, float mRate, String mAddress, String mDetail, String mPrice, Boolean mFavorite) {
        this.mAvatar = mAvatar;
        this.mName = mName;
        this.mRate = mRate;
        this.mAddress = mAddress;
        this.mDetail = mDetail;
        this.mPrice = mPrice;
        this.mFavorite = mFavorite;
    }

    public Boolean mFavorite;

    public Hotel(String mAvatar, String mName, float mRate, String mAddress, String mDetail, String mPrice) {
        this.mAvatar = mAvatar;
        this.mName = mName;
        this.mRate = mRate;
        this.mAddress = mAddress;
        this.mDetail = mDetail;
        this.mPrice = mPrice;
        this.mFavorite = false;
    }

    public String getAddress() {
        return mAddress;
    }

    public Hotel setAddress(String mAddress) {
        this.mAddress = mAddress;
        return this;
    }

    public String getDetail() {
        return mDetail;
    }

    public Hotel setDetail(String mDetail) {
        this.mDetail = mDetail;
        return this;
    }

    public String getPrice() {
        return mPrice;
    }

    public Hotel setPrice(String mPrice) {
        this.mPrice = mPrice;
        return this;
    }



    public Hotel(String mAvatar, String mName, float mRate) {
        this.mAvatar = mAvatar;
        this.mName = mName;
        this.mRate = mRate;
        this.mAddress = "";
        this.mDetail = "";
        this.mPrice = "0 đ";
        this.mFavorite = false;

    }

    public Hotel() {
        mAvatar="";
        mName="";
        mRate=0;
        this.mAddress = "";
        this.mDetail = "";
        this.mPrice = "0 đ";
        this.mFavorite = false;
    }

    public Hotel setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
        return this;
    }

    public Hotel setName(String mName) {
        this.mName = mName;
        return this;
    }

    public Hotel setRate(int mRate) {
        this.mRate = mRate;
        return this;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getName() {
        return mName;
    }

    public float getRate() {
        return mRate;
    }

    public Boolean getFavorite() {
        return mFavorite;
    }

    public Hotel setFavorite(Boolean mFavorite) {
        this.mFavorite = mFavorite;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAvatar);
        dest.writeString(mName);
        dest.writeFloat(mRate);
        dest.writeString(mAddress);
        dest.writeString(mDetail);
        dest.writeString(mPrice);
        dest.writeInt(mFavorite ? 1 : 0);
    }

    protected Hotel(Parcel in) {
        this.mAvatar = in.readString();
        this.mName =  in.readString();
        this.mRate = in.readFloat();
        this.mAddress = in.readString();
        this.mDetail = in.readString();
        this.mPrice = in.readString();
        this.mFavorite = in.readInt() == 1;
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        public Hotel createFromParcel(Parcel source) {
            return new Hotel(source);
        }

        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
}
