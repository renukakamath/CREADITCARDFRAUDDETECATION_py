package com.example.creditcard;

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
    TextView t1;
    String uname,password,logid,usertype;
    SharedPreferences sh;
    public static String log_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.uname);
        e2=(EditText) findViewById(R.id.password);
        b1=(Button) findViewById(R.id.login);
        t1=(TextView) findViewById(R.id.signup);

        startService(new Intent(getApplicationContext(),LocationService.class));



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = e1.getText().toString();
                password = e2.getText().toString();
                if(uname.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your username");
                    e1.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your password");
                    e2.setFocusable(true);
                }else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/logins?username=" + uname + "&password=" + password;
                    q = q.replace(" ", "%20");
                    JR.execute(q);


//                Toast.makeText(getApplicationContext(),"Username : "+uname+"\nPassword : "+password, Toast.LENGTH_LONG).show();
                }

            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
//                Intent i=new Intent(getApplicationContext(),Registration.class);
//                startActivity(i);

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
                usertype = ja1.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
                e.commit();

                if(usertype.equals("user"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));
                }



            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
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
