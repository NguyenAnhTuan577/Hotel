package com.example.hotel.Infor_User;

public class History_Booking {
    private int imgHotel;
    private String nameHotel;
    private String addressHotel;
    private String costHotel;
    private String dateBooking;

    public History_Booking(int imgHotel, String nameHotel, String addressHotel, String costHotel, String dateBooking) {
        this.imgHotel = imgHotel;
        this.nameHotel = nameHotel;
        this.addressHotel = addressHotel;
        this.costHotel = costHotel;
        this.dateBooking = dateBooking;
    }

    public int getImgHotel() {
        return imgHotel;
    }

    public void setImgHotel(int imgHotel) {
        this.imgHotel = imgHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public String getAddressHotel() {
        return addressHotel;
    }

    public void setAddressHotel(String addressHotel) {
        this.addressHotel = addressHotel;
    }

    public String getCostHotel() {
        return costHotel;
    }

    public void setCostHotel(String costHotel) {
        this.costHotel = costHotel;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }
}
