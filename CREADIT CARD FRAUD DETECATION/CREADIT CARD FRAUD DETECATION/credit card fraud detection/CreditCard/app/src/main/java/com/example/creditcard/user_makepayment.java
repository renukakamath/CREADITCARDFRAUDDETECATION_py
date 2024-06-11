package com.example.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class user_makepayment extends AppCompatActivity implements JsonResponse {

    Button b1;
    EditText e1, e2, e3, e4;
    public static String pamount, pay_id;
    String acno, name, cvv, date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_makepayment);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1 = (Button) findViewById(R.id.btpay);

//        e1 = findViewById(R.id.acno);
        e2 = findViewById(R.id.cvv);
        e3 = findViewById(R.id.date);


        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(user_makepayment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                e3.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        e4 = findViewById(R.id.name);





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                acno = e1.getText().toString();
                cvv = e2.getText().toString();
                date = e3.getText().toString();
                name = e4.getText().toString();

               if (cvv.equalsIgnoreCase("") || cvv.length() != 3) {
                    e2.setError("please enter 3 digit Cvv");
                    e2.setFocusable(true);
                } else if (date.equalsIgnoreCase("")) {
                    e3.setError("enter Expiery date");
                    e3.setFocusable(true);
                } else if (name.equalsIgnoreCase("")) {
                    e4.setError("please Enter your Name");
                    e4.setFocusable(true);
                } else {


                    JsonReq JR1 = new JsonReq();
                    JR1.json_response = (JsonResponse) user_makepayment.this;
                    String q1 = "/Makepayment?book_id=" + sh.getString("ordermaster_id","")+ "&amts=" + sh.getString("total","")+"&log_id=" +sh.getString("log_id", "")+"&account="+user_transfer.facc;
                    q1 = q1.replace(" ", "%20");
                    JR1.execute(q1);


                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_payment")) {

                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), user_otp.class));

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


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
    }
