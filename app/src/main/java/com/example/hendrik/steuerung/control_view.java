/*package com.example.hendrik.steuerung;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class control_view extends AppCompatActivity {

    public Socket clientSocket = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_view);

        Intent intent = getIntent();
        String ip = intent.getStringExtra(MainActivity.IP);

        TextView anzeigeIP = findViewById(R.id.anzeigeIP);
        anzeigeIP.setText(ip);


        TextView textAnzeige = findViewById(R.id.textNachrichten);
        networkOperation test = new  networkOperation();

        test.execute(MainActivity.IP);
        textAnzeige.setText(test.fehler);


    }

    private class networkOperation extends AsyncTask<String, Void, String>
    {
        String fehler;
        @Override
        protected String doInBackground(String... params)
        {

            try{
                clientSocket = new Socket(MainActivity.IP,8000);
            }
            catch (UnknownHostException ex)
            {
                fehler = "Unbekannter Host";
            }
            catch (IOException ex)
            {
                fehler = "IO Exception";
            }
            catch (Exception ex)
            {
                fehler = ex.getClass().getCanonicalName();

            }

            return "Success";
        }
    }


}

*/
