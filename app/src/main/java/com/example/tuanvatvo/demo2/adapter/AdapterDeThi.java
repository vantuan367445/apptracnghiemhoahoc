package com.example.tuanvatvo.demo2.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.DeThiActivity;
import com.example.tuanvatvo.demo2.activity.LamBaiActivity;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelCauHoi_Json;
import com.example.tuanvatvo.demo2.model.ModelDeThi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterDeThi extends RecyclerView.Adapter<AdapterDeThi.ViewHolder>  {

    private ArrayList<ModelDeThi> mList;
     Activity contect;

    public AdapterDeThi(ArrayList<ModelDeThi> mList, Activity contect) {
        this.mList = mList;
        this.contect = contect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dethi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ModelDeThi modelDeThi = mList.get(i);
        viewHolder.txtnameDeThi.setText(modelDeThi.getNamedethi().toString().trim());
        viewHolder.txtmaDeThi.setText(modelDeThi.getMade().toString().trim());
        viewHolder.txt_socauhoiDeThi.setText(modelDeThi.getSocauhoi()+" câu");
        viewHolder.txt_timelambaiDeThi.setText((long)(modelDeThi.getSocauhoi()*1.5)+" phút");


    }

    @Override
    public int getItemCount() {
        return  mList == null ? 0 : mList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView txtnameDeThi;
        TextView txtmaDeThi,txt_timelambaiDeThi;
        Button btn_lambaiThi;
        TextView txt_socauhoiDeThi;

        ViewHolder(final View itemView) {
            super(itemView);
            txtnameDeThi = itemView.findViewById(R.id.txt_nameDeThi);
            btn_lambaiThi = itemView.findViewById(R.id.btn_lambaiThi);
            txtmaDeThi = itemView.findViewById(R.id.txtmaDeThi);
            txt_socauhoiDeThi = itemView.findViewById(R.id.txt_socauhoiDeThi);
            txt_timelambaiDeThi = itemView.findViewById(R.id.txt_timelambaiDeThi);

            btn_lambaiThi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int  i = getPosition();
                    ModelDeThi modelDeThi = mList.get(i);
                    xulymoDialg(modelDeThi);

                }
            });
        }
    }
    private  void xulymoDialg(final ModelDeThi modelDeThi ){
        AlertDialog.Builder builder = new AlertDialog.Builder(contect);
        builder.setTitle(R.string.lamdethi);
        builder.setMessage(R.string.bandasansanglamde);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(contect, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(contect, "ĐỒNg Ý", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contect, LamBaiActivity.class);
                intent.putExtra("modelDeThi",modelDeThi);
                contect.startActivity(intent);
                contect.finish();
            }
        });
        getModelCauHoiJson(modelDeThi.getMade().toString().trim());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public  void getModelCauHoiJson(final String made) {
        DeThiActivity.listmodelCauHoiJsons = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(contect);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, LinkURL.urlgetCauHoi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(TrangChuActivity.this, response.toString()+"", Toast.LENGTH_SHORT).show();

                if(requestQueue != null){
                    DeThiActivity.listmodelCauHoiJsons.clear();
                    for(int  i = 0 ; i < response.length() ; i ++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if(jsonObject.getString("idmade").equals(made)){
                                DeThiActivity.listmodelCauHoiJsons.add(new ModelCauHoi_Json(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("namecauhoi"),
                                        jsonObject.getString("choice_a"),
                                        jsonObject.getString("choice_b"),
                                        jsonObject.getString("choice_c"),
                                        jsonObject.getString("choice_d"),
                                        jsonObject.getInt("idlop"),
                                        jsonObject.getString("idmade"),
                                        jsonObject.getInt("isimage"),
                                        jsonObject.getString("linkimage"),
                                        jsonObject.getInt("dapan"),
                                        0
                                ));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LOI",e.toString());
                            Toast.makeText(contect, e.toString()+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contect, error.toString()+"", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }



}
