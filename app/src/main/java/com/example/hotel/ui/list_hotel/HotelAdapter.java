package com.example.hotel.ui.list_hotel;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.model.Hotel;
import com.example.hotel.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ItemHolder> /*implements Filterable*/ {

    private ArrayList<Hotel> mData=new ArrayList<>();
/*    private List<Hotel> filter=new ArrayList<>();*/

    private Context mContext;

    HotelCallBack mCallBack;

    public ArrayList<? extends Parcelable> getData() {

        return mData;
    }

   /* @Override
    public Filter getFilter() {

        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();

                if(!charString.isEmpty()){
                    List<Hotel> filteredList=new ArrayList<>();
                    for(Hotel row : filter){
                        if(row.getAddress().toLowerCase().contains(charString.toLowerCase())||row.getName().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    mData.clear();
                    mData.addAll(filteredList);
                }
                else{
                    mData.clear();
                    mData.addAll(filter);
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=mData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData=(ArrayList<Hotel>)results.values;
                notifyDataSetChanged();
            }
        };
    }*/

    public interface HotelCallBack {
        void onFavoriteChanged(Hotel hotel,int position);
        void onHotelItemClick(Hotel hotel,int position);
    }


    public void setCallBack(HotelCallBack callBack) {
        mCallBack = callBack;
    }
    public void removeCallBack() {
        mCallBack = null;
    }

    public  HotelAdapter(Context context){
        mContext=context;
    }

    public void setData(List<Hotel> data) {
        mData.clear();
        if(data!=null)
        mData.addAll(data);
    /*    filter=data;*/

        notifyDataSetChanged();

    }

    public void notifyItemChange(Hotel hotel){
        int position=0;
        for (int i = 0; i <mData.size() ; i++) {
           if(mData.get(i).getId()==hotel.getId()) {
               position = i;
               break;
           }
        }
        notifyItemChanged(position);
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
            if(mCallBack!=null) {
                mCallBack.onHotelItemClick(mData.get(getAdapterPosition()),getAdapterPosition());
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

            if(mCallBack!=null) mCallBack.onFavoriteChanged(mData.get(getAdapterPosition()),getAdapterPosition());
        }
    }
}
