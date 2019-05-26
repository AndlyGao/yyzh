package com.qc.yyzs.pager;

import android.content.Context;
import android.view.View;

public abstract class BasePager {

    public final  Context context;
    public View returnView;
    public boolean isInitData;

    public BasePager(Context context){
        this.context=context;
        returnView= initView();
    }

    public abstract View initView() ;
    public void initData(){

    }
}
