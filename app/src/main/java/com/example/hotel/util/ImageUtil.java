package com.example.hotel.util;

import com.example.hotel.R;

public class ImageUtil {
    public interface ImageKey {
        public static final String WIFI_OUTSIDE="wifi_outside";
        public static final String WIFI_INSIDE="wifi_inside";
        public static final String SWIMMING_POOL="swimming_pool";
        public static final String SPA = "spa";
        public static final String PARK= "park";
        public static final String PET= "pet";
        public static final String AIR_CONDITION= "air_condition";
        public static final String RESTAURANT = "restaurant";
        public static final String BAR = "bar";
        public static final String GYM= "gym";
        // có thêm hình thì thêm zô
    }

    public static int getDrawableIdByImageKey(String key) {
        switch (key) {
            case ImageKey.WIFI_OUTSIDE: return R.drawable.ic_wifi;
            case ImageKey.WIFI_INSIDE: return  R.drawable.ic_wifi;
            case ImageKey.SWIMMING_POOL: return R.drawable.ic_pool;
            case ImageKey.SPA: return R.drawable.ic_spa;
            case ImageKey.PARK: return R.drawable.ic_local_parking;
            case ImageKey.PET: return R.drawable.ic_pets;
            case ImageKey.AIR_CONDITION: return R.drawable.ic_snow;
            case ImageKey.RESTAURANT: return R.drawable.ic_restaurant_menu;
            case ImageKey.BAR: return R.drawable.ic_local_bar;
            case ImageKey.GYM: return R.drawable.ic_gym;
            default:return R.drawable.ic_benefit;
        }
    }
}
