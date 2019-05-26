package com.qc.yyzs.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qc.yyzs.pager.BasePager;


public  class MyFragMent  extends Fragment {

    private BasePager currPager;

    public MyFragMent(BasePager pager) {
        this.currPager=pager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(currPager!=null){

            return currPager.returnView;
        }
        return null;
    }
}


