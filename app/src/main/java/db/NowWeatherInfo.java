package db;

import android.util.Log;

import org.json.JSONObject;

import util.MyInterface;

public class NowWeatherInfo extends WeatherInfo implements MyInterface {
    private static final String TAG = "NowWeatherInfo";

    private String condTxt;
    private String tmp;
    private String windDir;
    private String windSc;               //wind force:3-4
    public NowWeatherInfo(String weatherInfo) {
        super(weatherInfo);
        initWeather();
    }

    @Override
    public String parseJSONWithJSONObject() {
        JSONObject now = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject(super.parseJSONWithJSONObject());
            String status = jsonObject.getString("status");
            if(status.equals("ok")) {
                now = jsonObject.getJSONObject("now");
                //Log.i(TAG, "parseJSONWithJSONObject: now weather info"+ now.toString());
            }
            else{
                Log.e(TAG, "parseJSONWithJSONObject: status wrong");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return now.toString();
    }

    @Override
    public void initWeather(){
        try{
            JSONObject now = new JSONObject(parseJSONWithJSONObject());
            condTxt = now.getString("cond_txt");
            tmp = now.getString("tmp");
            windDir = now.getString("wind_dir");
            windSc = now.getString("wind_sc");
//            Log.i(TAG, "initWeather: "+condTxt);
//            Log.i(TAG, "initWeather: "+tmp);
//            Log.i(TAG, "initWeather: "+windDir);
//            Log.i(TAG, "initWeather: "+windSc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getCondTxt() {
        return condTxt;
    }

    public String getTmp() {
        return tmp;
    }

    public String getWindDir() {
        return windDir;
    }

    public String getWindSc() {
        return windSc;
    }
}
