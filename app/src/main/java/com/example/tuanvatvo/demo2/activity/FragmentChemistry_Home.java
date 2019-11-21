package com.example.tuanvatvo.demo2.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.adapter.AdapterChemistry;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.ModelChemistry;
import com.example.tuanvatvo.demo2.model.ModelItemHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentChemistry_Home extends Fragment {
    public  static  ArrayList<ModelItemHistory> arratHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        ArrayList<ModelChemistry> arrayList = getFakeData();

        AdapterChemistry adapterChemistry = new AdapterChemistry(arrayList,getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChemistry);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterChemistry);

        arratHistory = new ArrayList<>();
        getModelHistory();


        return  view;
    }


    private ArrayList<ModelChemistry> getFakeData() {
        ArrayList<ModelChemistry> lisChemistry = new ArrayList<>();
        //
        //

        //lisChemistry.add(new ModelChemistry(8,"HÓA 8", LinkURL.ip+"/chemistry/owl_image/owl6.png",false,getResources().getString(R.string.mota1),0));
        //lisChemistry.add(new ModelChemistry(9,"HÓA 9",LinkURL.ip+"/chemistry/owl_image/owl5.png",false,getResources().getString(R.string.mota2),1));
        lisChemistry.add(new ModelChemistry(10,"HÓA 10",LinkURL.ip+"/chemistry/owl_image/owl3.png",false,getResources().getString(R.string.mota3),2));
        lisChemistry.add(new ModelChemistry(11,"HÓA 11",LinkURL.ip+"/chemistry/owl_image/owl4.png",false,getResources().getString(R.string.mota4),3));
        lisChemistry.add(new ModelChemistry(12,"HÓA 12",LinkURL.ip+"/chemistry/owl_image/owl1.png",false,getResources().getString(R.string.mota5),4));
        lisChemistry.add(new ModelChemistry(13,"Luyện thi ĐH",LinkURL.ip+"/chemistry/owl_image/owl2.png",false,getResources().getString(R.string.mota6),5));
        return lisChemistry;
    }

    public  void getModelHistory() {


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkURL.urlgetHistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            arratHistory.clear();

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = null;
                                for(int i = 0 ; i < jsonArray.length() ; i ++){
                                    jsonObject = jsonArray.getJSONObject(i);
                                    arratHistory.add(new ModelItemHistory(
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
//                                adapterHistory.notifyDataSetChanged();
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
