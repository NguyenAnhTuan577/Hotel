package com.example.hotel.ui.detail;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hotel.model.Convenient;
import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.image_cover.CoverActivity;
import com.example.hotel.ui.image_cover.CoverPagerAdapter;
import com.example.hotel.ui.oder.OrderActivity;
import com.example.hotel.util.ImageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String HOTEL = "hotel";
    public static final String POSITION_ITEM = "position_item";
    @BindView(R.id.recycler_view)
    RecyclerView mMainRecyclerView;

    private ConvenientAdapter mConvenientAdapter;
    private CoverPagerAdapter mCoverPagerAdapter;

    @BindView(R.id.cover_view_pager)
    ViewPager mCoverViewPager;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.rating_hotel)
    RatingBar mRate;

    @BindView(R.id.favorite)
    ImageView mFavorite;

    @BindView(R.id.des)
    TextView mDescribe;

    private Hotel mHotel;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.book_hotel)
    void goToOder(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this,R.anim.push_left_in,R.anim.fade_out);

        Intent intent =new Intent(this,OrderActivity.class);
        intent.putExtra(OrderActivity.HOTEL,mHotel);
        startActivity(intent,options.toBundle());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            mHotel = bundle.getParcelable(HOTEL);
        }

        mMainRecyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));

        mConvenientAdapter =new ConvenientAdapter(this);

        mMainRecyclerView.setAdapter(mConvenientAdapter);
        mCoverPagerAdapter = new CoverPagerAdapter(this);
        mCoverViewPager.setAdapter(mCoverPagerAdapter);
        mCoverPagerAdapter.setClickListener(this);
        refreshData();


    }

    void goToCoverActivity(int position){


        Intent intent = new Intent(HotelDetailActivity.this, CoverActivity.class);
        intent.putExtra(HotelDetailActivity.HOTEL, mHotel);
        intent.putExtra(HotelDetailActivity.POSITION_ITEM,position);
        startActivity(intent);
    }

    private void refreshData() {
        loadHotel();

        ArrayList<Convenient> data = new ArrayList<>();
        data.add(new Convenient(true,ImageUtil.ImageKey.WIFI_OUTSIDE,"Miễn phí","Wifi tại sảnh"));
        data.add(new Convenient(true,ImageUtil.ImageKey.WIFI_INSIDE,"Miễn phí","Wifi trong phòng"));
        data.add(new Convenient(true,ImageUtil.ImageKey.SWIMMING_POOL,"","Bể bơi"));
        data.add(new Convenient(ImageUtil.ImageKey.SPA,"Spa"));
        data.add(new Convenient(ImageUtil.ImageKey.PARK,"Bãi đỗ xe"));
        data.add(new Convenient(ImageUtil.ImageKey.PET,"Chấp nhận vật nuôi"));
        data.add(new Convenient(ImageUtil.ImageKey.AIR_CONDITION,"Điều hòa nhiệt độ"));
        data.add(new Convenient(ImageUtil.ImageKey.RESTAURANT,"Nhà hàng"));
        data.add(new Convenient(ImageUtil.ImageKey.BAR,"Quầy bar",false));
        data.add(new Convenient(ImageUtil.ImageKey.GYM,"Phòng Gym"));


        mConvenientAdapter.setData(data);
    }

    private void loadHotel() {
        if (mHotel != null) {

            mCoverPagerAdapter.setData(mHotel.getImages());

            mName.setText(mHotel.getName());
            mRate.setRating(mHotel.getRate());

            boolean isFavorite=mHotel.getFavorite();
            if(isFavorite){
                mFavorite.setColorFilter(mFavorite.getContext().getResources().getColor(R.color.FlatRed));
            } else {
                mFavorite.setColorFilter(0xEEEEEE);
            }
            mDescribe.setText(mHotel.getDescribe());
        }
    }

    @Override
    public void onClick(View v) {
        goToCoverActivity(mCoverViewPager.getCurrentItem());
        }
}
