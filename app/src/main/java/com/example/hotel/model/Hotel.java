package com.example.hotel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotel implements Parcelable {

    // muốn gửi một object tới activity
    //thì object đó phải là 1 Parcelable

    private  String id;

    private String name;
    private float rate;

    private String address;
    private String detail;
    private String price;

    private String describe;

    public boolean favorite;

    private ArrayList<String> images = new ArrayList<>();


    private ArrayList<String> roomType = new ArrayList<>();
    private ArrayList<Integer> roomPrice=new ArrayList<>();

    public ArrayList<String> getRoomType() {
        return roomType;
    }

    public void setRoomType(ArrayList<String> roomType) {
        this.roomType = roomType;
    }

    public void addAllRoomType(List<String> type) {
        roomType.addAll(type);
    }
    public void addAllđrice(List<Integer> price) {
        roomPrice.addAll(price);
    }

    public ArrayList<Integer> getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(ArrayList<Integer> roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Hotel addImages(List<String> data) {
        if(data!=null)
        images.addAll(data);
        return this;
    }
    public Hotel addImages(String... data) {
        images.addAll(Arrays.asList(data));
        return this;
    }

    public Hotel setImages(List<String> data) {
        images.clear();
        if(data!=null)
        images.addAll(data);
        return this;
    }

    public Hotel(String id, String name, float rate, String address, String detail, String price, String describe, boolean favorite, String mAvatar) {
        images.add(mAvatar);
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.address = address;
        this.detail = detail;
        this.price = price;
        this.describe = describe;
        this.favorite = favorite;

    }

    public Hotel(String id, String name, float rate, String address, String detail, String price, boolean favorite, String mAvatar) {
        images.add(mAvatar);
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.address = address;
        this.detail = detail;
        this.price = price;
        this.favorite = favorite;

    }
    public Hotel(String mAvatar, String name, float rate, String address, String detail, String price, boolean favorite) {
        images.add(mAvatar);
        this.name = name;
        this.rate = rate;
        this.address = address;
        this.detail = detail;
        this.price = price;
        this.favorite = favorite;
    }


    public Hotel(String mAvatar, String name, float rate, String address, String detail, String price) {
        images.add(mAvatar);
        this.name = name;
        this.rate = rate;
        this.address = address;
        this.detail = detail;
        this.price = price;
        this.favorite = false;

    }

    public String getAddress() {
        return address;
    }

    public Hotel setAddress(String mAddress) {
        this.address = mAddress;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Hotel setDetail(String mDetail) {
        this.detail = mDetail;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Hotel setPrice(String mPrice) {
        this.price = mPrice;
        return this;
    }



    public Hotel(String mAvatar, String name, float rate) {
        images.add(mAvatar);
        this.name = name;
        this.rate = rate;
        this.address = "";
        this.detail = "";
        this.price = "0 đ";
        this.favorite = false;

    }

    public Hotel() {
        images.add("");
        name ="";
        rate =0;
        this.address = "";
        this.detail = "";
        this.price = "0 đ";
        this.favorite = false;
    }

    public Hotel setAvatar(String mAvatar) {
        images.add(0,mAvatar);
        return this;
    }

    public Hotel setName(String mName) {
        this.name = mName;
        return this;
    }

    public Hotel setRate(int mRate) {
        this.rate = mRate;
        return this;
    }
    public String getId() {
        return id;
    }

    public Hotel setId(String id) {
        this.id = id;
        return this;
    }

    public String getAvatar() {
        return images.get(0);
    }

    public String getName() {
        return name;
    }

    public float getRate() {
        return rate;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public Hotel setFavorite(boolean mFavorite) {
        this.favorite = mFavorite;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public Hotel setDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(roomType);
        dest.writeList(roomPrice);
        dest.writeStringList(images);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(describe);
        dest.writeFloat(rate);
        dest.writeString(address);
        dest.writeString(detail);
        dest.writeString(price);
        dest.writeInt(favorite ? 1 : 0);
    }

    protected Hotel(Parcel in) {
        in.readStringList(this.roomType);
        in.readList(this.roomPrice,Integer.class.getClassLoader());
        in.readStringList(this.images);
        this.id = in.readString();
        this.name =  in.readString();
        this.describe=in.readString();
        this.rate = in.readFloat();
        this.address = in.readString();
        this.detail = in.readString();
        this.price = in.readString();
        this.favorite = in.readInt() == 1;
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        public Hotel createFromParcel(Parcel source) {
            return new Hotel(source);
        }

        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public List<String> getImages() {
        return images;
    }

    public void addRoomPrice(int i) {
        roomPrice.add(i);
    }
}
