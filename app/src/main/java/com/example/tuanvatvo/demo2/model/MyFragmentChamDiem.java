package com.example.tuanvatvo.demo2.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.DeThiActivity;
import com.example.tuanvatvo.demo2.activity.LamBaiActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import io.github.kexanie.library.MathView;

public class MyFragmentChamDiem extends Fragment {

    View view;


    MathView mathview_namecauhoiChamDiem;
    PhotoView photoview_namecauhoiChamDiem;


    //
    LinearLayout linear_choiceAChamDiem,linear_choiceBChamDiem,linear_choiceCChamDiem,linear_choiceDChamDiem;
    TextView txt_IdchoiceAChamDiem,txt_IdchoiceBChamDiem,txt_IdchoiceCChamDiem,txt_IdchoiceDChamDiem;
    MathView math_choiceAChamDiem,math_choiceBChamDiem,math_choiceCChamDiem,math_choiceDChamDiem;
    //
    int page_nowChamDiem;
    ModelCauHoi_Json modelCauHoi_json_nowChamDiem;
    ModelSaveAnswer modelSaveAnswerChamDiem  ;
    ImageView img_truefalseChamDiem;
    Button btn_lamdemoiChamDiem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_cham_diem,container,false);

        addControls();
        addEVents();
        return view;
    }

    private void addControls() {
        Bundle bundle = getArguments();
        page_nowChamDiem = bundle.getInt("page",0);
        // nếu setText page thì nó bắt đầu từ 0
        // còn nếu Toast thì nó bắt đầu từ 1
        //Toast.makeText(getContext(), page_now+"", Toast.LENGTH_SHORT).show();

        img_truefalseChamDiem                 = view.findViewById(R.id.img_truefalseChamDiem);

        linear_choiceAChamDiem                = view.findViewById(R.id.linear_choiceAChamDiem);
        linear_choiceBChamDiem                = view.findViewById(R.id.linear_choiceBChamDiem);
        linear_choiceCChamDiem                = view.findViewById(R.id.linear_choiceCChamDiem);
        linear_choiceDChamDiem                = view.findViewById(R.id.linear_choiceDChamDiem);
        txt_IdchoiceAChamDiem                 = view.findViewById(R.id.txt_IdchoiceAChamDiem);
        txt_IdchoiceBChamDiem                 = view.findViewById(R.id.txt_IdchoiceBChamDiem);
        txt_IdchoiceCChamDiem                 = view.findViewById(R.id.txt_IdchoiceCChamDiem);
        txt_IdchoiceDChamDiem                 = view.findViewById(R.id.txt_IdchoiceDChamDiem);
        math_choiceAChamDiem                  = view.findViewById(R.id.math_choiceAChamDiem);
        math_choiceBChamDiem                  = view.findViewById(R.id.math_choiceBChamDiem);
        math_choiceCChamDiem                  = view.findViewById(R.id.math_choiceCChamDiem);
        math_choiceDChamDiem                  = view.findViewById(R.id.math_choiceDChamDiem);
        mathview_namecauhoiChamDiem           = view.findViewById(R.id.mathview_namecauhoiChamDiem);
        photoview_namecauhoiChamDiem          = view.findViewById(R.id.photoview_namecauhoiChamDiem);

        modelSaveAnswerChamDiem         = new ModelSaveAnswer();
        modelSaveAnswerChamDiem         = DeThiActivity.arraySaveAnswer.get(page_nowChamDiem);
        if(DeThiActivity.listmodelCauHoiJsons.size() > 0){
            modelCauHoi_json_nowChamDiem = DeThiActivity.listmodelCauHoiJsons.get(page_nowChamDiem);
            //TrangChuActivity.txt_stt.setText(page_now+"");
            if(modelCauHoi_json_nowChamDiem.getisImage() == 1){
                mathview_namecauhoiChamDiem.setVisibility(View.INVISIBLE);
                photoview_namecauhoiChamDiem.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(modelCauHoi_json_nowChamDiem.getLinkImage()).into(photoview_namecauhoiChamDiem);
            }
            else {
                mathview_namecauhoiChamDiem.setVisibility(View.VISIBLE);
                photoview_namecauhoiChamDiem.setVisibility(View.INVISIBLE);
                mathview_namecauhoiChamDiem.setText(modelCauHoi_json_nowChamDiem.getNamecauhoi());
            }

            // do du lieju
            math_choiceAChamDiem.setText(modelCauHoi_json_nowChamDiem.getChoiceA()+"");
            math_choiceBChamDiem.setText(modelCauHoi_json_nowChamDiem.getChoiceB()+"");
            math_choiceCChamDiem.setText(modelCauHoi_json_nowChamDiem.getChoiceC()+"");
            math_choiceDChamDiem.setText(modelCauHoi_json_nowChamDiem.getChoiceD()+"");
        }
        else{
            mathview_namecauhoiChamDiem.setText("rỗng");
            Toast.makeText(getActivity(), "rỗng", Toast.LENGTH_SHORT).show();
        }


        btn_lamdemoiChamDiem = view.findViewById(R.id.btn_lamdemoiChamDiem);

    }



    private void addEVents() {

        btn_lamdemoiChamDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeThiActivity.class);
                intent.putExtra("lop",LamBaiActivity.lop);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        if(modelCauHoi_json_nowChamDiem != null){

            int yourChoice = modelSaveAnswerChamDiem.getNumber_answer();
            int severChoice = modelCauHoi_json_nowChamDiem.getDapan();

            if(yourChoice == severChoice){
                // neu cẩu trả lời trùng với đáp án của server
                if(yourChoice == 1)
                    txt_IdchoiceAChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                else if(yourChoice == 2)
                    txt_IdchoiceBChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                else if(yourChoice == 3)
                    txt_IdchoiceCChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                else
                    txt_IdchoiceDChamDiem.setBackgroundResource(R.drawable.custom_button_true);

                img_truefalseChamDiem.setImageResource(R.drawable.ic_true_24dp);
            }
            else {
                // nếu trả lời sai
                if(yourChoice == 0){
                    // nếu người thi cố tình không chọn thì ta hiện đáp án đúng nên
                    if(severChoice == 1)
                        txt_IdchoiceAChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else if(severChoice == 2)
                        txt_IdchoiceBChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else if(severChoice == 3)
                        txt_IdchoiceCChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else
                        txt_IdchoiceDChamDiem.setBackgroundResource(R.drawable.custom_button_true);

                }
                else {
                    if(severChoice == 1)
                        txt_IdchoiceAChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else if(severChoice == 2)
                        txt_IdchoiceBChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else if(severChoice == 3)
                        txt_IdchoiceCChamDiem.setBackgroundResource(R.drawable.custom_button_true);
                    else
                        txt_IdchoiceDChamDiem.setBackgroundResource(R.drawable.custom_button_true);


                    if(yourChoice == 1)
                        txt_IdchoiceAChamDiem.setBackgroundResource(R.drawable.custom_button_false);
                    else if(yourChoice == 2)
                        txt_IdchoiceBChamDiem.setBackgroundResource(R.drawable.custom_button_false);
                    else if(yourChoice == 3)
                        txt_IdchoiceCChamDiem.setBackgroundResource(R.drawable.custom_button_false);
                    else
                        txt_IdchoiceDChamDiem.setBackgroundResource(R.drawable.custom_button_false);
                }
                img_truefalseChamDiem.setImageResource(R.drawable.ic_false_24dp);
            }


        }
    }

    public static Fragment newInstance(int page) {
        MyFragmentChamDiem fragmentFirst = new MyFragmentChamDiem();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }
}
