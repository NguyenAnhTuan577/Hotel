package com.example.hotel.ui.Infor_User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hotel.R;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    TextView txtBackHistory;
    ListView listView;
    ArrayList<History_Booking> arrayList;
    HotleHistoryAdapter hotleHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        txtBackHistory=(TextView) findViewById(R.id.txtBackHistory);
        Init();

        hotleHistoryAdapter=new HotleHistoryAdapter(this,R.layout.history_booking,arrayList);
        listView.setAdapter(hotleHistoryAdapter);

        txtBackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void Init()
    {
        listView=(ListView) findViewById(R.id.listViewHistory);
        arrayList=new ArrayList<>();

        arrayList.add(new History_Booking(R.drawable.hotel1,"Khách sạn Thiên Ân ","Đà Lạt","69.000.000","6/9/2019"));
        arrayList.add(new History_Booking(R.drawable.hotel2,"Khách sạn Hoàng Yến ","TP Hồ Chí Minh","96.000.000","9/9/2019"));
        arrayList.add(new History_Booking(R.drawable.hotel3,"Khách sạn Vĩnh Long ","Đà Lạt","6.000.000","16/9/2019"));
        arrayList.add(new History_Booking(R.drawable.hotel4,"Khách sạn An An ","TP Hồ Chí Minh","9.000.000","9/6/2019"));
    }
}
