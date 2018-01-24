package com.example.hendrik.steuerung;


import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;


    client(String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;

            InputStream inputStream = socket.getInputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1)
            {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        }
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
            response = "Unbekannter Host:  " + ex.toString();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            response = "IO Exception:  " + ex.toString();
        }
        finally {
            if (socket != null)
            {
                try {
                    socket.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
       return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        textResponse.setText(response);
        super.onPostExecute(result);
    }
}