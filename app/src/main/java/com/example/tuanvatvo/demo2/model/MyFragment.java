package com.example.tuanvatvo.demo2.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.DeThiActivity;
import com.example.tuanvatvo.demo2.activity.LamBaiActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import io.github.kexanie.library.MathView;

public class MyFragment extends Fragment {

    View view;
    int page_now;
    ModelCauHoi_Json modelCauHoi_json_now;
    ModelSaveAnswer modelSaveAnswer  ;

    MathView mathview_namecauhoi;
    PhotoView photoview_namecauhoi;

    //
    LinearLayout linear_choiceA,linear_choiceB,linear_choiceC,linear_choiceD;
    TextView txt_IdchoiceA,txt_IdchoiceB,txt_IdchoiceC,txt_IdchoiceD;
    MathView math_choiceA,math_choiceB,math_choiceC,math_choiceD;
    //

    int id_Linear_now = -1 ; // đay là id của Linearlayout để lưu xem đáp án nào được chọn  ,
    // và nếu người dùng thay đổi đáp án thì id_Linear_now sẽ được cập nhậy

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_my,container,false);

        addControls();
        addEvents();


        return view;
    }

    private void addControls() {
        Bundle bundle = getArguments();
        page_now = bundle.getInt("page",0);
        // nếu setText page thì nó bắt đầu từ 0
        // còn nếu Toast thì nó bắt đầu từ 1
        //Toast.makeText(getContext(), page_now+"", Toast.LENGTH_SHORT).show();

        linear_choiceA = view.findViewById(R.id.linear_choiceA);
        linear_choiceB = view.findViewById(R.id.linear_choiceB);
        linear_choiceC = view.findViewById(R.id.linear_choiceC);
        linear_choiceD = view.findViewById(R.id.linear_choiceD);
        txt_IdchoiceA  = view.findViewById(R.id.txt_IdchoiceA);
        txt_IdchoiceB  = view.findViewById(R.id.txt_IdchoiceB);
        txt_IdchoiceC  = view.findViewById(R.id.txt_IdchoiceC);
        txt_IdchoiceD  = view.findViewById(R.id.txt_IdchoiceD);
        math_choiceA   = view.findViewById(R.id.math_choiceA);
        math_choiceB   = view.findViewById(R.id.math_choiceB);
        math_choiceC   = view.findViewById(R.id.math_choiceC);
        math_choiceD   = view.findViewById(R.id.math_choiceD);
        mathview_namecauhoi     = view.findViewById(R.id.mathview_namecauhoi);
        photoview_namecauhoi          = view.findViewById(R.id.photoview_namecauhoi);

        modelSaveAnswer         = new ModelSaveAnswer();
        modelSaveAnswer         = DeThiActivity.arraySaveAnswer.get(page_now);

        modelCauHoi_json_now    = new ModelCauHoi_Json();
        if(DeThiActivity.listmodelCauHoiJsons.size() > 0){
            modelCauHoi_json_now = DeThiActivity.listmodelCauHoiJsons.get(page_now);
            //TrangChuActivity.txt_stt.setText(page_now+"");
            if(modelCauHoi_json_now.getisImage() == 1){
                mathview_namecauhoi.setVisibility(View.INVISIBLE);
                photoview_namecauhoi.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(modelCauHoi_json_now.getLinkImage()).into(photoview_namecauhoi);
            }
            else {
                mathview_namecauhoi.setVisibility(View.VISIBLE);
                photoview_namecauhoi.setVisibility(View.INVISIBLE);
                mathview_namecauhoi.setText(modelCauHoi_json_now.getNamecauhoi());
            }

            // do du lieju
            math_choiceA.setText(modelCauHoi_json_now.getChoiceA()+"");
            math_choiceB.setText(modelCauHoi_json_now.getChoiceB()+"");
            math_choiceC.setText(modelCauHoi_json_now.getChoiceC()+"");
            math_choiceD.setText(modelCauHoi_json_now.getChoiceD()+"");
        }
        else{
            mathview_namecauhoi.setText("rỗng");
            Toast.makeText(getActivity(), "rỗng", Toast.LENGTH_SHORT).show();
        }

    }

    private void addEvents() {

        if(modelSaveAnswer.isAnswer == true){
            // neu cau tra loi dc tra loi
            if(modelSaveAnswer.getNumber_answer() == 1)
                txt_IdchoiceA.setBackgroundResource(R.drawable.custom_circle_button_chon);
            else if(modelSaveAnswer.getNumber_answer() == 2)
                txt_IdchoiceB.setBackgroundResource(R.drawable.custom_circle_button_chon);
            else if(modelSaveAnswer.getNumber_answer() == 3)
                txt_IdchoiceC.setBackgroundResource(R.drawable.custom_circle_button_chon);
            else
                txt_IdchoiceD.setBackgroundResource(R.drawable.custom_circle_button_chon);
        }


        linear_choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelSaveAnswer.setNumber_answer(1);
                modelSaveAnswer.setAnswer(true);
                xulycautraloi(linear_choiceA.getId());
            }
        });
        linear_choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelSaveAnswer.setNumber_answer(2);
                modelSaveAnswer.setAnswer(true);
                xulycautraloi(linear_choiceB.getId());
            }
        });
        linear_choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelSaveAnswer.setNumber_answer(3);
                modelSaveAnswer.setAnswer(true);
                xulycautraloi(linear_choiceC.getId());
            }
        });
        linear_choiceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelSaveAnswer.setNumber_answer(4);
                modelSaveAnswer.setAnswer(true);
                xulycautraloi(linear_choiceD.getId());
            }
        });
    }

    private void xulycautraloi(int id_linearLayout) {


        if(linear_choiceA.getId() == id_linearLayout){
            txt_IdchoiceA.setBackgroundResource(R.drawable.custom_circle_button_chon);
            if(id_Linear_now != -1 && id_Linear_now != id_linearLayout){
                if(id_Linear_now == linear_choiceB.getId())
                    txt_IdchoiceB.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                else if(id_Linear_now == linear_choiceC.getId())
                    txt_IdchoiceC.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                if(id_Linear_now == linear_choiceD.getId())
                    txt_IdchoiceD.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
            }
        }
        else if(linear_choiceB.getId() == id_linearLayout){
            txt_IdchoiceB.setBackgroundResource(R.drawable.custom_circle_button_chon);
            if(id_Linear_now != -1 && id_Linear_now != id_linearLayout){
                if(id_Linear_now == linear_choiceA.getId())
                    txt_IdchoiceA.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                else if(id_Linear_now == linear_choiceC.getId())
                    txt_IdchoiceC.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                if(id_Linear_now == linear_choiceD.getId())
                    txt_IdchoiceD.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
            }
        }
        else if(linear_choiceC.getId() == id_linearLayout){
            txt_IdchoiceC.setBackgroundResource(R.drawable.custom_circle_button_chon);
            if(id_Linear_now != -1 && id_Linear_now != id_linearLayout){
                if(id_Linear_now == linear_choiceB.getId())
                    txt_IdchoiceB.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                else if(id_Linear_now == linear_choiceA.getId())
                    txt_IdchoiceA.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                if(id_Linear_now == linear_choiceD.getId())
                    txt_IdchoiceD.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
            }
        }
        else{
            txt_IdchoiceD.setBackgroundResource(R.drawable.custom_circle_button_chon);
            if(id_Linear_now != -1 && id_Linear_now != id_linearLayout){
                if(id_Linear_now == linear_choiceB.getId())
                    txt_IdchoiceB.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                else if(id_Linear_now == linear_choiceC.getId())
                    txt_IdchoiceC.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
                if(id_Linear_now == linear_choiceA.getId())
                    txt_IdchoiceA.setBackgroundResource(R.drawable.custom_circle_button_khong_chon);
            }
        }

        id_Linear_now = id_linearLayout;
        //update Navigation
        LamBaiActivity.UpdateAnswer(page_now,modelSaveAnswer.getNumber_answer());
    }

    public static Fragment newInstance(int page) {
        MyFragment fragmentFirst = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }
}
