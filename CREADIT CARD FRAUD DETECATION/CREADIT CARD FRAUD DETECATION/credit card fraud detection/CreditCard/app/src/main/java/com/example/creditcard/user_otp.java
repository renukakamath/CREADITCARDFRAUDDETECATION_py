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

public class user_otp extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5,e6;
    Button b1;

    SharedPreferences sh;
    String otp;
    String[] balances,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        e1=(EditText)findViewById(R.id.number);





        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1=(Button) findViewById(R.id.otp);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp=e1.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) user_otp.this;
                String q = "/user_otp?login_id="+ sh.getString("log_id", "") +"&otp=" +otp+"&facc="+user_transfer.facc+"&toacc="+user_transfer.toacc+"&amo="+user_transfer.amo+"&Latitude="+LocationService.lati+"&longitude="+LocationService.logi;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_otp")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }



        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
