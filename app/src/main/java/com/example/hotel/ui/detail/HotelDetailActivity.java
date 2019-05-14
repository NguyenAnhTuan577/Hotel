package com.example.hotel.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hotel.model.Convenient;
import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.util.ImageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelDetailActivity extends AppCompatActivity {

    public static final String HOTEL = "hotel";
    @BindView(R.id.recycler_view)
    RecyclerView mMainRecyclerView;

    private ConvenientAdapter mAdapter;

    @BindView(R.id.cover)
    ImageView mCover;

    private Hotel mHotel;

    @OnClick(R.id.back)
    void back() {
        finish();
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

        mAdapter=new ConvenientAdapter(this);

        mMainRecyclerView.setAdapter(mAdapter);
        refreshData();
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
        data.add(new Convenient(ImageUtil.ImageKey.BAR,"Quầy bar"));
        data.add(new Convenient(ImageUtil.ImageKey.GYM,"Phòng Gym"));


        mAdapter.setData(data);
    }

    private void loadHotel() {
        if (mHotel != null) {

            Glide.with(this).load(mHotel.getAvatar())
                    .into(mCover);
        }
    }

}
