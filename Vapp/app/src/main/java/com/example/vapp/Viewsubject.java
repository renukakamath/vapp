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

public class Viewsubject extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] subject_id,subject,value;
    public  static  String sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsubject);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewsubject.this;
        String q = "/Viewsubject?log_id=" +sh.getString("log_id", "")+"&cid="+Viewclass.cid;
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

                subject= new String[ja1.length()];

                subject_id= new String[ja1.length()];

                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    subject[i] = ja1.getJSONObject(i).getString("subject");
                    subject_id[i] = ja1.getJSONObject(i).getString("subject_id");




                    value[i] = "subject:" + subject[i]   ;

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

        sid=subject_id[i];
        final CharSequence[] items = {"View Notes","View Articles","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewsubject.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View Notes")) {
                    startActivity(new Intent(getApplicationContext(), Viewnotes.class));
                }


                else if(items[item].equals("View Articles")) {

                    startActivity(new Intent(getApplicationContext(),Viewarticles.class));


                }else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
    }
