package com.example.hotel.model;



public class Order {
    private  String id;


    private String checkIn;
    private String checkOut;

    private String idHotel;

    private String idRoomType;

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
}
