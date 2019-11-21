package com.example.tuanvatvo.demo2.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.adapter.AdapterMyFragment;
import com.example.tuanvatvo.demo2.adapter.AdapterMyFragmentChamDiem;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelCauHoi_Json;
import com.example.tuanvatvo.demo2.model.ModelSaveAnswer;
import com.example.tuanvatvo.demo2.model.MyFragment;
import com.example.tuanvatvo.demo2.model.MyFragmentChamDiem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChamDiemActivity extends AppCompatActivity {

    Toolbar toolBarChamDiem;
    ViewPager viewPagerChamDiem;
    TabLayout sliding_tabsChamDiem;
    MyFragmentChamDiem myfragmentChamDiem;
    ArrayList<MyFragmentChamDiem> fragmentListChamDiem;
    AdapterMyFragmentChamDiem adapterMyFragmentChamDiem;

    int dem = 0;
    TextView txt_lambai_socauDungChamDiem;
    TextView txt_cauhoihientaiChamDiem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_diem);

        addControls();
        addEvents();

    }

    private void addControls() {
        txt_lambai_socauDungChamDiem = findViewById(R.id.txt_lambai_socauDungChamDiem);
        txt_cauhoihientaiChamDiem = findViewById(R.id.txt_cauhoihientaiChamDiem);
        toolBarChamDiem = findViewById(R.id.toolBarChamDiem);
        viewPagerChamDiem = findViewById(R.id.viewPagerChamDiem);
        sliding_tabsChamDiem = findViewById(R.id.sliding_tabsChamDiem);
        fragmentListChamDiem = new ArrayList<>();
        genFragmentList();
        adapterMyFragmentChamDiem = new AdapterMyFragmentChamDiem(getSupportFragmentManager(),this,fragmentListChamDiem);
        viewPagerChamDiem.setAdapter(adapterMyFragmentChamDiem);
        sliding_tabsChamDiem.setupWithViewPager(viewPagerChamDiem);

        // tính số câu đúng
        if(DeThiActivity.listmodelCauHoiJsons.size() == LamBaiActivity.socauhoi){
            for(int i = 0 ; i < DeThiActivity.arraySaveAnswer.size() ; i ++){
                // nếu sai ở đây xem lại data trên locohosst ( xem lại mã đề )
                ModelCauHoi_Json modelCauHoi_json = DeThiActivity.listmodelCauHoiJsons.get(i);
                ModelSaveAnswer modelSaveAnswer = DeThiActivity.arraySaveAnswer.get(i);
                if(modelCauHoi_json.getDapan() == modelSaveAnswer.getNumber_answer()){
                    dem++;
                }
            }
            txt_lambai_socauDungChamDiem.setText(getResources().getString(R.string.dung)+" " + dem+"/"+LamBaiActivity.socauhoi);
        }
        else {
            int x =DeThiActivity.listmodelCauHoiJsons.size();
            Toast.makeText(this, "Có " +x +" câu hỏi , không đủ " +LamBaiActivity.socauhoi+ " câu xem lại data trên locaohosst", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {

        viewPagerChamDiem.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                txt_cauhoihientaiChamDiem.setText(i+1+"\\"+LamBaiActivity.socauhoi);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChamDiemActivity.this,DeThiActivity.class);
        intent.putExtra("lop",LamBaiActivity.lop);
        this.startActivity(intent);
        ChamDiemActivity.this.finish();

        super.onBackPressed();
    }

    private void genFragmentList() {

        for(int  i = 0 ; i < LamBaiActivity.socauhoi ; i ++){


            myfragmentChamDiem= new MyFragmentChamDiem();
            fragmentListChamDiem.add(myfragmentChamDiem);
        }
    }

}
