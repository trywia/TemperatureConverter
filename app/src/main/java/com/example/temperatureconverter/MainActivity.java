package com.example.temperatureconverter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        // change title, according to chosen language
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        Button changeLanguage = findViewById(R.id.buttonLanguage);
        changeLanguage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // show message to display list of languages, one can be selected
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        // languages to display
        final String[] listOfItems = {"Polski", "English", "Italiano"};
        AlertDialog.Builder langBuilder = new AlertDialog.Builder(MainActivity.this);
        langBuilder.setTitle("Wybierz język / Choose language / Cambia una lingua");
        langBuilder.setSingleChoiceItems(listOfItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("pl");
                    recreate();
                } else if (which == 1) {
                    setLocale("en");
                    recreate();
                } else if (which == 2) {
                    setLocale("it");
                    recreate();
                }

                // when selected, dismiss alert dialog
                dialog.dismiss();
            }
        });

        AlertDialog langDialog = langBuilder.create();
        // show alert dialog
        langDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

    // load languages saved in shared preferences
    public void loadLocale() {
        SharedPreferences prefer = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefer.getString("My_lang", "");
        setLocale(language);
    }

    public void onButtonClick(View view) {
        EditText temperatureField = (EditText) findViewById(R.id.temperature);
        Spinner temperatureSpinner = (Spinner) findViewById(R.id.spinner);
        TextView temperatureResult = (TextView) findViewById(R.id.result);

        String conversion = temperatureSpinner.getSelectedItem().toString();

        try {
            double temp = Double.valueOf(temperatureField.getText().toString());
            double finalTemp = 0;
            String unit = "";
            TemperatureConverter tempConv = new TemperatureConverter();

            if (conversion.equals("Celsjusz [°C] na Fahrenheit [°F]")
                    || conversion.equals("Celsius [°C] to Fahrenheit [°F]")
                    || conversion.equals("Da Celsius [°C] a Fahrenheit [°F]")) {
                finalTemp = tempConv.CtF(temp);
                unit = "[°F]";
            } else if (conversion.equals("Kelwin [K] na Fahrenheit [°F]")
                    || conversion.equals("Kelwin [K] to Fahrenheit [°F]")
                    || conversion.equals("Da Kelwin [K] a Fahrenheit [°F]")) {
                finalTemp = tempConv.KtF(temp);
                unit = "[°F]";
            } else if (conversion.equals("Fahrenheit [°F] na Celsjusz [°C]")
                    || conversion.equals("Fahrenheit [°F] to Celsius [°C]")
                    || conversion.equals("Da Fahrenheit [°F] a Celsius[°C]")) {
                finalTemp = tempConv.FtC(temp);
                unit = "[°C]";
            } else if (conversion.equals("Celsjusz [°C] na Kelwin [K]")
                    || conversion.equals("Celsius [°C] to Kelwin [K]")
                    || conversion.equals("Da Celsius [°C] a Kelwin [K]")) {
                finalTemp = tempConv.CtK(temp);
                unit = "[K]";
            } else if (conversion.equals("Kelwin [K] na Celsjusz [°C]")
                    || conversion.equals("Kelwin [K] to Celsius [°C]")
                    || conversion.equals("Da Kelwin [K] a Celsius [°C]")) {
                finalTemp = tempConv.KtC(temp);
                unit = "[°C]";
            } else if (conversion.equals("Fahrenheit [°F] na Kelwin [K]")
                    || conversion.equals("Fahrenheit [°F] to Kelwin [K]")
                    || conversion.equals("Da Fahrenheit [°F] a Kelwin [K]")) {
                finalTemp = tempConv.FtK(temp);
                unit = "[K]";
            }

            temperatureResult.setText(finalTemp + " " + unit);

        } catch (NumberFormatException e) {
            temperatureResult.setText("Błąd / Error / Lo Errore");
        }

    }
}