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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
        String portS = String.valueOf(port);
        TextView errors = (TextView) findViewById(R.id.textView_Message);
        errors.setText(status);
        PORT.setText(portS);

        errors = findViewById(R.id.textView_Message);
        editText_msg = findViewById(R.id.editText_msg);

        // Einen neuen Socket erstellen.
        // Auf den Task wird max. 2 Sek. gewartet.
        // falls bis dahin kein Socket erstellt werden konnte, wird ein TiemOutError ausgegeben.
        AsyncTask<Void, Void, Socket> task = new SocketHandler(ip, port).execute();
        try
        {
            socket = task.get(2, TimeUnit.SECONDS);
        }
        catch (TimeoutException ex)
        {
            errors.setText("TimeOutError");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        // Benutze die Klasse msg_Sender um die eingegebene Nachricht auf Tastendruck auf dem übergebenen Socket zu senden
        // @Hendrik: Ich hab keine Ahnung warum ich hier das mit "final..." machen muss und nicht direkt auf errors zugreifen kann...
        final TextView finalErrors = errors;
        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finalErrors.setText(editText_msg.getText().toString());
                String msg = "" + editText_msg.getText().toString();
                new msg_Sender(socket, msg).execute();
            }
        });
    }

    // hier muss das Result, also bei uns "Socket" stehen!  ----\/  (siehe https://developer.android.com/reference/android/os/AsyncTask.html)
    private class SocketHandler extends AsyncTask<Void, Void, Socket> {

        String dstAddress;
        int dstPort;
        String response = "";
        //TextView textResponse;
        //EditText messageSend;
        //Button buttonSend;
        boolean var = true;

        //Socket Handler Stellt Verbindung her
        // @Hendrik: Hier waren bei dir noch mehr Übergabeparameter mit drin.
        // Da ich nicht wusste was ich damit anfangen soll, hab ich die einfach mal entfernt.
        SocketHandler(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
            //this.textResponse = textResponse;

        }

        @Override
        protected Socket doInBackground(Void... arg0) {


            try {
                socket = new Socket(dstAddress, dstPort);
                new msg_Sender(socket, "Connection established!").execute();
                //out = new DataOutputStream(socket.getOutputStream());
                //out.writeUTF("Test Welcome Msg Client");
                //out.flush();
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
            return socket;
        }


    }

// MessageSender Klasse:
    private class msg_Sender extends AsyncTask<Void, Void, Void> {
        Socket s;
        String message;

        msg_Sender(Socket socket, String messageSend)
        {
            message = messageSend;
            s = socket;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                out = new DataOutputStream(s.getOutputStream());
                out.writeUTF(message);
                out.flush();
                //out.close();
                //socket.close(); // nicht machen sonst ist der Socket natürlich geschlossen!

            } catch (IOException ex)
            {
                errors.setText("Upps hier stimmt was nicht");
            }

            return null;
        }
    }

}
