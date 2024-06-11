package com.example.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class user_requestacc extends AppCompatActivity implements JsonResponse {
    EditText e1, e2, e3, e4, e5, e6;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String det, date,ifsc;
    String[] details, dates, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_requestacc);

        e1 = (EditText) findViewById(R.id.det);
        e2 = (EditText) findViewById(R.id.date);

        e3 = (EditText) findViewById(R.id.ifsc);

        l1 = (ListView) findViewById(R.id.list);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1 = (Button) findViewById(R.id.request);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) user_requestacc.this;
        String q = "/viewuser_requestacc?login_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                det = e1.getText().toString();
                date = e2.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) user_requestacc.this;
                String q = "/user_requestacc?login_id=" + sh.getString("log_id", "") +"&det="+det+"&date="+date;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_requestacc")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), user_requestacc.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("viewuser_requestacc"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    details=new String[ja1.length()];

                    dates=new String[ja1.length()];

                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        details[i]=ja1.getJSONObject(i).getString("details");

                        dates[i]=ja1.getJSONObject(i).getString("date");




                        value[i]="details: "+details[i]+"\ndate: "+dates[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
    }
