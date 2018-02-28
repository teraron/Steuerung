package com.example.hendrik.steuerung;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.view.View.OnClickListener;


public class app_connected extends AppCompatActivity{
    int connected = 0;
    Socket socket = null;
    DataOutputStream out;
    Button buttonSend;
    EditText editText_msg;
    TextView errors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_connected);
        String status = String.valueOf(connected);
        errors = findViewById(R.id.textView_Message);
        buttonSend = findViewById(R.id.button_send);
        editText_msg = findViewById(R.id.editText_msg);
        String ip = getIntent().getStringExtra("IP");
        int port = getIntent().getIntExtra("Port", 0);
        setContentView(R.layout.activity_app_connected);
        TextView IP = findViewById(R.id.textView_IP);
        IP.setText(ip);
        TextView PORT = findViewById(R.id.textView_PORT);
        String portS = String .valueOf(port);
        //TextView errors = (TextView) findViewById(R.id.textView_Message);
        //errors.setText(status);
        PORT.setText(portS);
        final SocketHandler myClient = new SocketHandler(ip, port, errors, editText_msg.toString(), buttonSend);
        myClient.execute();
        sendmsg(socket);


        //errors.setText(status);
        /*try {
                this.socket.close();
            }
            catch (IOException ex)
            {
                errors.setText("Upps hier stimmt was nicht");
            }
**/
    }


    private class SocketHandler extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        TextView textResponse;
        EditText messageSend;
        Button buttonSend;
        boolean var = true;

//Socket Handler Stellt Verbindung her
        SocketHandler(String addr, int port, TextView textResponse, String messageSend, Button buttonSend) {
            dstAddress = addr;
            dstPort = port;
            this.textResponse = textResponse;

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            try {
                socket = new Socket(dstAddress, dstPort);

                out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF("Test");
                out.writeUTF("Test2\n");
                out.flush();
                out.writeUTF("Test3");
                //out.close();
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
            finally
            {


            }

            connected = 1;
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            textResponse.setText(response);
            super.onPostExecute(result);
        }



    }

    public void sendmsg (final Socket socket)
    {
        errors = findViewById(R.id.textView_Message);
        editText_msg = findViewById(R.id.editText_msg);
        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                errors.setText(editText_msg.getText().toString());
                String msg = ""+editText_msg.getText().toString();

                /*try
                {
                    out.writeUTF(msg+"\n");
                }
                catch (IOException ex)
                {

                }**/
            }
        });
    }
    private class msg_Sender extends AsyncTask<Void, Void, Void>
    {
        String message;

        msg_Sender(String messageSend)
        {
            message = messageSend;
        }

        @Override
        protected Void doInBackground(Void...arg0)
        {

            try
            {

                output.print(message);
                out.flush();
                out.close();

                socket.close();
            }
            catch (IOException ex)
            {

            }

            return null;
        }
    }
}
