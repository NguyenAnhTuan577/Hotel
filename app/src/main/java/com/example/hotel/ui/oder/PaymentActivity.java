package com.example.hotel.ui.oder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends AppCompatActivity {
    public static final String TAG="PaymentActivity";

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.room_type)
    TextView mRoomType;
    @BindView(R.id.unit_price)
    TextView mUnitPrice;
    @BindView(R.id.checkin)
    TextView mCheckIn;
    @BindView(R.id.checkout)
    TextView mCheckOut;
    @BindView(R.id.total_price)
    TextView mTotalPrice;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    private Order mOrder;
    private Hotel mHotel;
    private Integer mTotalDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_hotel);

        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mOrder=bundle.getParcelable(OrderActivity.ORDER);
            mHotel=bundle.getParcelable(OrderActivity.HOTEL);
            mTotalDay=bundle.getInt(OrderActivity.TOTAL_DAY);
        }

        refreshData();
    }

    private void refreshData() {
        if(mHotel!=null&&mOrder!=null){
            mName.setText(mHotel.getName());
            mRoomType.setText(mHotel.getRoomType().get(Integer.parseInt(mOrder.getIdRoomType())));
            mUnitPrice.setText(mOrder.getPurchase()/mTotalDay+" đ/đêm");
            mCheckIn.setText(mOrder.getCheckIn());
            mCheckOut.setText(mOrder.getCheckOut());
            mTotalPrice.setText(mOrder.getPurchase()+" đ");
        }
    }
}
