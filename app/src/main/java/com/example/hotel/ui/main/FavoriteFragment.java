package com.example.hotel.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.detail.HotelDetailActivity;
import com.example.hotel.ui.list_hotel.HotelAdapter;
import com.example.hotel.ui.list_hotel.ItemClickListener;
import com.example.hotel.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteFragment extends Fragment implements ItemClickListener {
    @BindView(R.id.hotel_recycler_view)
    RecyclerView mHotelRecyclerView;

    private HotelAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        mHotelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter=new HotelAdapter(getContext());
        mAdapter.setItemClickListener(this);

        mHotelRecyclerView.setAdapter(mAdapter);

        refreshData();
    }

    @Override
    public void onClick(Hotel item) {
        if(getActivity()!=null) {
            Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
            intent.putExtra(HotelDetailActivity.HOTEL,item);
            startActivity(intent);
        }
    }

    private void refreshData() {
        ArrayList<Hotel> data= new ArrayList<>();
        Random rnd = new Random();
        data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg","Sky hotel",4,"Quận cam, TP HCM","0126493541","50 000 đ",true));
        data.add(new Hotel("https://ihg.scene7.com/is/image/ihg/holiday-inn-the-colony-4629618286-4x3","Kaze hotel",4,"Thủ Đức TP HCM","6565659","90 000 đ",true));
        data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/797/79726354.jpg","Kumo hotel",3,"Nha Trang","45454545","150 000 đ",true));
        data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/460/46080789.jpg","Vanish hotel",5," Bến Tre","25252525","125 000 đ",true));


        mAdapter.setData(data);
    }

    @OnClick(R.id.search_panel)
    void goToSearchActivity() {
        if(getActivity()!=null) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    }
}
