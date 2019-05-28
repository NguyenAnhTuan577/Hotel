package com.example.hotel.ui.main.bottomsheet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.ui.oder.OrderActivity;
import com.example.hotel.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;

public class ChooseTypeBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String ROOM_TYPES = "room_types";
    public static final String TAG = "ChooseTypeBottomSheet";

    public static ChooseTypeBottomSheet newInstance(ArrayList<String> roomTypes) {

        Bundle args = new Bundle();
        args.putStringArrayList(ROOM_TYPES,roomTypes);

        ChooseTypeBottomSheet fragment = new ChooseTypeBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    private ArrayList<String> mRoomTypes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_room_type_bottom_sheet,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // no care
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout)
                        dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(-Util.getNavigationHeight(getActivity()));
                behavior.setHideable(false);
                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == STATE_COLLAPSED)
                            ChooseTypeBottomSheet.this.dismiss();
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                });
            }
        });
        onViewCreated(view);
    }

    public void onViewCreated(View view) {
        ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            ArrayList<String> pass = bundle.getStringArrayList(ROOM_TYPES);
            if(pass!=null) mRoomTypes.addAll(pass);
        }
        createRoomTypeChooser();

    }
    @BindView(R.id.room_type_linear_layout)
    LinearLayout mRoomTypeParent;

    private void createRoomTypeChooser() {
        mRoomTypeParent.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(mRoomTypeParent.getContext());
        if(inflater!=null)
        for (int i = 0; i < mRoomTypes.size(); i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_room_type,mRoomTypeParent,false);
            textView.setText(mRoomTypes.get(i));
            textView.setTag(R.id.room_type_tag,i);
            textView.setOnClickListener(this);
            mRoomTypeParent.addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        if(v instanceof TextView) {
           Integer position = (Integer) v.getTag(R.id.room_type_tag);
           if(position!=null) {


               Activity activity = getActivity();
               if(activity instanceof OrderActivity) {
                   ((OrderActivity)activity).setRoomType(mRoomTypes.get(position),position);
               }
           }
           dismiss();
        }
    }
}