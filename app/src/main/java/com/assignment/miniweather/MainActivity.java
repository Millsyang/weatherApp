package com.assignment.miniweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import HttpURLconnection.WeatherData;
import db.ForecastWeatherInfo;
import db.NowWeatherInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static String location = "大兴";
    private static String key = "aafd1012c7434079a130e6de7c6363c0";

    private NowWeatherInfo nowWeatherInfo;
    private ForecastWeatherInfo forecastWeatherInfo;
    private TextView time;
    private TextView tmp;
    private TextView weatherCond;
    private TextView windDir;
    private TextView tmpRange;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 200:
                    setViewCtrl();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeCity = findViewById(R.id.changeCity);
        initViewCtrl();
        updateWeatherInfo(location,key);
        changeCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.changeCity){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.baidu.com"));
            startActivity(intent);
        }
    }

    private void updateWeatherInfo(String location,String key){
        WeatherData.setUrl(location,key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int requestTime = 0;
                Message message = new Message();
                while((WeatherData.getForecast_code()&WeatherData.getNow_code())!=200&&requestTime<=10){
                    WeatherData.createHttpConnection();
                    requestTime++;
                }
                if(requestTime>10){
                    Log.e(TAG, "HttpURLConnection Failed");
                }
                else{
                    message.what = 200;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void initViewCtrl(){
        time = findViewById(R.id.time);
        tmp = findViewById(R.id.temp);
        weatherCond = findViewById(R.id.weather_cond);
        windDir = findViewById(R.id.wind);
        tmpRange = findViewById(R.id.temprange);
    }

    private void setViewCtrl(){
        nowWeatherInfo = new NowWeatherInfo(WeatherData.getNow_weatherInfo());
        forecastWeatherInfo = new ForecastWeatherInfo(WeatherData.getForecast_weatherInfo());
        time.setText(nowWeatherInfo.getTime());
        tmp.setText(nowWeatherInfo.getTmp());
        weatherCond.setText(nowWeatherInfo.getCondTxt()+"（实时）");
        windDir.setText(nowWeatherInfo.getWindDir()+nowWeatherInfo.getWindSc()+"级");
        tmpRange.setText(forecastWeatherInfo.getTmpMin()+"~"+forecastWeatherInfo.getTmpMax()+"℃");
    }
}
