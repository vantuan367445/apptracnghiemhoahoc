package com.example.tuanvatvo.demo2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.adapter.AdapterDeThi;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelCauHoi_Json;
import com.example.tuanvatvo.demo2.model.ModelDeThi;
import com.example.tuanvatvo.demo2.model.ModelSaveAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeThiActivity extends AppCompatActivity {

    Toolbar toolbar_kiemtra;
    ArrayList<ModelDeThi> arrayDeThi;
    AdapterDeThi adapterDeThi;
    RecyclerView recyclerViewDeThi;
    ProgressDialog dialog;


    public static ArrayList<ModelCauHoi_Json> listmodelCauHoiJsons;
    public static ArrayList<ModelSaveAnswer> arraySaveAnswer ; // lưu câu trả lời của người thi

    int lop = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_thi);

        //Toast.makeText(this, "De thi activity", Toast.LENGTH_SHORT).show();
        if(lop == 0){
            Intent intent = getIntent();
            lop = intent.getIntExtra("lop",0);
            //Toast.makeText(this, "lop = " + lop, Toast.LENGTH_SHORT).show();

        }

        addControls();
        addEvents();
        ActionBar();

    }

    private void addEvents() {
    }

    private void ActionBar() {

        //Toast.makeText(this, lop+"", Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar_kiemtra);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // mũi tên trên toolBar đẻ quay trở lại trang chính
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // ẩn title

        toolbar_kiemtra.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //toolbar_kiemtra.setTitle("Đề " + lop);
    }

    private void addControls() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data


        arrayDeThi = new ArrayList<>();
        recyclerViewDeThi = findViewById(R.id.recyclerViewDeThi);
        toolbar_kiemtra = findViewById(R.id.toobar_kiemtra);

        arraySaveAnswer = new ArrayList<>();



        adapterDeThi = new AdapterDeThi(arrayDeThi,this);
        recyclerViewDeThi.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewDeThi.setHasFixedSize(true);
        recyclerViewDeThi.setAdapter(adapterDeThi);

        // get Đề Thi bàng AsyncTask
       // new ReadJsonTask().execute(LinkURL.urlgetDeThi);
        // get đề thi bằng Json
            getDeThiJson(lop);
            adapterDeThi.notifyDataSetChanged();
    }



    class ReadJsonTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            // đọc xong và trả keesy qua trong onPostExecute
            dialog.dismiss();
            arrayDeThi.clear();
            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i = 0 ; i < jsonArray.length() ; i ++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arrayDeThi.add(new ModelDeThi(
                            jsonObject.getInt("id"),
                            jsonObject.getString("namede"),
                            jsonObject.getInt("lop"),
                            jsonObject.getString("made"),
                            jsonObject.getInt("socauhoi")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("LOI",e.toString());
                Toast.makeText(getApplicationContext(), e.toString()+"", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(DeThiActivity.this, "s = "+s, Toast.LENGTH_SHORT).show();
            adapterDeThi.notifyDataSetChanged();
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            // xử lý
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while((line = bufferedReader.readLine()) != null){ // nếu còn đọc được
                stringBuilder.append(line);
                }
                bufferedReader.close();
                // sau khi đoc xog thì biến  stringBuilder có giá trị ta return biến stringBuilder


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }
    //
    public void  getDeThiJson(final int lop){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, LinkURL.urlgetDeThi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response!=null){
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = null;
                                for(int  i = 0 ; i < jsonArray.length() ; i ++){
                                    jsonObject = jsonArray.getJSONObject(i);
                                    arrayDeThi.add(new ModelDeThi(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("namede"),
                                            jsonObject.getInt("lop"),
                                            jsonObject.getString("made"),
                                            jsonObject.getInt("socauhoi")));

                                }
                                adapterDeThi.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("LOI",e.toString()+"");
                                Toast.makeText(DeThiActivity.this, e.toString()+"", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOI",error.toString()+"");
                        Toast.makeText(DeThiActivity.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("lop", String.valueOf(lop));
                return param;
            }
        };
        requestQueue.add(request);
    }

}
