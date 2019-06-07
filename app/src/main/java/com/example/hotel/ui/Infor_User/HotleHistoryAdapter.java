package com.example.hotel.ui.Infor_User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel.R;

import java.util.List;

public class HotleHistoryAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private List<History_Booking> history_bookingList;

    public HotleHistoryAdapter(Context context, int layout, List<History_Booking> history_bookingList) {
        this.context = context;
        this.layout = layout;
        this.history_bookingList = history_bookingList;
    }

    @Override
    public int getCount() {
        return history_bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(layout,null);

        TextView txtNameHotelHistory=(TextView) convertView.findViewById(R.id.NameHotelHistory);
        TextView txtAdrHotelHistory=(TextView) convertView.findViewById(R.id.AdrHotleHistory);
        TextView txtCostHotelHistory=(TextView) convertView.findViewById(R.id.CostHostelHistory);
        TextView txtDateBookingHistory=(TextView) convertView.findViewById(R.id.DateBookingHistory);
        ImageView imgHotelHistory=(ImageView) convertView.findViewById(R.id.imgHotelHistory);

        History_Booking history_booking=history_bookingList.get(position);
        txtNameHotelHistory.setText(history_booking.getNameHotel());
        txtAdrHotelHistory.setText(history_booking.getAddressHotel());
        txtCostHotelHistory.setText(history_booking.getCostHotel());
        txtDateBookingHistory.setText(history_booking.getDateBooking());
        imgHotelHistory.setImageResource(history_booking.getImgHotel());

        return convertView;
    }
}
