package com.example.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class user_viewtransaction extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] faccount,taccount,  amount, date, value;
    public static String oid, iid, sid, amt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_viewtransaction);

        l1=(ListView) findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) user_viewtransaction.this;
        String q = "/user_viewtransaction?login_id="+sh.getString("log_id","" );
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
                faccount = new String[ja1.length()];
                taccount = new String[ja1.length()];
                amount = new String[ja1.length()];

                value = new String[ja1.length()];

                date= new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    faccount[i] = ja1.getJSONObject(i).getString("faccount");
                    taccount[i] = ja1.getJSONObject(i).getString("taccount");
                    amount[i] = ja1.getJSONObject(i).getString("amount");

                    date[i] = ja1.getJSONObject(i).getString("date");




                    value[i] = "faccount:" + faccount[i] + "\ntaccount: " + taccount[i] + "\n amount: " + amount[i]+ "\n date: " + date[i];
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);


                l1.setAdapter(ar);


            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}