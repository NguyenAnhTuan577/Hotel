package com.example.hotel.ui.image_cover;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;

import java.util.ArrayList;
import java.util.List;

public class CoverPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;

    public CoverPagerAdapter(Context context) {
        mContext = context;
    }

    private ArrayList<String> mData = new ArrayList<>();

    private boolean mCenterCrop = true;
    public void setCenterCrop(boolean value) {
        mCenterCrop = value;
    }

    public void setData(List<String> data) {
        mData.clear();
        if(data!=null)
        mData.addAll(data);

        notifyDataSetChanged();
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        String imageUrl = mData.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ImageView image = (ImageView)inflater.inflate(R.layout.item_cover_view_pager, collection, false);
        if(mContext!=null&&image!=null) {
            if(mCenterCrop) image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            else image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Glide.with(mContext).load(imageUrl).into(image);
            image.setOnClickListener(this);
            collection.addView(image);
        }
        return image;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mContext!=null)
        return mContext.getString(R.string.image)+" "+ (position+1);
        return "";
    }

    public void setClickListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    private View.OnClickListener mListener;

    @Override
    public void onClick(View v) {
        if(mListener!=null) mListener.onClick(v);
    }
}
