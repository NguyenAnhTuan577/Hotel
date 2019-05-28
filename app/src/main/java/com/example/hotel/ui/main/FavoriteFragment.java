package com.example.hotel.ui.main;

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
import com.example.hotel.ui.detail.HotelDetailActivity;
import com.example.hotel.ui.list_hotel.HotelAdapter;
import com.example.hotel.ui.search.SearchActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteFragment extends Fragment implements HotelAdapter.HotelCallBack,  EventListener<QuerySnapshot> {

    private static final String TAG = "FavoriteFragment";

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
    public void onHotelItemClick(Hotel item, int position) {
        if(getActivity()!=null) {
            Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
            intent.putExtra(HotelDetailActivity.HOTEL,item);
            startActivity(intent);
        }
    }

    private void getFromFireBase(){
        FirebaseFirestore.getInstance()
                .collection("hotels")
                .whereEqualTo("favorite",true)
                .addSnapshotListener(this);
    }

    private void refreshData() {
        mSwipeRefresh.setRefreshing(true);

        getFromFireBase();
    }

    private void setFireBase(Hotel hotel,int position){
        FirebaseFirestore.getInstance()
                .collection("hotels")
                .document(hotel.getId())
                .update("favorite",hotel.getFavorite());
    }

    @OnClick(R.id.search_panel)
    void goToSearchActivity() {
        if(getActivity()!=null) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(),R.anim.push_left_in,R.anim.fade_out);

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putParcelableArrayListExtra(SearchActivity.SEARCH_DATA,mAdapter.getData());
            startActivity(intent);
        }
    }

    @Override
    public void onFavoriteChanged(Hotel hotel, int position) {

        setFireBase(hotel,position);
        refreshData();

        if(getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).changeFavoriteInHomeTab(hotel,position);
        }
    }

    public void changedFavoriteThisHotel(Hotel hotel, int position) {
        //Toast.makeText(getContext(),"I am FavoriteTab, have a favorite hotel changed ?", Toast.LENGTH_SHORT).show();

        refreshData();

    }



    @Override
    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
        if(e==null) {
            List<Hotel> hotels = queryDocumentSnapshots.toObjects(Hotel.class);

            mAdapter.setData(hotels);
            Log.d(TAG,"Success !!!");
        }
        else {
            mAdapter.setData(null);
        }
        mSwipeRefresh.setRefreshing(false);
    }
}
