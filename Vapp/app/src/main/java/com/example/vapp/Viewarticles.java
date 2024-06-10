package com.example.vapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class Viewarticles extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] articles,details,value;
    public static String deta;

    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewarticles);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);

        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        }
                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            });
        }

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewarticles.this;
        String q = "/Viewarticle?log_id=" +sh.getString("log_id", "")+"&sid="+Viewsubject.sid;
        q = q.replace(" ", "%20");
        JR.execute(q);



    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                articles= new String[ja1.length()];
                details= new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    articles[i] = ja1.getJSONObject(i).getString("article");

                    details[i] = ja1.getJSONObject(i).getString("details");



                    value[i] = "articles:" + articles[i] +"\n details:" + details[i]    ;

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
        deta=details[i];

        if (tts != null) {
            tts.speak(deta, TextToSpeech.QUEUE_FLUSH,null);
        }

    }
}
