package com.example.tuanvatvo.demo2.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.model.ModelPhieuDapAn_Naviagation;

import java.util.ArrayList;

public class AdapterPhieuDapAn_Navigation extends ArrayAdapter<ModelPhieuDapAn_Naviagation> {

    boolean check = false;
    Activity context;
    int resource;
    ArrayList<ModelPhieuDapAn_Naviagation> arrayList;
    public AdapterPhieuDapAn_Navigation(@NonNull Activity context, int resource, @NonNull ArrayList<ModelPhieuDapAn_Naviagation> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View view = inflater.inflate(this.resource,null);

        TextView txt_stt_cau = view.findViewById(R.id.txt_stt_cau);
        Button btn_dapanA = view.findViewById(R.id.btn_dapanA);
        Button btn_dapanB = view.findViewById(R.id.btn_dapanB);
        Button btn_dapanC = view.findViewById(R.id.btn_dapanC);
        Button btn_dapanD = view.findViewById(R.id.btn_dapanD);


        final ModelPhieuDapAn_Naviagation dapAn = arrayList.get(position);

        txt_stt_cau.setText(dapAn.getStt() + 1 +"");

        if(dapAn.isDapanA()){
            btn_dapanA.setBackgroundResource(R.drawable.custom_button_chon);
        }
        else if (dapAn.isDapanB()){
            btn_dapanB.setBackgroundResource(R.drawable.custom_button_chon);
        }
        else if (dapAn.isDapanC()){
            btn_dapanC.setBackgroundResource(R.drawable.custom_button_chon);
        }
        else if (dapAn.isDapanD()){
            btn_dapanD.setBackgroundResource(R.drawable.custom_button_chon);
        }





        return view;
    }
}
