package com.munjal.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActMain extends AppCompatActivity {

    EditText etCity;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        onClick();
    }

    private void initialize() {
        etCity = findViewById(R.id.etCity);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void onClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActMain.this, ActWeather.class);
                Log.i("ActMain", "City: " + etCity.getText());
                String CITY = etCity.getText().toString();
                intent.putExtra("City", CITY);
                startActivity(intent);
            }
        });
    }
}
