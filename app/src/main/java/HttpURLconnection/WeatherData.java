package HttpURLconnection;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class WeatherData {
    private static final String TAG = "WeatherData";
    private static String now_url = "https://free-api.heweather.net/s6/weather/now?location=beijing&key=aafd1012c7434079a130e6de7c6363c0";
    private static String forecast_url = "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=aafd1012c7434079a130e6de7c6363c0";
    private static String now_weatherInfo;
    private static String forecast_weatherInfo;
    private static int now_code = 0;
    private static int forecast_code = 0;


    public static void createHttpConnection(){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(getNow_url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            now_code = connection.getResponseCode();
            switch (now_code)
            {
                case 320:
                    String location = connection.getHeaderField("Location");
                    url = new URL(location);
                    connection = (HttpsURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    break;
                    default:
                        break;
            }
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            now_weatherInfo = response.toString();
            Log.i(TAG, "createHttpConnection"+now_weatherInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        //get forecast weatherinfo
        try {
            URL url = new URL(getForecast_url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            forecast_code = connection.getResponseCode();
            switch (forecast_code)
            {
                case 320:
                    String location = connection.getHeaderField("Location");
                    url = new URL(location);
                    connection = (HttpsURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    break;
                    default:
                        break;
            }
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            forecast_weatherInfo = response.toString();
            Log.i(TAG, "createHttpConnection"+forecast_weatherInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void setUrl(String location,String key) {
        WeatherData.now_url = "https://free-api.heweather.net/s6/weather/now"+"?"
            +"location="+location+"&key="+key;
        WeatherData.forecast_url = "https://free-api.heweather.net/s6/weather/forecast"+"?"
                +"location="+location+"&key="+key;
    }

    public static String getNow_url() {
        return now_url;
    }

    public static String getNow_weatherInfo() {
        return now_weatherInfo;
    }

    public static String getForecast_url() {
        return forecast_url;
    }

    public static String getForecast_weatherInfo() {
        return forecast_weatherInfo;
    }

    public static int getNow_code() {
        return now_code;
    }

    public static int getForecast_code() {
        return forecast_code;
    }
}
