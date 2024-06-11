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

public class user_transfer extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    public static String facc,toacc,amo;
    String[] balances,value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transfer);


        e1=(EditText)findViewById(R.id.facc);
        e2=(EditText)findViewById(R.id.toacc);
        e3=(EditText)findViewById(R.id.amo);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1=(Button) findViewById(R.id.transfer);



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) user_transfer.this;
        String q = "/viewmyaccount?login_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facc=e1.getText().toString();
                toacc=e2.getText().toString();
                amo=e3.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) user_transfer.this;
                String q = "/user_transfer?login_id="+ sh.getString("log_id", "") +"&facc=" +facc +"&toacc="+toacc+"&amo="+amo+"&latitude="+LocationService.lati+"&longitude="+LocationService.logi;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_transfer")) {

                String status = jo.getString("status");
                Log.d("pearl", status);




                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), user_makepayment.class));

                } else {

                    Toast.makeText(getApplicationContext(), "Transaction Blocked", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));
                }
            }

            if(method.equalsIgnoreCase("viewmyaccount")) {
                e1.setText(jo.getString("facc"));
                e2.setText(jo.getString("toacc"));
                e3.setText(jo.getString("amo"));

            }


        }


        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}