package com.munjal.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActWeather extends AppCompatActivity {

    private String CITY = "Binghamton,us";
    private static String KEY = "18d841e653ccb66d55c9e9fb2b433adf";
    private String unit = "°C"; //°F
    private String units = "metric"; //imperial
    private Context context;

    RelativeLayout rlBackground;

    TextView tvCity, tvDate;
    TextView tvDescription, tvTemperature, tvMinTemperature, tvMaxTemperature;
    TextView tvSunrise, tvSunset, tvWind, tvFeelsLike, tvHumidity, tvCurrent;

    ImageView ivSunrise, ivSunset, ivWind, ivFeelsLike, ivHumidity, ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        CITY = getIntent().getStringExtra("City");
        Log.i("CityTag", "City: " + CITY);

        initialize();
        new weatherTask().execute();
    }

    private void initialize() {

        rlBackground = findViewById(R.id.rlBackground);

        tvCity = findViewById(R.id.tvCity);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvMinTemperature = findViewById(R.id.tvMinTemperature);
        tvMaxTemperature = findViewById(R.id.tvMaxTemperature);
        tvSunrise = findViewById(R.id.tvSunrise);
        tvSunset = findViewById(R.id.tvSunset);
        tvWind = findViewById(R.id.tvWind);
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCurrent = findViewById(R.id.tvCurrent);

        ivSunrise = findViewById(R.id.ivSunrise);
        ivSunset = findViewById(R.id.ivSunset);
        ivWind = findViewById(R.id.ivWind);
        ivFeelsLike = findViewById(R.id.ivFeelsLike);
        ivHumidity = findViewById(R.id.ivHumidity);
        ivIcon = findViewById(R.id.ivIcon);
    }

    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.rlMain).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=" + units + "&appid=" + KEY);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONObject jsonObj = new JSONObject(result);

                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                String current = weather.getString("main");
                String description = weather.getString("description");
                String icon = weather.getString("icon");

                JSONObject main = jsonObj.getJSONObject("main");
                String temperature = main.getString("temp") + unit;
                String feelsLike = main.getString("feels_like") + unit;
                String MinTemperature = "Min Temp: " + main.getString("temp_min") + unit;
                String MaxTemperature = "Max Temp: " + main.getString("temp_max") + unit;
                String humidity = main.getString("humidity");

                JSONObject wind = jsonObj.getJSONObject("wind");
                String windSpeed = wind.getString("speed");

                Long updatedAt = jsonObj.getLong("dt");
                String date = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));

                JSONObject sys = jsonObj.getJSONObject("sys");
                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");

                String city = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                tvCity.setText(city);
                tvDate.setText(date);
                tvDescription.setText(description.toUpperCase());
                tvTemperature.setText(temperature);
                tvMinTemperature.setText(MinTemperature);
                tvMaxTemperature.setText(MaxTemperature);
                tvSunrise.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                tvSunset.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                tvWind.setText(windSpeed);
                tvFeelsLike.setText(feelsLike);
                tvHumidity.setText(humidity);
                tvCurrent.setText(current);

                float temp = Float.parseFloat(main.getString("temp"));
                float condition = 25;


                if (temp < condition) {
                    rlBackground.setBackgroundResource(R.drawable.bg_cold);
                }
                else {
                    rlBackground.setBackgroundResource(R.drawable.bg_hot);
                }


                if (icon.equalsIgnoreCase("01d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d1", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("01n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n1", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("02d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d2", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("02n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n2", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("03d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d3", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("03n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n3", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("04d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d4", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("04n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n4", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("09d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d9", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("09n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n9", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("10d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d10", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("10n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n10", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("11d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d11", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("11n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n11", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("13d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d13", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("13n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n13", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("50d")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/d50", null, getPackageName())));
                } else if (icon.equalsIgnoreCase("50n")) {
                    ivIcon.setImageDrawable(getDrawable(getResources().getIdentifier("@drawable/n50", null, getPackageName())));
                }


                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.rlMain).setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
    }
}
