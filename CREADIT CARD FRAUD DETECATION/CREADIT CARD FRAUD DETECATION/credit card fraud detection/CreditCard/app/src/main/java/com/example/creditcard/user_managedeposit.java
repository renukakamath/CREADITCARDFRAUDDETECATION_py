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

public class user_managedeposit extends AppCompatActivity implements JsonResponse  {
    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
//    ListView l1;
    SharedPreferences sh;
    String balance;
    String[] balances,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managedeposit);

        e1=(EditText)findViewById(R.id.bal);


//        l1=(ListView)findViewById(R.id.list);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1=(Button) findViewById(R.id.acc);




        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) user_managedeposit.this;
        String q = "/viewuser_managedeposit?login_id="+ sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance=e1.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) user_managedeposit.this;
                String q = "/user_managedeposit?login_id="+ sh.getString("log_id", "") +"&balance=" +balance+"&cid="+user_managecreditcard.cid;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_managedeposit")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), user_managecreditcard.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("viewuser_managedeposit"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    balances=new String[ja1.length()];



                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {

                        balances[i]=ja1.getJSONObject(i).getString("balance");



                        value[i]="balance: "+balances[i];

                    }
//                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
//                    l1.setAdapter(ar);
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
        Intent b=new Intent(getApplicationContext(),user_managecreditcard.class);
        startActivity(b);
    }
}