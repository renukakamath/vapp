package com.example.vapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse {
    EditText e1,e2;
    Button b1;
    public static String user,pass,logid,usertype;
    SharedPreferences sh;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etunm);
        e2=(EditText)findViewById(R.id.etpass);
        b1=(Button)findViewById(R.id.btnlogin);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=e1.getText().toString();
                pass=e2.getText().toString();
                if(user.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your username");
                    e1.setFocusable(true);
                }
                else if(pass.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your password");
                    e2.setFocusable(true);
                }
                else
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse)Login.this;
                    String q="/logins?username=" + user + "&password=" + pass;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                logid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("user_type");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
                e.putString("types",usertype);
                e.commit();

                if(usertype.equals("blind"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));
                }
//                else if(usertype.equals("staff"))
//                {
//                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),Stafffhome.class));
//                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Ipsettings.class);
        startActivity(b);
    }
    }
