package com.example.hotel.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.detail.HotelDetailActivity;
import com.example.hotel.ui.list_hotel.HotelAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,HotelAdapter.HotelCallBack, OnSuccessListener<QuerySnapshot>, OnFailureListener {

    private static final String TAG="SearchActivity";
    public static final String SEARCH_DATA = "search_data";

    @BindView(R.id.recycler_view)
    RecyclerView mHotelRecyclerview;

    private HotelAdapter mAdapter;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @OnClick(R.id.search_view)
    void searchViewClicked() {
        mSearchView.onActionViewExpanded();
    }

    @OnClick(R.id.back_button)
    void back() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchView.onActionViewExpanded();
        mSearchView.requestFocus();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivy_search);
        ButterKnife.bind(this);

        mHotelRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mAdapter=new HotelAdapter(this);
        mAdapter.setCallBack(this);

        mSearchView.setOnQueryTextListener(this);
        mHotelRecyclerview.setAdapter(mAdapter);

        refreshData();
    }


    private void refreshData() {
       if(!getDataArgument())
           getFromeFireBase();
    }

    private boolean getDataArgument() {
        Bundle bundle = getIntent().getExtras();
        ArrayList<Hotel> list = null;
        if(bundle!=null) {
            list = bundle.getParcelableArrayList(SEARCH_DATA);
        }
        if(list!=null&& !list.isEmpty()) {
            mOriginalData.clear();
            mOriginalData.addAll(list);
            return true;
        } else return false;

    }

    private void getFromeFireBase() {

        Toast.makeText(this,"Trying to get data from server", Toast.LENGTH_SHORT).show();
        FirebaseFirestore.getInstance()
                .collection("hotels")
                .get()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_hotel,menu);

        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.search_view).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return  true;
    }*/

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    // ctrl + shift + /
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        if(!newText.isEmpty()) {
            List<Hotel> filterHotels = filter(mOriginalData, newText);
            mAdapter.setData(filterHotels);
        } else {
            mAdapter.setData(null);
        }
        return true;
    }

    @Override
    public void onFavoriteChanged(Hotel hotel, int position) {

    }


    private static List<Hotel> filter(List<Hotel> hotels, String query) {
        final String lowerCaseQuery = deAccent(query.toLowerCase());

        final List<Hotel> list = new ArrayList<>();

        for(Hotel hotel : hotels) {
            final String text = deAccent(hotel.getName().toLowerCase());
            final String address = deAccent(hotel.getAddress().toLowerCase());
            final String describe  = deAccent(hotel.getDescribe().toLowerCase());
            final String detail = deAccent(hotel.getDetail().toLowerCase());
            if(text.contains(lowerCaseQuery)|| address.contains(lowerCaseQuery)|| describe.contains(lowerCaseQuery)||detail.contains(lowerCaseQuery))
                list.add(hotel);
        }
        return list;
    }

    @Override
    public void onHotelItemClick(Hotel hotel, int position) {
        Intent intent=new Intent(this,HotelDetailActivity.class);
        intent.putExtra(HotelDetailActivity.HOTEL,hotel);
        startActivity(intent);
    }

    private ArrayList<Hotel> mOriginalData = new ArrayList<>();


    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        List<Hotel> hotels=queryDocumentSnapshots.toObjects(Hotel.class);


        mOriginalData.clear();
        mOriginalData.addAll(hotels);

        Log.d(TAG,"Successful!!!");
    }

    @Override
    public void onFailure(@NonNull Exception e) {

        Log.d(TAG,"Fail to load data from firebase");
        mOriginalData.clear();
        mAdapter.setData(null);
    }
}
