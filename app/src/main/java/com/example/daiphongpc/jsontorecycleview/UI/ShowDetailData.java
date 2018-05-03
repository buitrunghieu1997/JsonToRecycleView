package com.example.daiphongpc.jsontorecycleview.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.daiphongpc.jsontorecycleview.R;

public class ShowDetailData extends AppCompatActivity {
    private TextView data_id;
    private TextView data_name;
    private TextView data_username;
    private TextView data_email;
    private TextView data_address;
    private TextView data_street;
    private TextView data_city;
    private TextView data_zipcode;
    private TextView data_suite;
    private TextView data_geo;
    private TextView data_lat;
    private TextView data_lng;
    private TextView data_phone;
    private TextView data_website;
    private TextView data_company;
    private TextView data_company_name;
    private TextView data_catch_phrase;
    private TextView data_bs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);
        getSupportActionBar().hide();

        data_id = (TextView) findViewById(R.id.id);
        data_name = (TextView) findViewById(R.id.name);
        data_username = (TextView) findViewById(R.id.username);
        data_email = (TextView) findViewById(R.id.email);
        data_address = (TextView) findViewById(R.id.address);
        data_street = (TextView) findViewById(R.id.street);
        data_city = (TextView) findViewById(R.id.city);
        data_zipcode = (TextView) findViewById(R.id.zipcode);
        data_suite = (TextView) findViewById(R.id.suite);
        data_geo = (TextView) findViewById(R.id.lat);
        data_lng = (TextView) findViewById(R.id.lng);
        data_lat = (TextView) findViewById(R.id.lat);
        data_phone = (TextView) findViewById(R.id.phone);
        data_website = (TextView) findViewById(R.id.website);
        data_company = (TextView) findViewById(R.id.company);
        data_company_name = (TextView) findViewById(R.id.company_name);
        data_catch_phrase = (TextView) findViewById(R.id.catch_pharse);
        data_bs = (TextView) findViewById(R.id.bs);

        Intent intent = getIntent();

        data_id.setText("> Id: " + String.valueOf(intent.getIntExtra(MainActivity.DATA_ID,0)));
        data_name.setText("> Name: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_NAME)));
        data_username.setText("> Username: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_USERNAME)));
        data_email.setText("> Email: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_EMAIL)));
        data_street.setText(">> Street: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_STREET)));
        data_city.setText(">> City: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_CITY)));
        data_zipcode.setText(">> Zip Code: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_ZIPCODE)));
        data_suite.setText(">> Suite: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_SUITE)));
        data_lat.setText(">>> Lat: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_LAT)));
        data_lng.setText(">>> Lng: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_LNG)));
        data_phone.setText("> Phone: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_PHONE)));
        data_website.setText("> Website: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_WEBSITE)));
        data_company_name.setText(">> Name: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_COMPANY_NAME)));
        data_catch_phrase.setText(">> Catch Phrase: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_CATCH_PHRASE)));
        data_bs.setText(">> Bs: " + String.valueOf(intent.getStringExtra(MainActivity.DATA_BS)));
    }
}
