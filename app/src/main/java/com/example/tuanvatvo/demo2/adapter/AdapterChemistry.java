package com.example.tuanvatvo.demo2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.DeThiActivity;
import com.example.tuanvatvo.demo2.model.ModelChemistry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public  class AdapterChemistry extends RecyclerView.Adapter<AdapterChemistry.ViewHolder> {

    private ArrayList<ModelChemistry> mList;
    Activity context;

    ModelChemistry chemistry1;
    int check = -1;


    public AdapterChemistry(ArrayList<ModelChemistry> mList, Activity context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chemistry, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ModelChemistry chemistry2 = mList.get(position);

        holder.text_id.setText(chemistry2.getName()+"");
        Picasso.with(context).load(chemistry2.getLinkImage()).into(holder.img_avt);
        holder.text_mota.setText(chemistry2.getDescribe().toString().trim());

        if(chemistry2.isSelected() == true){
             // đã click
            holder.text_mota.setVisibility(View.INVISIBLE);
            holder.Line_practice.setVisibility(View.VISIBLE);
        }
        else {
            holder.text_mota.setVisibility(View.VISIBLE);
            holder.Line_practice.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         ImageView img_avt;
         TextView text_id;
         TextView text_mota;
        LinearLayout Line_practice;
        Button btn_kiemtra;
        //Button btn_lythuyet;


        ViewHolder(final View itemView) {
            super(itemView);
            img_avt         = itemView.findViewById(R.id.img_avt);
            text_id         = itemView.findViewById(R.id.text_id);
            text_mota       = itemView.findViewById(R.id.text_mota);
            Line_practice   = itemView.findViewById(R.id.Line_practice);
            btn_kiemtra     = itemView.findViewById(R.id.btn_kiemtra);
            //btn_lythuyet    = itemView.findViewById(R.id.btn_lythuyet);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(check != getPosition() && check > -1){
                        // dã có dữ liệu
                        ModelChemistry chemistry3 = mList.get(check);
                        chemistry3.setSelected(false);
                    }


                    // làm việc với chemistry2 là chính
                    chemistry1 = mList.get(getPosition());
                    if(chemistry1.isSelected() == true)
                        chemistry1.setSelected(false);
                    else if (chemistry1.isSelected() == false)
                        chemistry1.setSelected(true);

                    check = getPosition();
                    notifyDataSetChanged();
                }
            });

            btn_kiemtra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "Kiem tra" + chemistry1.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DeThiActivity.class);
//                    if(chemistry1.getLop() == 13){
//                        intent.putExtra("lop",chemistry1.getLop());
//                    }
//                    else
//                    intent.putExtra("lop",chemistry1.getLop());
                    intent.putExtra("lop",chemistry1.getLop());
                    context.startActivity(intent);
                }
            });
//            btn_lythuyet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Ly thuyet"+chemistry1.getName(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }




    }
}