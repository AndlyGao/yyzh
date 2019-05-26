package com.qc.yyzs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qc.yyzs.R;

public class MusicAdapter extends BaseAdapter {
    private Context context;
    public MusicAdapter(Context context){
        this.context=context;

    }
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
            return  0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.music_main_search_item,null);
            viewHolder.xuhao= (TextView) convertView.findViewById(R.id.xuhao);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.xuhao.setText(position+1+"");
        return convertView;
    }

    static   class ViewHolder{
        TextView xuhao;
    }
}
