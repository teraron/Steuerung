package com.example.hendrik.steuerung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = findViewById(R.id.editText);
        editTextPort = findViewById(R.id.editText2);
        buttonConnect = findViewById(R.id.button);
        buttonClear = findViewById(R.id.button2);
        response = findViewById(R.id.textView);

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent intentMain = new Intent(MainActivity.this, app_connected.class);

                intentMain.putExtra("IP", editTextAddress.getText().toString());
                intentMain.putExtra("Port", Integer.parseInt(editTextPort.getText().toString()));

                MainActivity.this.startActivity(intentMain);


            }
        });

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }
}


