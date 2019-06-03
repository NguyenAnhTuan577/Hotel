package com.example.hotel.ui.oder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.hotel.model.Order;
import com.example.hotel.ui.image_cover.CoverActivity;
import com.example.hotel.ui.image_cover.CoverPagerAdapter;
import com.example.hotel.ui.main.bottomsheet.ChooseTypeBottomSheet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, OnSuccessListener<QuerySnapshot>, OnFailureListener {

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

    private Order mOrder;

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

            mOrder=new Order();
            mOrder.setIdHotel(mHotel.getId());
        }

        getFromFireBase();
    }
    private void getFromFireBase() {

        FirebaseFirestore.getInstance()
                .collection("orders")
                .get()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    private void setOrderToFireBase(){
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(mOrder.getId())
                .set(mOrder);
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

        mOrder.setIdRoomType(position.toString());
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
                mOrder.setCheckIn(mCheckIn);
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
                mOrder.setCheckOut(mCheckOut);
            }
        },year1,month1,day);
        datePickerDialog.show();
    }

    @OnClick(R.id.book_hotel)
    void confirmOrder(){
        showAlertDialog();
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Bạn có muốn something không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                setOrderToFireBase();

                Toast.makeText(OrderActivity.this,"lalalalal",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        List<Order> orders=queryDocumentSnapshots.toObjects(Order.class);
        Integer size=1;
        for(Order order:orders){
            size++;
        }
        mOrder.setId(size.toString());
    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }
}
