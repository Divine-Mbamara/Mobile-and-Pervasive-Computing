package com.example.weatherapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.Weather;
import com.example.weatherapp.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<Weather> myValue;

    public WeatherViewModel() {
        myValue = new MutableLiveData<>();
    }

    public void queryWeather(String q) {
        String url = "https://api.weatherapi.com/v1/current.json?key=0263213e1aa34a46923171552250511&q=" + q;
        ApiClient.get(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("tag", Objects.requireNonNull(e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String res = response.body().string();
                    JSONObject json = new JSONObject(res);

                    // Get Value from Json
                    String name = json.getJSONObject("location").getString("name");
                    String country = json.getJSONObject("location").getString("country");
                    String condition = json.getJSONObject("current").getJSONObject("condition").getString("text");
                    String icon = json.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Integer feelLikeC = (int) Math.round(json.getJSONObject("current").getDouble("feelslike_c"));
                    Integer feelLikeF = (int) Math.round(json.getJSONObject("current").getDouble("feelslike_f"));
                    String windMph = json.getJSONObject("current").getString("wind_mph");
                    String windKph = json.getJSONObject("current").getString("wind_kph");
                    String windDirect = json.getJSONObject("current").getString("wind_dir");
                    Integer tempC = (int) Math.round(json.getJSONObject("current").getDouble("temp_c"));
                    Integer tempF = (int) Math.round(json.getJSONObject("current").getDouble("temp_f"));
                    Integer windDegree = json.getJSONObject("current").getInt("wind_degree");

                    Weather weather = new Weather(name, country, condition, icon, feelLikeC, feelLikeF, windMph, windKph, windDirect, tempC, tempF, windDegree);
                    myValue.postValue(weather);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public LiveData<Weather> getResponse() {
        return myValue;
    }
}
