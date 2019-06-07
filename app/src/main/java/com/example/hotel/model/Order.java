package com.example.hotel.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private  String id;


    private String checkIn;
    private String checkOut;

    private String idHotel;

    private String idRoomType;
    private String uid="";
    private Integer purchase=0;

    public String getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        this.idRoomType = idRoomType;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public Order(String id, String checkIn, String checkOut, String idHotel) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.idHotel = idHotel;
    }

    public Order() {
        this.id="";
        this.checkIn="";
        this.checkOut="";
        this.idHotel="";
        this.idRoomType="";
    }


    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(checkIn);
        dest.writeString(checkOut);
        dest.writeString(idHotel);
        dest.writeString(idRoomType);
        dest.writeInt(purchase);
    }

    protected Order(Parcel in){
        this.id=in.readString();
        this.checkIn=in.readString();
        this.checkOut=in.readString();
        this.idHotel=in.readString();
        this.idRoomType=in.readString();
        this.purchase=in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>(){

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
