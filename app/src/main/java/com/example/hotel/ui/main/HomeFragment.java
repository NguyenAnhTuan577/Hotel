package com.example.hotel.ui.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.search.SearchActivity;
import com.example.hotel.ui.detail.HotelDetailActivity;
import com.example.hotel.ui.list_hotel.HotelAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements HotelAdapter.HotelCallBack, OnSuccessListener<QuerySnapshot>, OnFailureListener {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.hotel_recycler_view)
    RecyclerView mHotelRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private HotelAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        mHotelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter=new HotelAdapter(getActivity());
        mAdapter.setCallBack(this);

        mHotelRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setProgressViewOffset(true,
                (int) getResources().getDimension(R.dimen.refresher_offset),
                (int)getResources().getDimension(R.dimen.refresher_offset_end));
        mSwipeRefresh.setColorSchemeResources(R.color.White);
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.FlatGreen);
        mSwipeRefresh.setOnRefreshListener(this::refreshData);
        refreshData();
    }

    @Override
    public void onFavoriteChanged(Hotel hotel, int position) {

        setFireBase(hotel,position);

        Activity activity = getActivity();
        if(activity instanceof MainActivity) {
            ((MainActivity)activity).changeFavoriteInFavoriteTab(hotel,position);

        }
    }


    @Override
    public void onHotelItemClick(Hotel hotel,int position) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
            intent.putExtra(HotelDetailActivity.HOTEL, hotel);
            startActivity(intent);
        }
    }
    private void getFromFireBase() {

        FirebaseFirestore.getInstance()
                .collection("hotels")
                .get()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    private void refreshData() {
        mSwipeRefresh.setRefreshing(true);

        getFromFireBase();
    }

    private void  setFireBase(Hotel hotel,int position){
        FirebaseFirestore.getInstance()
                .collection("hotels")
                .document(hotel.getId())
                .update("favorite",hotel.getFavorite());

    }

    private void getSampleData() {
        ArrayList<Hotel> data= new ArrayList<>();
        Random rnd = new Random();
        Hotel hotel = new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg","Sky hotel",4,"Quận cam, TP HCM","0126493541","50 000 đ",true)
                .addImages("https://q-cf.bstatic.com/images/hotel/max1024x768/134/134481018.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/161/161051644.jpg","https://r-cf.bstatic.com/images/hotel/max1024x768/161/161052022.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478560.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478936.jpg");

        data.add(hotel);
        data.add(new Hotel("https://ihg.scene7.com/is/image/ihg/holiday-inn-the-colony-4629618286-4x3", "Kaze hotel", 4, "Thủ Đức TP HCM", "6565659", "90 000 đ")
                .addImages("https://q-cf.bstatic.com/images/hotel/max1024x768/134/134481018.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/161/161051644.jpg","https://r-cf.bstatic.com/images/hotel/max1024x768/161/161052022.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478560.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478936.jpg"));
        data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/797/79726354.jpg","Kumo hotel",3,"Nha Trang","45454545","150 000 đ",true)
                .addImages("https://q-cf.bstatic.com/images/hotel/max1024x768/134/134481018.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/161/161051644.jpg","https://r-cf.bstatic.com/images/hotel/max1024x768/161/161052022.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478560.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478936.jpg"));
        data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/460/46080789.jpg","Vanish hotel",5," Bến Tre","25252525","125 000 đ",true)
                .addImages("https://q-cf.bstatic.com/images/hotel/max1024x768/134/134481018.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/161/161051644.jpg","https://r-cf.bstatic.com/images/hotel/max1024x768/161/161052022.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478560.jpg","https://q-cf.bstatic.com/images/hotel/max1024x768/134/134478936.jpg"));;
        for (int i = 0; i < 100; i++) {
            data.add(new Hotel("https://q-ec.bstatic.com/images/hotel/max1024x768/797/79726354.jpg","Kumo hotel",rnd.nextInt(6),"Trà Vinh","35353535",rnd.nextInt(999)+"000 đ"));
        }

        mAdapter.setData(data);
    }

    @BindView(R.id.search_panel) View mSearchPanel;

    @OnClick(R.id.search_panel)
    void goToSearchActivity() {
        if(getActivity()!=null) {

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(),R.anim.push_left_in,R.anim.fade_out);

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putParcelableArrayListExtra(SearchActivity.SEARCH_DATA,mAdapter.getData());
            startActivity(intent,options.toBundle());
        }
    }

    @OnClick(R.id.location)
    void goToMapActivity() {
        if(getActivity()!=null) {

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(),R.anim.push_left_in,R.anim.fade_out);

            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent,options.toBundle());
        }
    }


    public void changeFavoriteThisHotel(Hotel hotel, int position) {
        //Toast.makeText(getContext(),"I am Home Fragment, do U want me to change favorite this hotel ?", Toast.LENGTH_SHORT).show();

        getFromFireBase();
        mAdapter.notifyItemChange(hotel);

    }

    @Override
    public void onSuccess(QuerySnapshot querySnapshot) {


        List<Hotel> hotels = querySnapshot.toObjects(Hotel.class);
        mAdapter.setData(hotels);

        mSwipeRefresh.setRefreshing(false);

        Log.d(TAG,"Success !!!");


    }

    @Override
    public void onFailure(@NonNull Exception e) {
        mSwipeRefresh.setRefreshing(false);
        Log.d(TAG,"Fail to load data from firebase");
        mAdapter.setData(null);
    }
}
