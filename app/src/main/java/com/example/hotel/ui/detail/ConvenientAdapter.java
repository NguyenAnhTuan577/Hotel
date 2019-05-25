package com.example.hotel.ui.detail;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.model.Convenient;
import com.example.hotel.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConvenientAdapter extends RecyclerView.Adapter<ConvenientAdapter.ItemHolder> {


    private ArrayList<Convenient> mData = new ArrayList<>();

    //
    private Context mContext;

    ItemClickListener mListener;

    public void setItemClickListener(ItemClickListener listener){
        mListener=listener;
    }
    public void removeItemClickListener(){
        mListener=null;
    }

    public ConvenientAdapter(Context context){
        mContext=context;
    }

    public void setData(List<Convenient> data){
        mData.clear();
        if(data!=null)
            mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_convenient_hotel_detail,viewGroup,false);
        return new ItemHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.icon)
        ImageView mIcon;

        @BindView(R.id.nameconvenient)
        TextView mName;

        @BindView(R.id.describe)
        TextView mDescribe;

        public ItemHolder(View itemView){
            super(itemView);

            ButterKnife.bind(this,itemView);


        }

        public void bind(Convenient convenient){
            if(convenient.mName==null||convenient.mName.isEmpty()) {
                mName.setVisibility(View.GONE);
            } else {
                mName.setText(convenient.mName);
                mName.setVisibility(View.VISIBLE);
            }

            mDescribe.setText(convenient.mDescribe);

            if(convenient.isUseDrawable())
                mIcon.setImageResource(ImageUtil.getDrawableIdByImageKey(convenient.mIcon));
            else
            Glide.with(mContext).load(convenient.mIcon).into(mIcon);

            if(!convenient.isUseConvenient()){
                mIcon.setAlpha(0.5f);
                mName.setAlpha(0.5f);
                mDescribe.setAlpha(0.5f);
            } else {
                mIcon.setAlpha(1f);
                mName.setAlpha(1f);
                mDescribe.setAlpha(1f);
            }

        }

    }
}
