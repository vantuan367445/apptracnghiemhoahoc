package com.example.tuanvatvo.demo2.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.tuanvatvo.demo2.common.CheckConnectiom;
import com.example.tuanvatvo.demo2.common.LinkURL;
import com.example.tuanvatvo.demo2.model.UserLogIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UiActivity extends AppCompatActivity {

    Button btn_createAcc,btn_lognin;
    EditText edt_useremaillognin,edt_userpasslognin;
    Dialog dialog;
    UserLogIn userLogIn_Create;

    public static int idUsser = -1;

    public static ArrayList<UserLogIn> arrayUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        if(CheckConnectiom.checkCon(getApplicationContext())){
            addControls();
            getUser();
            addEvents();
        }else{
            CheckConnectiom.toastCheckConnection(getApplicationContext());
        }


    }



    private void addControls() {
        btn_createAcc = findViewById(R.id.btn_createAcc);
        edt_useremaillognin = findViewById(R.id.edt_useremaillognin);
        edt_userpasslognin = findViewById(R.id.edt_userpasslognin);
        btn_lognin = findViewById(R.id.btn_lognin);
        userLogIn_Create = new UserLogIn();
        arrayUser = new ArrayList<>();

    }
    private void  addEvents(){
        btn_createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digLogCreateAcc();

            }
        });
        btn_lognin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = edt_useremaillognin.getText().toString().trim();
                String pass  = edt_userpasslognin.getText().toString().trim();


                int check = 0;
                for(int i = 0 ; i < arrayUser.size() ; i ++ ){
                    if(email.equals(arrayUser.get(i).getEmail()) &&
                            pass.equals(arrayUser.get(i).getPass())){
                        check = 1;
                        idUsser = i+1; // vì id trên Locohost bắt đàu
                        break;
                    }
                }
                if(check == 1 ){
                    //
                    // Toast.makeText(UiActivity.this, "Đăng nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UiActivity.this,TrangChuActivity.class);
                    startActivity(intent);
                    UiActivity.this.finish();
                }
                else {
                    Toast.makeText(UiActivity.this, R.string.thongtindangnhapkhongdung, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private  void digLogCreateAcc(){
        dialog = new Dialog(UiActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_create);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        ImageView ima_cancel_dialog_createacc;
        final EditText edt_yourname_dialog_createacc,edt_youremail_dialog_createacc,edt_yourpass_dialog_createacc;
        Button btn_dialog_createacc;
        ima_cancel_dialog_createacc = dialog.findViewById(R.id.ima_cancel_dialog_createacc);
        edt_yourname_dialog_createacc = dialog.findViewById(R.id.edt_yourname_dialog_createacc);
        edt_youremail_dialog_createacc = dialog.findViewById(R.id.edt_youremail_dialog_createacc);
        edt_yourpass_dialog_createacc = dialog.findViewById(R.id.edt_yourpass_dialog_createacc);
        btn_dialog_createacc = dialog.findViewById(R.id.btn_dialog_createacc);



        dialog.show();

        ima_cancel_dialog_createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btn_dialog_createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_dialog = edt_yourname_dialog_createacc.getText().toString().trim();
                String email_dialog = edt_youremail_dialog_createacc.getText().toString().trim();
                String pass_dialog = edt_yourpass_dialog_createacc.getText().toString().trim();
                if(name_dialog.isEmpty() || email_dialog.isEmpty()|| pass_dialog.isEmpty()){
                    Toast.makeText(UiActivity.this, R.string.vuilongdienduthongtin, Toast.LENGTH_SHORT).show();

                }
                else {
                    int check = 0; // khong bi trung
                    for(int  i = 0 ; i  < arrayUser.size() ; i ++){
                        if(email_dialog.equalsIgnoreCase(arrayUser.get(i).getEmail())){
                            Toast.makeText(UiActivity.this, R.string.emaildatontai, Toast.LENGTH_SHORT).show();
                            check = 1; // bi trung
                            break;
                        }

                    }
                    if(check == 0){

                        userLogIn_Create.setEmail(email_dialog);
                        userLogIn_Create.setName(name_dialog);
                        userLogIn_Create.setPass(pass_dialog);
                        guiThongtinuser();


                    }
                }



            }
        });

    }

    private void guiThongtinuser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkURL.urlsendUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // nhận kqua từ PHP trả về  sau khi hàm Map được gửi lên
                        // nếu thành công thì PHP trả về  success . vậy mình lấy cái này để đem đi so sánh

                        if(response.toString().trim().equals("success")){
                            Toast.makeText(UiActivity.this, R.string.taothanhcong, Toast.LENGTH_SHORT).show();
                            edt_useremaillognin.setText(userLogIn_Create.getEmail());
                            edt_userpasslognin.setText(userLogIn_Create.getPass());
                            getUser();
                            dialog.dismiss();
                        }
                        else
                            Toast.makeText(UiActivity.this, "LOI", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UiActivity.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                        Log.e("LOI",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",userLogIn_Create.getName());
                params.put("email",userLogIn_Create.getEmail());
                params.put("pass",userLogIn_Create.getPass());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(LinkURL.urlgetUser,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(UiActivity.this, response.toString()+"", Toast.LENGTH_SHORT).show();
                        if(response!=null){
                            arrayUser.clear();
                            for(int  i = 0 ; i < response.length() ; i ++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    arrayUser.add(new UserLogIn(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("email"),
                                            jsonObject.getString("pass")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(UiActivity.this, e.toString()+"", Toast.LENGTH_SHORT).show();
                                    Log.e("LOI",e.toString());
                                }
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UiActivity.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                        Log.e("LOI",error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
