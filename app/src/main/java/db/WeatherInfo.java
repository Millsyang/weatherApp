package db;

import org.json.JSONObject;

public class WeatherInfo {
    //private static final String TAG = "WeatherInfo";

    private String info;
    private String time;

    public WeatherInfo(String weatherInfo){
        info = weatherInfo;
        time = "2019-10-20 12:00";
    }

    public String parseJSONWithJSONObject() {
        String jsonResult = new String();
        try {
            JSONObject jsonObject = new JSONObject(info).getJSONArray("HeWeather6").getJSONObject(0);
            time = jsonObject.getJSONObject("update").getString("loc");
            jsonResult = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

//    public void setInfo(String info) {
//        this.info = info;
//    }
//
//    public String getInfo() {
//        return info;
//    }

    public String getTime() {
        return time;
    }
}
