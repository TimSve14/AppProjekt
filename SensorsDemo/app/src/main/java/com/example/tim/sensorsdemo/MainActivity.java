package com.example.tim.sensorsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SyncStatusObserver;
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
import android.widget.Switch;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private String roomId = "qzrmxlos2ctysvbcsor";
    private String userName = "Android";
    private String currentState = "";
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        mSocket.on("move received",new Emitter.Listener(){

                    @Override
                    public void call(Object... args) {
                        v.vibrate(100);
                    }
                });

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

            sendValue(event.values[0],event.values[1],event.values[2]);

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


    private void sendValue(float xAccel, float yAccel,float zAccel) {

        // To read the value of the sensor and send the right value to the server,
        // going to calibrate this later to fit the game

       // double angleToY = Math.atan2(z,y)*180*1/Math.PI;
       // double angleToX = Math.atan2(z,x)*180*1/Math.PI;

        //System.out.println("X: " + xAccel);
        //System.out.println("Y: " + yAccel);
        //System.out.println("Z: " + zAccel);

        if(yAccel < -1 && (zAccel >2 && zAccel<7) && xAccel > 0){
            System.out.println("DownLeft");
        }
        else if(yAccel > -9 && yAccel < -1 && zAccel >7 && xAccel > 0){
            System.out.println("Left");
        }



/*

       if(((xAccel < -1.1 && xAccel > -3.9) && (yAccel < -1.1 && yAccel > -3.9)) && currentState != "RightDown0"){
           System.out.println("DownRight0");

           JSONObject RightDown0 = setJsonObject("RD0", roomId, userName);

           mSocket.emit("send message", RightDown0);
           currentState = "RightDown0";
       }

       else if(((xAccel < -4 && xAccel > -7) && (yAccel < -4 && yAccel > -7 )) && currentState != "RightDown1"){
            System.out.println("RightDown1");

            JSONObject RightDown1 = setJsonObject("RD1", roomId, userName);

            mSocket.emit("send message", RightDown1);
            currentState = "RightDown1";
        }

       else if((xAccel < -1.1 && xAccel > -3.9) && (yAccel > 1.1 && yAccel < 3.9) && currentState != "RightUp0"){
           System.out.println("RightUp0");

           JSONObject RightUp0 = setJsonObject("RU0", roomId, userName);

           mSocket.emit("send message", RightUp0);
           currentState = "RightUp0";
       }

       else if((xAccel < -4 && xAccel > -7 ) && (yAccel > 4 && yAccel < 7) && currentState != "RightUp1"){
           System.out.println("RightUp1");

           JSONObject RightUp1 = setJsonObject("RU1", roomId, userName);

           mSocket.emit("send message", RightUp1);
           currentState = "RightUp1";
       }


        else if((xAccel < -1.1 && xAccel > -3.9) && (!(yAccel < -1.1 && yAccel > -3.9)) && (!(yAccel > 1.1 && yAccel < 3.9)) && (!(yAccel > 4 && yAccel < 7)) && currentState != "Right0") {

                System.out.println("Right0");

                JSONObject Right0 = setJsonObject("R0", roomId, userName);

                mSocket.emit("send message", Right0);
                currentState = "Right0";
                //v.vibrate(500);
        }

       else if((xAccel < -4 && xAccel > -7) && (!(yAccel < -4 && yAccel > -7)) &&currentState != "Right1"){
            System.out.println("Right1");

            JSONObject Right1 = setJsonObject("R1",roomId,userName);
            mSocket.emit("send message",Right1);
            currentState = "Right1";
        }

       else if((xAccel < -7) && currentState != "Right2"){
            System.out.println("Right2");

            JSONObject Right2 = setJsonObject("R2",roomId,userName);

            mSocket.emit("send message",Right2);
            currentState = "Right2";
        }

       else if(xAccel > -1 && xAccel < 1 && yAccel > -1 && yAccel < 1 && currentState != "Center"){
            System.out.println("Standing still");

            JSONObject Center = setJsonObject("C",roomId,userName);
            mSocket.emit("send message",Center);
            currentState = "Center";
        }

       else if(xAccel > 1.1 && xAccel < 3.9 && currentState != "Left0"){
            System.out.println("Left0");

            JSONObject Left0 = setJsonObject("L0",roomId,userName);
            mSocket.emit("send message",Left0);
            currentState = "Left0";

        }

       else if(xAccel > 4 && xAccel < 7 && currentState != "Left1"){
            System.out.println("Left1");

            JSONObject Left1 = setJsonObject("L1",roomId,userName);

            mSocket.emit("send message",Left1);
            currentState = "Left1";
        }

       else if(xAccel > 7 && currentState != "Left2"){
            System.out.println("Left2");

            JSONObject Left2 = setJsonObject("L2",roomId,userName);

            mSocket.emit("send message",Left2);
            currentState = "Left2";
        }

       else if((yAccel < -1.1 && yAccel > -3.9) && (!((xAccel < -1.1 && xAccel > -3.9))) &&currentState != "Down0"){
            System.out.println("Down0");

            JSONObject Down0 = setJsonObject("D0",roomId,userName);

            mSocket.emit("send message",Down0);
            currentState = "Down0";

        }


       else if((yAccel < -4 && yAccel > -7) && (!(xAccel < -4 && xAccel > -7)) &&currentState != "Down1"){
            System.out.println("Down1");

            JSONObject Down1 = setJsonObject("D1",roomId,userName);

            mSocket.emit("send message",Down1);
            currentState = "Down1";
        }

       else if((yAccel < -7) && currentState != "Down2"){
            System.out.println("Down2");

            JSONObject Down2 = setJsonObject("D2",roomId,userName);

            mSocket.emit("send message",Down2);
            currentState = "Down2";
        }

       else if((yAccel > 1.1 && yAccel < 3.9)&& (!(xAccel < -1.1 && xAccel > -3.9)) && currentState != "Up0"){
            System.out.println("Up0");

            JSONObject Up0 = setJsonObject("U0",roomId,userName);

            mSocket.emit("send message",Up0);
            currentState = "Up0";
        }

       else if(yAccel > 4 && yAccel < 7 && (!(xAccel < -4 && xAccel > -7)) && currentState != "Up1"){
            System.out.println("Up1");

            JSONObject Up1 = setJsonObject("U1",roomId,userName);

            mSocket.emit("send message",Up1);
            currentState = "Up1";
        }

       else if(yAccel > 7 && currentState != "Up2"){
            System.out.println("Up2");

            JSONObject Up2 = setJsonObject("U2",roomId,userName);

            mSocket.emit("send message",Up2);
            currentState = "Up2";
        }
*/
    }


}

