/**package com.example.hendrik.steuerung;


import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHandler extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    EditText messageSend;
    Button buttonSend;
    boolean var = true;


    SocketHandler(String addr, int port, TextView textResponse, String messageSend, Button buttonSend) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;

    }

    @Override
    protected Void doInBackground(Void... arg0) {


        try {
            final Socket socket = new Socket(dstAddress, dstPort);

            OutputStream out = socket.getOutputStream();
            PrintWriter output =  new PrintWriter(out);

            output.println(messageSend);
            output.flush();



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
            //if (socket != null)
            //{
                /*try {
                    socket.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            //}
        }



       return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {

        super.onPostExecute(result);
    }



}*/