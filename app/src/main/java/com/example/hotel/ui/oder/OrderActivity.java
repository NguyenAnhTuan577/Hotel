package com.example.hotel.ui.oder;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.model.Order;
import com.example.hotel.model.UserInfo;
import com.example.hotel.ui.image_cover.CoverActivity;
import com.example.hotel.ui.image_cover.CoverPagerAdapter;
import com.example.hotel.ui.main.bottomsheet.ChooseTypeBottomSheet;
import com.example.hotel.util.DocumentGetResultListener;
import com.example.hotel.util.DocumentPushResultListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, OnSuccessListener<QuerySnapshot>, OnFailureListener {
    public static final String TAG="OrderActivity";

    public static final String HOTEL="hotel";
    public static final String ORDER="order";
    public static final String POSITION_ITEM="position_item";
    public static final String TOTAL_DAY="total_day";

    private CoverPagerAdapter mCoverPagerAdapter;

    private FirebaseUser mUser;
    private UserInfo mUserInfo;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.rating_hotel)
    RatingBar mRate;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.cover_view_pager)
    ViewPager mCoverViewPager;

    @BindView(R.id.notify_panel) View mNotifyPanel;
    @BindView(R.id.notify_text) TextView mNotifyText;
    @BindView(R.id.bill_text) TextView mBillText;

    @BindView(R.id.book_hotel) AppCompatButton mBookConfirmButton;
    @BindView(R.id.alert_text) TextView mAlertText;


    private Hotel mHotel;

    private Order mOrder;

    @OnClick(R.id.back)
    void back() {
        //Toast.makeText(this,"lalalalal",Toast.LENGTH_SHORT).show();

        finish();
    }


    int[] mCheckInArrDate = new int[3] ;
    int[] mCheckOutArrDate = new int[3];

    public static int daysBetweenTwoDates(int[] from, int[] to) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(from[2],from[1],from[0]);
        long startDateMillis = startDate.getTimeInMillis();

        Calendar endDate = Calendar.getInstance();
        endDate.set(to[2],to[1],to[0]);
        long endDateMillis = endDate.getTimeInMillis();

        long differenceMillis = endDateMillis - startDateMillis;
        int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
        return daysDifference;
    }

    void notifyInputChanged() {

        if(mCheckIn.isEmpty()||mCheckOut.isEmpty()||mRoomType.isEmpty()||mRoomTypePrice<=0||mUserInfo==null) {
            mBookConfirmButton.setEnabled(false);
            mBookConfirmButton.setBackgroundColor(0xff666666);
            mNotifyPanel.setVisibility(View.GONE);
        } else {


            int numberDays = daysBetweenTwoDates(mCheckInArrDate,mCheckOutArrDate);

            int price = numberDays* mRoomTypePrice;

            mOrder.setPurchase(price);

            mNotifyText.setText(getString(R.string.order_one_room) + " " + mRoomType + " " + getString(R.string.in) + " " + numberDays + " " + getString(R.string.days));
            mBillText.setText(mRoomTypePrice + " x " + numberDays + " = " + price + getString(R.string.vnd_symbol));

            if(numberDays<1)  {
                mAlertText.setVisibility(View.VISIBLE);
                mAlertText.setText(R.string.checkout_must_be_after_checkin);
                mBookConfirmButton.setEnabled(false);
                mBookConfirmButton.setBackgroundColor(0xff666666);

            }
            else if(price>mUserInfo.getBalance()) {

                mAlertText.setVisibility(View.VISIBLE);
                mAlertText.setText(R.string.not_enough_balance);
                mBookConfirmButton.setEnabled(false);
                mBookConfirmButton.setBackgroundColor(0xff666666);
            } else {
                mAlertText.setVisibility(View.GONE);

                mAlertText.setVisibility(View.GONE);
                mBookConfirmButton.setBackgroundColor(getResources().getColor(R.color.FlatGreen));
                mBookConfirmButton.setEnabled(true);
            }

            mNotifyPanel.setVisibility(View.VISIBLE);
        }

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
            notifyInputChanged();
            mCoverPagerAdapter.setData(mHotel.getImages());

            mName.setText(mHotel.getName());
            mRate.setRating(mHotel.getRate());
            mAddress.setText(mHotel.getAddress());

            mOrder=new Order();
            mOrder.setIdHotel(mHotel.getId());
        }

        getCurrentUser();
        getFromFireBase();
    }


    private void getCurrentUser() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null)
            Toast.makeText(this, "Please sign in to use this feature!", Toast.LENGTH_SHORT).show();
        else {
            mUser.isAnonymous();///

            Log.d(TAG, "getCurrentUser: uid = " + mUser.getUid() + ", anonymous = " + mUser.isAnonymous());


            FirebaseFirestore.getInstance()
                    .collection("user_infos")
                    .document(mUser.getUid())
                    .get()
                    .addOnSuccessListener(mUserInfoListener)
                    .addOnFailureListener(mUserInfoListener);
        }
    }

    private void getFromFireBase() {

        FirebaseFirestore.getInstance()
                .collection("orders")
                .get()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }
    @BindView(R.id.loading_parent)
    FrameLayout mLoadingParent;

    private void setOrderToFireBase(){
        mLoadingParent.setVisibility(View.VISIBLE);

        mBookConfirmButton.setVisibility(View.GONE);
        mBookConfirmButton.setEnabled(false);
        mBookConfirmButton.setBackgroundColor(0xff666666);

        mUserInfo.setBalance(mUserInfo.getBalance() - mOrder.getPurchase());

        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(mOrder.getId())
                .set(mOrder)
                .addOnSuccessListener(mPushOrderListener)
                .addOnFailureListener(mPushOrderListener);

        FirebaseFirestore.getInstance()
                .collection("user_infos")
                .document(mUser.getUid())
                .set(mUserInfo)
                .addOnSuccessListener(mPushBalanceListener)
                .addOnFailureListener(mPushBalanceListener);

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
    private int mRoomTypePrice = 0;


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
        mRoomTypePrice = mHotel.getRoomPrice().get(position);

        mRoomTypeTextView.setText(mRoomType);

        mUnitPrice.setText(mRoomTypePrice+getString(R.string.vnd_symbol));

        mOrder.setIdRoomType(position.toString());

        notifyInputChanged();
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
                view.setMinDate(System.currentTimeMillis() - 1000);

                mCheckIn=dayOfMonth+"-"+(month+1)+"-"+year;

                mCheckInArrDate[0] = dayOfMonth;
                mCheckInArrDate[1] = month;
                mCheckInArrDate[2] = year;

                mDateCheckIn.setText(mCheckIn);
                mOrder.setCheckIn(mCheckIn);
                notifyInputChanged();
            }
        },year1,month1,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    @OnClick(R.id.choose_date_panel_1)
    void chooseDateCheckoutPanel(){
        final Calendar c=Calendar.getInstance();

        int day,month1,year1;
        day=c.get(Calendar.DAY_OF_MONTH);
        month1=c.get(Calendar.MONTH);
        year1=c.get(Calendar.YEAR);
        //c.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                view.setMinDate(System.currentTimeMillis() - 1000);

                mCheckOut=dayOfMonth+"-"+(month+1)+"-"+year;
                mCheckOutArrDate[0] = dayOfMonth;
                mCheckOutArrDate[1] = month;
                mCheckOutArrDate[2] = year;

                mDateCheckOut.setText(mCheckOut);
                mOrder.setCheckOut(mCheckOut);
                notifyInputChanged();
            }
        },year1,month1,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @OnClick(R.id.book_hotel)
    void confirmOrder(){
        showAlertDialog();
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Xác nhận thanh toán ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setOrderToFireBase();
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

    private void startPaymentActivity(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this,R.anim.push_left_in,R.anim.fade_out);

        Intent intent =new Intent(this,PaymentActivity.class);
        intent.putExtra(OrderActivity.HOTEL,mHotel);
        intent.putExtra(OrderActivity.ORDER,mOrder);
        intent.putExtra(OrderActivity.TOTAL_DAY,daysBetweenTwoDates(mCheckInArrDate,mCheckOutArrDate));
        startActivity(intent);
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

    private DocumentGetResultListener mUserInfoListener = new DocumentGetResultListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(OrderActivity.this,"Fail to get user info",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if(documentSnapshot.exists()) {
                mUserInfo = documentSnapshot.toObject(UserInfo.class);
                Log.d(TAG, "onSuccess: get user info successfully");
                Log.d(TAG, "onSuccess: user info : balance = "+ mUserInfo.getBalance());

                mOrder.setUid(mUserInfo.getUid());
            }
            else {
                mUserInfo = new UserInfo();
                mUserInfo.setUid(mUser.getUid());
                mUserInfo.setBalance(0);
                Log.d(TAG, "onSuccess: auto created user info.");
            }
        }
    };
    public static final int NO_SET = -1;
    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;

    private int[] mResults = new int[]{NO_SET,NO_SET};

    private void checkingResult(int pos,int result) {
        mResults[pos] = result;

        if(mResults[0]!=NO_SET&&mResults[1]!=NO_SET)
        if(mResults[0]==FAILURE||mResults[1]==FAILURE) {
            Toast.makeText(this,"Xin lỗi, vui lòng thử lại!",Toast.LENGTH_LONG).show();
            mLoadingParent.setVisibility(View.GONE);

            mBookConfirmButton.setVisibility(View.VISIBLE);
            mBookConfirmButton.setEnabled(true);
            mBookConfirmButton.setBackgroundResource(R.color.FlatGreen);
        } else {
            startPaymentActivity();
            finish();
        }
    }

    private DocumentPushResultListener mPushOrderListener = new DocumentPushResultListener() {


        @Override
        public void onSuccess(Void aVoid) {
            checkingResult(0,SUCCESS);
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            checkingResult(0,FAILURE);
        }


    };

    private DocumentPushResultListener mPushBalanceListener = new DocumentPushResultListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            checkingResult(1,FAILURE);
        }

        @Override
        public void onSuccess(Void v) {
            checkingResult(1,SUCCESS);
        }
    };

}
