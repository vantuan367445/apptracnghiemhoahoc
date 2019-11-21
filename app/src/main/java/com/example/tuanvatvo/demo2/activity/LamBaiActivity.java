package com.example.tuanvatvo.demo2.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.tuanvatvo.demo2.adapter.AdapterMyFragment;
import com.example.tuanvatvo.demo2.adapter.AdapterPhieuDapAn_Navigation;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelCauHoi_Json;
import com.example.tuanvatvo.demo2.model.ModelDeThi;
import com.example.tuanvatvo.demo2.model.ModelPhieuDapAn_Naviagation;
import com.example.tuanvatvo.demo2.model.ModelSaveAnswer;
import com.example.tuanvatvo.demo2.model.MyFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class LamBaiActivity extends AppCompatActivity {



    NavigationView navigationView;
    DrawerLayout drawerlayout;

    static ArrayList<ModelPhieuDapAn_Naviagation>list_modelPhieuDapAn_Navigation;
    static AdapterPhieuDapAn_Navigation adapterPhieuDapAnNavigation;
    ListView lvnavigationView_MHC;      // nằm trong navigationViewManHinhChinh

    Toolbar toolbar_main;
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<MyFragment> fragmentList;
    AdapterMyFragment adapterMyFragment;

    //
    TextView txt_cauhoihientai; // set tex 1/40 or 2/40


    MyFragment fragment;
    Dialog dialogTinhDiem;
    // tính thời gian
    TextView count_timeText;
    CountDownTimer countDownTimer;
    long timelambai;
    int  phut;
    int giay;
    //
    TextView txt_lambai_made;


    ModelDeThi modelDeThi;
    int soCauDung = 0;

    public static  int socauhoi;
    public  static int lop = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambai);

        Intent intent = getIntent();
        modelDeThi = (ModelDeThi) intent.getSerializableExtra("modelDeThi");
        timelambai= (long) ((modelDeThi.getSocauhoi()*1.5)*60000);
        socauhoi = DeThiActivity.listmodelCauHoiJsons.size();
        //Toast.makeText(this, "socauhoi = " + socauhoi, Toast.LENGTH_SHORT).show();
        for(int  i  = 0 ; i < socauhoi ; i ++){
            DeThiActivity.arraySaveAnswer.add(new ModelSaveAnswer(false, 0));
        }
        //chạy thời gian làm bài
        count_timeText = findViewById(R.id.count_timeText);
        // 60000 = 1p
        countDownTimer = new CountDownTimer(timelambai, 1000) {

            public void onTick(long millisUntilFinished) {
                //count_timeText.setText("" + millisUntilFinished / 1000);
                String text;
                  phut= (int) (millisUntilFinished / 60000);
                  giay   = (int) (millisUntilFinished % 60000 / 1000);

                text = phut +":";
                if(giay < 10){
                    text = text +"0"+giay;
                }
                else {
                    text = text + giay +"";
                }


                count_timeText.setText(text);

            }

            public void onFinish() {
                xulymoDialogTinhDiem();
            }
        }.start();

        addControls();
        addEvents();
        ActionBar();
    }




    private void addControls() {
        lop = modelDeThi.getLop();
        txt_lambai_made = findViewById(R.id.txt_lambai_made);
        txt_lambai_made.setText(getResources().getString(R.string.ma)+" " + modelDeThi.getMade().trim());
        toolbar_main = findViewById(R.id.toolBarMain);

        //
        navigationView = findViewById(R.id.navigatinonView);
        drawerlayout = findViewById(R.id.drawerlayout);
        //
        txt_cauhoihientai = findViewById(R.id.txt_cauhoihientai);
        //
        lvnavigationView_MHC = findViewById(R.id.lvnavigationView_MHC);
        list_modelPhieuDapAn_Navigation = new ArrayList<>();
        for(int  i = 0 ; i < socauhoi ; i ++){
            //fake data Navigation
            ModelPhieuDapAn_Naviagation dapAn = new ModelPhieuDapAn_Naviagation(i,false,false,false,false);
            list_modelPhieuDapAn_Navigation.add(dapAn);
        }
        adapterPhieuDapAnNavigation = new AdapterPhieuDapAn_Navigation(this,R.layout.item_multiple_choice_navigation,list_modelPhieuDapAn_Navigation);
        lvnavigationView_MHC.setAdapter(adapterPhieuDapAnNavigation);
        //




        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.sliding_tabs);
        fragmentList = new ArrayList<>();
        genFragmentList();
        adapterMyFragment = new AdapterMyFragment(getSupportFragmentManager(),this,fragmentList);
        viewPager.setAdapter(adapterMyFragment);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void addEvents() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                txt_cauhoihientai.setText(i+1+"\\"+socauhoi);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @SuppressLint("RestrictedApi")
    private void ActionBar() {
     setSupportActionBar(toolbar_main);
     getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_main.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // bắt sự kiện để mở drawerlayoutMHC
        toolbar_main.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {

        // đóng listView


        if((drawerlayout.isDrawerOpen(GravityCompat.START))){

            drawerlayout.closeDrawer(GravityCompat.START);
        }else {
            xulymoDialogNopBai();

        }


    }

    @Override // tạo menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.mnu_done,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        xulymoDialogNopBai();

        return super.onOptionsItemSelected(item);
    }

    private void xulymoDialogNopBai() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LamBaiActivity.this);
        builder.setTitle(R.string.nopbai);
        builder.setMessage(R.string.banchacchanmuonnopbai);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(LamBaiActivity.this, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.dongy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(LamBaiActivity.this, "ĐỒNg Ý", Toast.LENGTH_SHORT).show();

                countDownTimer.onFinish();
                //get time
                Calendar calendar = Calendar.getInstance();
                String strDateFormat24 = "HH:mm";
                SimpleDateFormat sdf1 = new SimpleDateFormat(strDateFormat24);
                String time = sdf1.format(calendar.getTime());
                //get day
                String strDateFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat);
                String day = sdf2.format(calendar.getTime());

                //get time đã làm bài
                long timedalambai = timelambai -(phut*60000+giay*1000);

                int phutdalam= (int) (timedalambai / 60000);
                int giaydalam  = (int) (timedalambai % 60000 / 1000);
                String s;
                s = phutdalam +":";
                if(giaydalam < 10){
                    s = s +"0"+giaydalam;
                }
                else {
                    s = s + giaydalam +"";
                }

                guidevaoHistory(time,day,s);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void guidevaoHistory(final String time , final String day, final  String s) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkURL.urlsendhistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // nhận kqua từ PHP trả về  sau khi hàm Map được gửi lên
                        // nếu thành công thì PHP trả về  success . vậy mình lấy cái này để đem đi so sánh
                        if(response.toString().trim().equals("success")){
                            //Toast.makeText(LamBaiActivity.this, "Gửi history thành công", Toast.LENGTH_SHORT).show();
                        }
                        else

                            Toast.makeText(LamBaiActivity.this, "LOI", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LamBaiActivity.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                        Log.e("LOI",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameDe",modelDeThi.getNamedethi());
                params.put("soCauDung", String.valueOf(soCauDung));
                params.put("day",day);
                params.put("time",time);
                params.put("timeLamBai",s);
                params.put("made",modelDeThi.getMade());
                params.put("idUsser", String.valueOf(UiActivity.idUsser));
                params.put("socauhoi", String.valueOf(modelDeThi.getSocauhoi()));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void genFragmentList() {

        for(int  i = 0 ; i < socauhoi ; i ++){


            fragment = new MyFragment();
            fragmentList.add(fragment);
        }
    }


    public static void  UpdateAnswer (int cau , int dapanduocchon){

        ModelPhieuDapAn_Naviagation modelPhieuDapAnNaviagation = list_modelPhieuDapAn_Navigation.get(cau);

        switch (dapanduocchon){

            case 1:{
                modelPhieuDapAnNaviagation.setDapanA(true);
                modelPhieuDapAnNaviagation.setDapanB(false);
                modelPhieuDapAnNaviagation.setDapanC(false);
                modelPhieuDapAnNaviagation.setDapanD(false);
                break;
            }

            case 2:{
                modelPhieuDapAnNaviagation.setDapanB(true);
                modelPhieuDapAnNaviagation.setDapanA(false);
                modelPhieuDapAnNaviagation.setDapanC(false);
                modelPhieuDapAnNaviagation.setDapanD(false);
                break;
            }

            case 3:{
                modelPhieuDapAnNaviagation.setDapanC(true);
                modelPhieuDapAnNaviagation.setDapanA(false);
                modelPhieuDapAnNaviagation.setDapanB(false);
                modelPhieuDapAnNaviagation.setDapanD(false);
                break;
            }

            case 4:{
                modelPhieuDapAnNaviagation.setDapanD(true);
                modelPhieuDapAnNaviagation.setDapanA(false);
                modelPhieuDapAnNaviagation.setDapanB(false);
                modelPhieuDapAnNaviagation.setDapanC(false);
                break;
            }

        }
        adapterPhieuDapAnNavigation.notifyDataSetChanged();



    }
    private void xulymoDialogTinhDiem(){

//        Toast.makeText(this, DeThiActivity.listmodelCauHoiJsons.size()+"", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, DeThiActivity.arraySaveAnswer.size()+"", Toast.LENGTH_SHORT).show();
        if(DeThiActivity.listmodelCauHoiJsons.size() == socauhoi){
            for(int i = 0 ; i < DeThiActivity.arraySaveAnswer.size() ; i ++){
                // nếu sai ở đây xem lại data trên locohosst ( xem lại mã đề )
                ModelCauHoi_Json modelCauHoi_json = DeThiActivity.listmodelCauHoiJsons.get(i);
                ModelSaveAnswer modelSaveAnswer = DeThiActivity.arraySaveAnswer.get(i);
                if(modelCauHoi_json.getDapan() == modelSaveAnswer.getNumber_answer()){
                    soCauDung++;
                }
            }
        }
        else {
            int x =DeThiActivity.listmodelCauHoiJsons.size();
            Toast.makeText(this, "Có " +x +" câu hỏi , không đủ 40 câu xem lại data trên locaohosst", Toast.LENGTH_SHORT).show();
        }

        count_timeText.setText("Hết giờ !");
        countDownTimer.cancel();
        dialogTinhDiem = new Dialog(LamBaiActivity.this);
        dialogTinhDiem.requestWindowFeature(Window.FEATURE_NO_TITLE);// no title
        dialogTinhDiem.setContentView(R.layout.custom_dialog_tinhdiem);
        dialogTinhDiem.setCanceledOnTouchOutside(false); // click ra ngoài có tắt DiaLog không
        dialogTinhDiem.setCancelable(false);

        Button btn_dialoglamdemoi = dialogTinhDiem.findViewById(R.id.btn_dialoglamdemoi);
        Button btn_dialogxemketqua = dialogTinhDiem.findViewById(R.id.btn_dialogxemketqua);
        TextView txt_dialogsocaudung    = dialogTinhDiem.findViewById(R.id.txt_dialogsocaudung);
        TextView txt_dialogsodiem    = dialogTinhDiem.findViewById(R.id.txt_dialogsodiem);
        txt_dialogsocaudung.setText(soCauDung+"/" + socauhoi);
        float d = (float)Math.round(((float)(soCauDung*10)/(float) socauhoi)*100)/100;

        if(soCauDung == 0){
            txt_dialogsodiem.setText(d +" điểm");
        }
        else{

            txt_dialogsodiem.setText(d +" điểm");
        }

        dialogTinhDiem.show();

        btn_dialoglamdemoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTinhDiem.cancel();
                Intent intent = new Intent(LamBaiActivity.this, DeThiActivity.class);
                intent.putExtra("lop",lop);
                LamBaiActivity.this.startActivity(intent);
                LamBaiActivity.this.finish();

            }
        });
        btn_dialogxemketqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LamBaiActivity.this, ChamDiemActivity.class);
                intent.putExtra("modelDeThi",modelDeThi);
                LamBaiActivity.this.startActivity(intent);
                LamBaiActivity.this.finish();
            }
        });
    }


}
