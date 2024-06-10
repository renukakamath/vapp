package com.example.vapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewclass extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] class_id,classs,value;
    public static  String cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclass);


        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewclass.this;
        String q = "/Viewclass?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                classs= new String[ja1.length()];
                class_id= new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    classs[i] = ja1.getJSONObject(i).getString("class");

                    class_id[i] = ja1.getJSONObject(i).getString("class_id");



                    value[i] = "class:" + classs[i]   ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        cid=class_id[i];
        final CharSequence[] items = {"View Subject","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewclass.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View Subject")) {
                    startActivity(new Intent(getApplicationContext(), Viewsubject.class));
                }


                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
    }

