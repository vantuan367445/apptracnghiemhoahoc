package com.example.tuanvatvo.demo2.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.model.ModelItemHistory;

import java.util.List;

public class AdapterHistory extends ArrayAdapter<ModelItemHistory> {
    Activity context;
    int resource;
    List mlist;
    public AdapterHistory(@NonNull Activity context, int resource, @NonNull List mlist) {
        super(context, resource, mlist);
        this.context = context;
        this.mlist = mlist;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        TextView txt_namede_history,txt_socaudung_history,txt_ngaylambai_history,txt_giolambai_history,txt_timelambai_history;
        txt_namede_history = view.findViewById(R.id.txt_namede_history);
        txt_socaudung_history = view.findViewById(R.id.txt_socaudung_history);
        txt_ngaylambai_history = view.findViewById(R.id.txt_ngaylambai_history);
        txt_giolambai_history = view.findViewById(R.id.txt_giolambai_history);
        txt_timelambai_history = view.findViewById(R.id.txt_timelambai_history);

        ModelItemHistory history = (ModelItemHistory) mlist.get(position);
        txt_namede_history.setText(history.getNameDe()+"");
        txt_socaudung_history.setText(history.getSoCauDung()+"/"+history.getSocauhoi()+"");
        txt_ngaylambai_history.setText(history.getDay()+"");
        txt_giolambai_history.setText(history.getTime()+"");
        txt_timelambai_history.setText(history.getTimeLamBai()+"");


        return view;
    }
}
