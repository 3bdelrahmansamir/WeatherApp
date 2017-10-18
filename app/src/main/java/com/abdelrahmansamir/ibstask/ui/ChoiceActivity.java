package com.abdelrahmansamir.ibstask.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abdelrahmansamir.ibstask.R;
import com.abdelrahmansamir.ibstask.citiesData.GetCitiesData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChoiceActivity extends AppCompatActivity {

    Button btMap;
    Button btList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        new GetCitiesData(this);
        iniView();


    }

    public void iniView() {
        btMap = (Button) findViewById(R.id.bt_show_map);
        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isConnected(ChoiceActivity.this)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ChoiceActivity.this.startActivity(new Intent(ChoiceActivity.this, WeatherMapActivity.class));
                                }
                            });
                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChoiceActivity.this, "Connection error ... You can't open map without internet.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        btList = (Button) findViewById(R.id.bt_show_listview);
        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoiceActivity.this, CitiesInfromationsActivity.class));
            }
        });
    }

    public boolean isConnected(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com/");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setRequestProperty("User-Agent", "test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1000); // mTimeout is in seconds
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    } else {
                        urlc.connect();
                        if (urlc.getResponseCode() == 200) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } catch (IOException e) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;

    }

}
