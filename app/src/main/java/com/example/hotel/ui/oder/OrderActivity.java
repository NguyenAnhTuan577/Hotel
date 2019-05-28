package com.example.hotel.ui.oder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.image_cover.CoverActivity;
import com.example.hotel.ui.image_cover.CoverPagerAdapter;
import com.example.hotel.ui.main.bottomsheet.ChooseTypeBottomSheet;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String HOTEL="hotel";
    public static final String POSITION_ITEM="position_item";

    private CoverPagerAdapter mCoverPagerAdapter;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.rating_hotel)
    RatingBar mRate;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.cover_view_pager)
    ViewPager mCoverViewPager;

    private Hotel mHotel;

    @OnClick(R.id.back)
    void back() {
        //Toast.makeText(this,"lalalalal",Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_hotel);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mHotel=bundle.getParcelable(HOTEL);
        }

        mCoverPagerAdapter=new CoverPagerAdapter(this);
        mCoverViewPager.setAdapter(mCoverPagerAdapter);
        mCoverPagerAdapter.setClickListener(this);

        refreshData();
    }

    private void refreshData(){
        if(mHotel!=null){
            mCoverPagerAdapter.setData(mHotel.getImages());

            mName.setText(mHotel.getName());
            mRate.setRating(mHotel.getRate());
            mAddress.setText(mHotel.getAddress());
        }
    }

    private void goToCoverActivity(int position){
        Intent intent=new Intent(OrderActivity.this,CoverActivity.class);
        intent.putExtra(OrderActivity.HOTEL,mHotel);
        intent.putExtra(OrderActivity.POSITION_ITEM,position);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        //Toast.makeText(this,"lalalalal",Toast.LENGTH_SHORT).show();
        goToCoverActivity(mCoverViewPager.getCurrentItem());
    }


    private String mRoomType = "";


    @OnClick(R.id.choose_type_panel)
    void chooseRoomTypePanel() {
        // bottom sheet
        //show ra
        ChooseTypeBottomSheet.newInstance(mHotel.getRoomType())
                .show(getSupportFragmentManager(),ChooseTypeBottomSheet.TAG);
    }

    @BindView(R.id.room_type)
    TextView mRoomTypeTextView;

    @BindView(R.id.unit_price)
    TextView mUnitPrice;

    public void setRoomType(String s, Integer position) {
     mRoomType = s;

        mRoomTypeTextView.setText(mRoomType);

        mUnitPrice.setText(mHotel.getRoomPrice().get(position).toString()+" đ");
    }

    private String mCheckIn="";
    private String mCheckOut="";
    @BindView(R.id.check_in)
    TextView mDateCheckIn;

    @BindView(R.id.check_out)
    TextView mDateCheckOut;

    @OnClick(R.id.choose_date_panel)
    void chooseDateCheckInPanel(){
        final Calendar c=Calendar.getInstance();

        int day,month1,year1;
        day=c.get(Calendar.DAY_OF_MONTH);
        month1=c.get(Calendar.MONTH);
        year1=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCheckIn=dayOfMonth+"-"+(month+1)+"-"+year;
                mDateCheckIn.setText(mCheckIn);
            }
        },year1,month1,day);
        datePickerDialog.show();
    }
    @OnClick(R.id.choose_date_panel_1)
    void chooseDateCheckoutPanel(){
        final Calendar c=Calendar.getInstance();

        int day,month1,year1;
        day=c.get(Calendar.DAY_OF_MONTH);
        month1=c.get(Calendar.MONTH);
        year1=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCheckOut=dayOfMonth+"-"+(month+1)+"-"+year;
                mDateCheckOut.setText(mCheckOut);
            }
        },year1,month1,day);
        datePickerDialog.show();
    }


}
