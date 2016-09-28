package com.example.tim.sensorsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private float xAccel = 0.0f;
    private float yAccel = 0.0f;
    private SensorManager sensorManager;
    private String roomId = "1s6qouqjj5j8t8pbke29";
    private String userName = "Android";
    private String currentState = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();

        JSONObject connect = new JSONObject();
        try {
            connect.put("id", roomId);
            connect.put("type", "input");
            connect.put("nick", userName);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mSocket.emit("register", connect);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }


    // Connect to the server
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://130.211.111.100");

        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
   // This is where the magic happens, we get value from the sensor and save it in yAccel and xAccel.
    //We need to put the values to negativ because we want to use the phone in landscape mode
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            yAccel = -event.values[0];
            xAccel = -event.values[1];

            sendValue();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Create JsonObject so the server understand what we are sending

    public JSONObject setJsonObject(String msg, String id, String nick){

        JSONObject JsonObject = new JSONObject();
        try {
            JsonObject.put("msg", msg);
            JsonObject.put("id", id);
            JsonObject.put("nick", nick);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return JsonObject;
    }


    private void sendValue() {

        // To read the value of the sensor and send the right value to the server,
        // going to calibrate this later to fit the game

        // Testing the vibrator
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

       if(((xAccel < -1.1 && xAccel > -3.9) && (yAccel < -1.1 && yAccel > -3.9)) && currentState != "DownRight0"){
           System.out.println("DownRight0");

           JSONObject DownRight0 = setJsonObject("DownRight0", roomId, userName);

           mSocket.emit("send message", DownRight0);
           currentState = "DownRight0";
       }

       else if(((xAccel < -4 && xAccel > -7) && (yAccel < -4 && yAccel > -7 )) && currentState != "DownRight1"){
            System.out.println("DownRight1");

            JSONObject DownRight1 = setJsonObject("DownRight1", roomId, userName);

            mSocket.emit("send message", DownRight1);
            currentState = "DownRight1";
        }

        else if((xAccel < -1.1 && xAccel > -3.9)&&(!(yAccel < -1.1 && yAccel > -3.9)) && currentState != "Right0") {

                System.out.println("Right0");

                JSONObject Right0 = setJsonObject("Right0", roomId, userName);

                mSocket.emit("send message", Right0);
                currentState = "Right0";
                //v.vibrate(500);
        }

       else if((xAccel < -4 && xAccel > -7) && (!(yAccel < -4 && yAccel > -7)) &&currentState != "Right1"){
            System.out.println("Right1");

            JSONObject Right1 = setJsonObject("Right1",roomId,userName);
            mSocket.emit("send message",Right1);
            currentState = "Right1";
        }

       else if(xAccel < -7 && currentState != "Right2"){
            System.out.println("Right2");

            JSONObject Right2 = setJsonObject("Right2",roomId,userName);

            mSocket.emit("send message",Right2);
            currentState = "Right2";
        }

       else if(xAccel > -1 && xAccel < 1 && yAccel > -1 && yAccel < 1 && currentState != "Still"){
            System.out.println("Standing still");

            JSONObject Still = setJsonObject("Standing still",roomId,userName);
            mSocket.emit("send message",Still);
            currentState = "Still";
        }

       else if(xAccel > 1.1 && xAccel < 3.9 && currentState != "Left0"){
            System.out.println("Left0");

            JSONObject Left0 = setJsonObject("Left0",roomId,userName);
            mSocket.emit("send message",Left0);
            currentState = "Left0";

        }

       else if(xAccel > 4 && xAccel < 7 && currentState != "Left1"){
            System.out.println("Left1");

            JSONObject Left1 = setJsonObject("Left1",roomId,userName);

            mSocket.emit("send message",Left1);
            currentState = "Left1";
        }

       else if(xAccel > 7 && currentState != "Left2"){
            System.out.println("Left2");

            JSONObject Left2 = setJsonObject("Left2",roomId,userName);

            mSocket.emit("send message",Left2);
            currentState = "Left2";
        }

       else if((yAccel < -1.1 && yAccel > -3.9) && (!((xAccel < -1.1 && xAccel > -3.9))) &&currentState != "Down0"){
            System.out.println("Down0");

            JSONObject Down0 = setJsonObject("Down0",roomId,userName);

            mSocket.emit("send message",Down0);
            currentState = "Down0";

        }


       else if((yAccel < -4 && yAccel > -7) && (!(xAccel < -4 && xAccel > -7)) &&currentState != "Down1"){
            System.out.println("Down1");

            JSONObject Down1 = setJsonObject("Down1",roomId,userName);

            mSocket.emit("send message",Down1);
            currentState = "Down1";
        }

       else if(yAccel < -7 && currentState != "Down2"){
            System.out.println("Down2");

            JSONObject Down2 = setJsonObject("Down2",roomId,userName);

            mSocket.emit("send message",Down2);
            currentState = "Down2";
        }

       else if(yAccel > 1.1 && yAccel < 3.9 && currentState != "Up0"){
            System.out.println("Up0");

            JSONObject Up0 = setJsonObject("Up0",roomId,userName);

            mSocket.emit("send message",Up0);
            currentState = "Up0";
        }

       else if(yAccel > 4 && yAccel < 7 && currentState != "Up1"){
            System.out.println("Up1");

            JSONObject Up1 = setJsonObject("Up1",roomId,userName);

            mSocket.emit("send message",Up1);
            currentState = "Up1";
        }

       else if(yAccel > 7 && currentState != "Up2"){
            System.out.println("Up2");

            JSONObject Up2 = setJsonObject("Up2",roomId,userName);

            mSocket.emit("send message",Up2);
            currentState = "Up2";
        }
    }
}

