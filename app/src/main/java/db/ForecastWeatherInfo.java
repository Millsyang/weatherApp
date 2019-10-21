package db;

import android.util.Log;

import org.json.JSONObject;

import util.MyInterface;

public class ForecastWeatherInfo extends WeatherInfo implements MyInterface {
    private static final String TAG = "ForecastWeatherInfo";

    private String tmpMax;
    private String tmpMin;

    public ForecastWeatherInfo(String weatherInfo) {
        super(weatherInfo);
        initWeather();
    }

    @Override
    public String parseJSONWithJSONObject() {
        JSONObject  forecast = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject(super.parseJSONWithJSONObject());
            String status = jsonObject.getString("status");
            if(status.equals("ok")) {
                forecast = jsonObject.getJSONArray("daily_forecast").getJSONObject(0);
                //Log.i(TAG, "parseJSONWithJSONObject: forecast weather info"+ forecast.toString());
            }
            else{
                Log.e(TAG, "parseJSONWithJSONObject: status wrong");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return forecast.toString();
    }

    @Override
    public void initWeather(){
        try{
            JSONObject now = new JSONObject(parseJSONWithJSONObject());
            tmpMax = now.getString("tmp_max");
            tmpMin = now.getString("tmp_min");
//            Log.i(TAG, "initWeather: "+tmpMax);
//            Log.i(TAG, "initWeather: "+tmpMin);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getTmpMax() {
        return tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }
}
