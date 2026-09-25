package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "SmartPantrySettings";
    private static final String KEY_EXPIRY_ALERTS = "expiry_alerts_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        sharedPreferences =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );

        boolean expiryAlertsEnabled =
                sharedPreferences.getBoolean(
                        KEY_EXPIRY_ALERTS,
                        false
                );

        switchExpiryAlerts.setChecked(
                expiryAlertsEnabled
        );

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    SharedPreferences.Editor editor =
                            sharedPreferences.edit();

                    editor.putBoolean(
                            KEY_EXPIRY_ALERTS,
                            isChecked
                    );

                    editor.apply();

                    if (isChecked) {

                        Toast.makeText(
                                this,
                                "Expiry alerts enabled",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                this,
                                "Expiry alerts disabled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}