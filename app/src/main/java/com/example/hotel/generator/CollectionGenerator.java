package com.example.hotel.generator;

import com.example.hotel.model.Hotel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectionGenerator {
    public static void generateRoomType(String newCollectionName, List<Hotel> hotels) {

        ArrayList<String> types = new ArrayList<>();
        types.add("Phòng Giường Đôi");
        types.add("Phòng Có Giường Cỡ Queen");
        types.add("Phòng Giường Đơn");
        types.add("Phòng Cao Cấp Giường Đôi");
        types.add("Phòng Có Giường Cỡ Yuumi");
        types.add("Phòng Có Giường Đôi Nhìn Ra Nuối");
        types.add("Phòng Có Giường Cỡ King");
        types.add("Phòng Suite Hạng Tổng Thống");
        types.add("Phòng Đơn Nhìn Ra Hồ Bơi");
        types.add("Phòng Executive Suite");
        types.add("Phòng Deluxe Có Giường Cỡ King");

        Random random = new Random();



        for (Hotel hotel :
                hotels) {
            hotel.addAllRoomType(types);
            for (int i = 0; i < 11; i++) {
                hotel.addRoomPrice(300000 + random.nextInt(5) * 100000 + random.nextInt(99) * 1000);
            }
        }

        for (Hotel hotel :
                hotels) {
            FirebaseFirestore.getInstance()
                    .collection(newCollectionName )
                    .document(hotel.getId())
                    .set(hotel);
        }

    }

}