package com.example.hotel.model;

import com.google.gson.annotations.SerializedName;

public class Convenient {
    //@SerializedName("")
    public String mIcon;
    public boolean mUseDrawable = true;

    public boolean mIsUseConvenient = true;

    public boolean isUseConvenient() {
        return mIsUseConvenient;
    }

    public void setmIsUseConvenient(boolean mIsUseConvenient) {
        this.mIsUseConvenient = mIsUseConvenient;
    }

    public boolean isUseDrawable() {
        return mUseDrawable;
    }

    public void setUseDrawable(boolean mUseDrawable) {
        this.mUseDrawable = mUseDrawable;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String mDescribe) {
        this.mDescribe = mDescribe;
    }

    // @SerializedName("")
    public String mName;

    //@SerializedName("")
    public String mDescribe;



    public Convenient(boolean mUseDrawable, String mIcon, String mName, String mDescribe){
        this.mUseDrawable = mUseDrawable;
        this.mIcon=mIcon;
        this.mName=mName;
        this.mDescribe=mDescribe;
    }

    public Convenient(boolean mUseDrawable, String mIcon, String mDescribe) {

        this.mUseDrawable = mUseDrawable;
        this.mIcon = mIcon;
        this.mDescribe = mDescribe;
    }

    public Convenient(String mIcon, String mDescribe) {
        this.mUseDrawable = true;
        this.mIcon = mIcon;
        this.mName = "";
        this.mDescribe = mDescribe;
    }
    public Convenient(String mIcon, String mDescribe,boolean mIsUseConvenient) {
        this.mUseDrawable = true;
        this.mIcon = mIcon;
        this.mName = "";
        this.mDescribe = mDescribe;
        this.mIsUseConvenient=mIsUseConvenient;
    }

    public Convenient(){
        mIcon="";
        mName="";
        mDescribe="";
    }
}
