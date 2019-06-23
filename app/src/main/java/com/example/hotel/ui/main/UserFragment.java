package com.example.hotel.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel.ui.Infor_User.Change_Infor;
import com.example.hotel.ui.Infor_User.Change_Language;
import com.example.hotel.ui.Infor_User.Help;
import com.example.hotel.ui.Infor_User.History;
import com.example.hotel.ui.Infor_User.InforCard;
import com.example.hotel.ui.Infor_User.Notify;
import com.example.hotel.ui.Login_Register.Login;
import com.example.hotel.R;
import com.example.hotel.ui.Infor_User.SecurAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFragment extends Fragment implements View.OnClickListener {
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    Button txtLanguage,txtHistory,txtInforCard,txtNotify,txtSecure,txtHelp,txtLogout;
    ImageView imgEditInfor,profile_image;
    TextView txtUseName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        txtUseName=(TextView) view.findViewById(R.id.txtUseName);
        if(user.getDisplayName()==null)
        {
            txtUseName.setText(user.getUid());
        }
        else
        {
            txtUseName.setText(user.getDisplayName());
        }


        profile_image=(ImageView) view.findViewById(R.id.profile_image);

        if(user.getPhotoUrl()==null) {
            profile_image.setImageResource(R.drawable.no_avatar);
        }

        imgEditInfor=(ImageView) view.findViewById(R.id.imgEditInfor);
        txtLanguage=(Button) view.findViewById(R.id.txtLanguage);
        txtHistory=(Button) view.findViewById(R.id.txtHistory);
        txtInforCard=(Button) view.findViewById(R.id.txtInforCard);
        txtNotify=(Button) view.findViewById(R.id.txtNotify);
        txtSecure=(Button) view.findViewById(R.id.txtSecure);
        txtHelp=(Button) view.findViewById(R.id.txtHelp);
        txtLogout=(Button) view.findViewById(R.id.txtLogout);

        txtLanguage.setOnClickListener((OnClickListener) this);
        txtHistory.setOnClickListener((OnClickListener) this);
        txtInforCard.setOnClickListener((OnClickListener) this);
        txtNotify.setOnClickListener((OnClickListener) this);
        txtSecure.setOnClickListener((OnClickListener) this);
        txtHelp.setOnClickListener((OnClickListener) this);
        txtLogout.setOnClickListener((OnClickListener) this);
        imgEditInfor.setOnClickListener((OnClickListener) this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.imgEditInfor: {
                Intent intent=new Intent(getContext(),Change_Infor.class);
                startActivity(intent);
                break;
            }

            case  R.id.txtLanguage: {
                Intent intent=new Intent(getContext(),Change_Language.class);
                startActivity(intent);
                break;
            }

            case R.id.txtHistory: {
                Intent intent=new Intent(getContext(),History.class);
                startActivity(intent);
                break;
            }

            case  R.id.txtInforCard: {
                Intent intent=new Intent(getContext(),InforCard.class);
                startActivity(intent);
                break;
            }

            case R.id.txtNotify: {
                Intent intent=new Intent(getContext(),Notify.class);
                startActivity(intent);
                break;
            }
            case  R.id.txtSecure: {
                Intent intent=new Intent(getContext(),SecurAccount.class);
                startActivity(intent);
                break;
            }

            case R.id.txtHelp: {
                Intent intent=new Intent(getContext(),Help.class);
                startActivity(intent);
                break;
            }
            case  R.id.txtLogout: {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null) {
                    FirebaseAuth.getInstance().signOut();
                    getActivity().finish();
                }

                Intent intent = new Intent(getContext(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                break;
            }

            default:{
                break;
            }
        }
    }


}
