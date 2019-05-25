package com.example.hotel.ui.image_cover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.detail.HotelDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoverActivity extends AppCompatActivity {

    @BindView(R.id.cover)
    ViewPager mViewPager;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.rating_hotel)
    RatingBar mRate;

    @BindView(R.id.favorite)
    ImageView mFavorite;

    private Hotel mHotel;
    private int mPosition;
    private CoverPagerAdapter mViewPagerAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_view_pager);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mHotel=bundle.getParcelable(HotelDetailActivity.HOTEL);
            mPosition=bundle.getInt(HotelDetailActivity.POSITION_ITEM,0);
        }
        mViewPagerAdapter=new CoverPagerAdapter(this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPagerAdapter.setCenterCrop(false);

        loadHotel();
    }
    private void loadHotel(){
        if(mHotel!=null){

            mViewPagerAdapter.setData(mHotel.getImages());
            mViewPager.setCurrentItem(mPosition);

            mName.setText(mHotel.getName());
            mRate.setRating(mHotel.getRate());

            boolean isFavorite=mHotel.getFavorite();
            if(isFavorite){
                mFavorite.setColorFilter(mFavorite.getContext().getResources().getColor(R.color.FlatRed));
            } else {
                mFavorite.setColorFilter(0xEEEEEE);
            }
        }
    }

}
