package com.example.creditcard;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class user_managecreditcard extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1, e2, e3, e4, e5, e6;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String card, month, cvv, pin_no, ifsc, bank;
    String[] cardnum, months, pin_nos, date, balance, bank_num, ifsc_code, value,creditcard_id;
    public static String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managecreditcard);
        e1 = (EditText) findViewById(R.id.card);
        e2 = (EditText) findViewById(R.id.month);
        e3 = (EditText) findViewById(R.id.cvv);
        e4 = (EditText) findViewById(R.id.pin_no);
        e5 = (EditText) findViewById(R.id.ifsc);

        e6 = (EditText) findViewById(R.id.bank);

        l1 = (ListView) findViewById(R.id.list);

        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1 = (Button) findViewById(R.id.actbutton);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) user_managecreditcard.this;
        String q = "/Viewuser_managecreditcard?login_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card = e1.getText().toString();
                month = e2.getText().toString();
                cvv = e3.getText().toString();
                pin_no = e3.getText().toString();
                ifsc = e5.getText().toString();

                bank = e6.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) user_managecreditcard.this;
                String q = "/user_managecreditcard?login_id=" + sh.getString("log_id", "") + "&card=" + card + "&month=" + month + "&cvv=" + cvv + "&pin_no=" + pin_no + "&ifsc=" + ifsc + "&bank=" + bank;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("user_managecreditcard")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), user_managecreditcard.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            } else if (method.equalsIgnoreCase("Viewuser_managecreditcard")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    cardnum = new String[ja1.length()];
                    months = new String[ja1.length()];
                    pin_nos = new String[ja1.length()];

                    balance = new String[ja1.length()];

                    bank_num = new String[ja1.length()];
                    ifsc_code = new String[ja1.length()];
                    date = new String[ja1.length()];
                    creditcard_id= new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        cardnum[i] = ja1.getJSONObject(i).getString("cardnum");
                        months[i] = ja1.getJSONObject(i).getString("month");
                        pin_nos[i] = ja1.getJSONObject(i).getString("pin_no");
                        bank_num[i] = ja1.getJSONObject(i).getString("bank_num");
                        balance[i] = ja1.getJSONObject(i).getString("balance");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        ifsc_code[i] = ja1.getJSONObject(i).getString("ifsc_code");
                        creditcard_id[i] = ja1.getJSONObject(i).getString("creditcard_id");

                        value[i] = "cardnum: " + cardnum[i] + "\nmonths: " + months[i] + "\npin_nos: " + pin_nos[i] + "\nbank_num: " + bank_num[i] + "\nbalance: " + balance[i] + "\ndate: " + date[i] + "\nifsc_code: " + ifsc_code[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                    l1.setAdapter(ar);
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Userhome.class);
        startActivity(b);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cid=creditcard_id[i];
        final CharSequence[] items = {"Add Deposit", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(user_managecreditcard.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add Deposit")) {

                    startActivity(new Intent(getApplicationContext(), user_managedeposit.class));


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }

}









