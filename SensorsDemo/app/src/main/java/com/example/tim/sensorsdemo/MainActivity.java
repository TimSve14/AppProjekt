package com.example.tim.sensorsdemo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity{


    private InitSensor Sensor1;
    private SimpleController Controller;
    private SocketConnect Connect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Connect= new SocketConnect("owkzz2znppa13tfswcdi","input","android");
        Controller = new SimpleController();

        Connect.startSocketConnection();

        Sensor1 = new InitSensor(this,Controller,Connect);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Sensor1.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Sensor1.stop();
    }

}

