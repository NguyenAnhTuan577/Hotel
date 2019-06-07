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
import com.example.hotel.model.UserInfo;
import com.example.hotel.ui.search.SearchActivity;
import com.example.hotel.ui.detail.HotelDetailActivity;
import com.example.hotel.ui.list_hotel.HotelAdapter;
import com.example.hotel.util.DocumentGetResultListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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

    private FirebaseUser mUser;
    private UserInfo mUserInfo;

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

        updateFavorite(hotel,position);

        updateUserInfoFavorite(hotel.getId());


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
    private void getHotelsFromFireBase() {

        FirebaseFirestore.getInstance()
                .collection("hotels")
                .get()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }


    private void refreshData() {
        mSwipeRefresh.setRefreshing(true);
        mResults[0] = NO_SET;
        mResults[1] = NO_SET;

        getCurrentUserInfo();
        getHotelsFromFireBase();
    }

    private void getCurrentUserInfo() {
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        if(mUser==null)
            Toast.makeText(getContext(), "Please sign in to use this feature!", Toast.LENGTH_SHORT).show();
        else {
            FirebaseFirestore.getInstance()
                    .collection("user_infos")
                    .document(mUser.getUid())
                    .get()
                    .addOnSuccessListener(mUserInfoListener)
                    .addOnFailureListener(mUserInfoListener);
        }
    }

    private DocumentGetResultListener mUserInfoListener = new DocumentGetResultListener(){

        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if(documentSnapshot.exists()){

                mUserInfo=documentSnapshot.toObject(UserInfo.class);
                checkingResult(1,SUCCESS);
            } else checkingResult(1,FAILURE);
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            checkingResult(1,FAILURE);
            Toast.makeText(getContext(),"Fail to get user info",Toast.LENGTH_SHORT).show();
        }
    };


    private void updateFavorite(Hotel hotel, int position){
        FirebaseFirestore.getInstance()
                .collection("hotels")
                .document(hotel.getId())
                .update("favorite",hotel.getFavorite());


    }

    private void updateUserInfoFavorite(String idHotel){
        List<String> favors=mUserInfo.getFavors();
        if(favors.contains(idHotel))
            favors.remove(idHotel);
        else
            favors.add(idHotel);
        FirebaseFirestore.getInstance()
                .collection("user_infos")
                .document(mUserInfo.getUid())
                .update("favors",favors);
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


    public void changeFavoriteThisHotel(Hotel hotel, int position) {
        //Toast.makeText(getContext(),"I am Home Fragment, do U want me to change favorite this hotel ?", Toast.LENGTH_SHORT).show();
        updateUserInfoFavorite(hotel.getId());

        getHotelsFromFireBase();
        mAdapter.notifyItemChange(hotel);

    }
    private List<Hotel> hotels;
    @Override
    public void onSuccess(QuerySnapshot querySnapshot) {

        hotels = querySnapshot.toObjects(Hotel.class);
        checkingResult(0,SUCCESS);

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        checkingResult(0,FAILURE);
        mSwipeRefresh.setRefreshing(false);
        Log.d(TAG,"Fail to load data from firebase");
        mAdapter.setData(null);
    }

    public static final int NO_SET = -1;
    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;

    private int[] mResults = new int[]{NO_SET,NO_SET};

    private void checkingResult(int pos,int result) {
        mResults[pos] = result;

        if (mResults[0] != NO_SET && mResults[1] != NO_SET)  {

            if (mResults[0] == FAILURE || mResults[1] == FAILURE) {
                Toast.makeText(getContext(), "Xin lỗi, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                mSwipeRefresh.setRefreshing(false);
            } else {
                setUpHotels();
            }
        }
    }

    private void setUpHotels(){

        mSwipeRefresh.setRefreshing(false);

        List<String> favors = mUserInfo.getFavors();

        for (Hotel hotel : hotels) {
            if(favors.contains(hotel.getId()))
                hotel.setFavorite(true);
            else
                hotel.setFavorite(false);
            updateFavorite(hotel,0);
        }

        mAdapter.setData(hotels);
        Log.d(TAG,"Success !!!");
    }
}
