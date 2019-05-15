package com.example.hotel.ui.list_hotel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ItemHolder> {

    private ArrayList<Hotel> mData=new ArrayList<>();

    private Context mContext;

    ItemClickListener mListener;

    public void setItemClickListener(ItemClickListener listener){
        mListener=listener;
    }
    public void removeItemClickListener(){
        mListener=null;
    }

    public  HotelAdapter(Context context){
        mContext=context;
    }

    public void setData(List<Hotel> data) {
        mData.clear();
        mData.addAll(data);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayout =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotel,viewGroup,false);
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
        @BindView(R.id.avatar)
        ImageView mAvatar;

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.rating_list_hotel)
        RatingBar mRate;

        @BindView(R.id.address)
        TextView mAddress;

        @BindView(R.id.detail)
        TextView mDetail;

        @BindView(R.id.price)
        TextView mPrice;

        @BindView(R.id.favorite)
        ImageView mFavorie;

        public ItemHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Hotel hotel){
            mName.setText(hotel.getName());
            mRate.setRating(hotel.getRate());
            mAddress.setText(hotel.getAddress());
            mDetail.setText(hotel.getDetail());
            mPrice.setText(hotel.getPrice());

            boolean isFavorite=hotel.getFavorite();
            bindFavorite(isFavorite);


            if(mContext!=null)
            Glide.with(mContext).load(hotel.getAvatar()).into(mAvatar);
        }

        @OnClick(R.id.root)
        public void goToHotel(){
            if(mListener!=null){
                mListener.onClick(mData.get(getAdapterPosition()));
            }
        }
        private void bindFavorite(boolean favor) {
            if(favor) {
                mFavorie.setColorFilter(mFavorie.getContext().getResources().getColor(R.color.FlatRed));
            } else {
                mFavorie.setColorFilter(0xEEEEEE);
            }
        }

        @OnClick(R.id.favorite)
        void favorOrUnfavorThisItem() {
           // TODO : notify activity that current favor of this item had changed.
            // My good english

            boolean favorite  = mData.get(getAdapterPosition()).getFavorite();
            favorite = !favorite;
            mData.get(getAdapterPosition()).setFavorite(favorite);
            bindFavorite(favorite);
        }
    }
}
