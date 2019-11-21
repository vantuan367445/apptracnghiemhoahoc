package com.example.tuanvatvo.demo2.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.tuanvatvo.demo2.adapter.AdapterHistory;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelItemHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentHistory_Home extends Fragment {
    View view;
    Toolbar toobar_history;

    int check = 0 ; // Lich su rỗng
    TextView txt_ronghistory;

    ListView lv_history;

    AdapterHistory adapterHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history,container,false);


        addControls();
        getModelHistory();
        if(FragmentChemistry_Home.arratHistory.size() > 0){
            check = 1; // history có dữ liệu
        }

        ActionBar();

        if(check == 1){
            txt_ronghistory.setVisibility(View.INVISIBLE);
        }
        else{
            txt_ronghistory.setVisibility(View.VISIBLE);

        }
        return view;
    }

    private void addControls() {
        txt_ronghistory = view.findViewById(R.id.txt_ronghistory);
        toobar_history = view.findViewById(R.id.toobar_history);
        lv_history = view.findViewById(R.id.lv_history);
//        arratHistory = new ArrayList<>();
        adapterHistory = new AdapterHistory(getActivity(),R.layout.item_history,FragmentChemistry_Home.arratHistory);
        lv_history.setAdapter(adapterHistory);

    }

//
    private void ActionBar() {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toobar_history);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public  void getModelHistory() {


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkURL.urlgetHistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            FragmentChemistry_Home.arratHistory.clear();

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = null;
                                for(int i = 0 ; i < jsonArray.length() ; i ++){
                                    jsonObject = jsonArray.getJSONObject(i);
                                    FragmentChemistry_Home.arratHistory.add(new ModelItemHistory(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("nameDe"),
                                            jsonObject.getInt("soCauDung"),
                                            jsonObject.getString("day"),
                                            jsonObject.getString("time"),
                                            jsonObject.getString("timeLamBai"),
                                            jsonObject.getString("made"),
                                            jsonObject.getInt("idUsser"),
                                            jsonObject.getInt("socauhoi")
                                    ));
                                }
                                adapterHistory.notifyDataSetChanged();
                                if(adapterHistory.getCount() > 0){
                                    txt_ronghistory.setVisibility(View.INVISIBLE);
                                }
                                else
                                    txt_ronghistory.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), e.toString()+"", Toast.LENGTH_SHORT).show();
                                Log.e("LOI",e.toString());
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString()+"", Toast.LENGTH_SHORT).show();
                        Log.e("LOI",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("idUsser",String.valueOf(UiActivity.idUsser));
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }


}
