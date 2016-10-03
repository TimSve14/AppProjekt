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

    private SensorManager sensorManager;
    private String roomId = "byxkzgrlaoi6fwm6lxr";
    private String userName = "Android";
    private String currentState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();

        JSONObject student1 = new JSONObject();
        try {
            student1.put("id", roomId);
            student1.put("type", "input");
            student1.put("nick", "android");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mSocket.emit("register", student1);

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


    private void sendValue(float xAccel, float yAccel, float zAccel){

        // To read the value of the sensor and send the right value to the server,
        // going to calibrate this later to fit the game
        //System.out.println("X: " + xAccel);
        //System.out.println("Y: " + yAccel);
        //System.out.println("Z: " + zAccel);
        //Standing still
        if((xAccel > 3 && xAccel < 7) && (yAccel<2 && yAccel>-2)&& zAccel > 7&& currentState != "Center"){
            System.out.println("Center");
            mSocket.emit("send message",setJsonObject("C",roomId,userName));
            currentState = "Center";

        }
        //Right move
        else if((xAccel<7 && xAccel>2) && (yAccel > 2&& yAccel < 5) && zAccel >2 && currentState != "Right0"){
            System.out.println("Right0");
            mSocket.emit("send message",setJsonObject("R0",roomId,userName));
            currentState = "Right0";
        }

        else if((xAccel<7 && xAccel>2) && (yAccel > 5&& yAccel < 9) && zAccel >2 && currentState != "Right1"){
            System.out.println("Right1");
            mSocket.emit("send message",setJsonObject("R1",roomId,userName));
            currentState = "Right1";
        }
        // Down right
        else if(((xAccel>7 && xAccel<9.5)  && (yAccel > 2&& yAccel < 5) && (zAccel <6 && zAccel >2 )) && currentState != "RightDown0"){
            System.out.println("RightDown0");
            mSocket.emit("send message",setJsonObject("RD0",roomId,userName));
            currentState = "RightDown0";
        }
        else if(((xAccel>7 && xAccel<9.5)  && (yAccel > 2&& yAccel < 5) && (zAccel <6 && zAccel <2 )) && currentState != "RightDown1"){
            System.out.println("RightDown1");
            mSocket.emit("send message",setJsonObject("RD1",roomId,userName));
            currentState = "RightDown1";
        }
        else if(((xAccel>7 && xAccel<9.5)  && (yAccel > -5&& yAccel < -2) && (zAccel <6 && zAccel >2 )) && currentState != "LeftDown0"){
            System.out.println("LeftDown0");
            mSocket.emit("send message",setJsonObject("LD0",roomId,userName));
            currentState = "LeftDown0";
        }
        else if(((xAccel>7 && xAccel<9.5)  && (yAccel > -9.5&& yAccel < -2) && (zAccel <6 && zAccel <2 )) && currentState != "LeftDown1"){
            System.out.println("LeftDown1");
            mSocket.emit("send message",setJsonObject("LD1",roomId,userName));
            currentState = "LeftDown1";
        }
        // Down move
        else if(((xAccel> 7 && xAccel <9) && (yAccel > -2 && yAccel < 2)&& zAccel >2) && currentState != "Down0"){
            System.out.println("Down0");
            mSocket.emit("send message",setJsonObject("D0",roomId,userName));
            currentState = "Down0";
        }
        else if((xAccel> 9 && (yAccel > -2 && yAccel < 2)&& zAccel >-2) && currentState != "Down1"){
            System.out.println("Down1");
            mSocket.emit("send message",setJsonObject("D1",roomId,userName));
            currentState = "Down1";
        }
        // left move
        else if((xAccel<7 && xAccel>2) && (yAccel < -2 && yAccel >-5)&& zAccel >2 && currentState != "Left0"){
            System.out.println("Left0");
            mSocket.emit("send message",setJsonObject("L0",roomId,userName));
            currentState = "Left0";
        }
        else if((xAccel<7 && xAccel>3) && (yAccel < -5 && yAccel >-9)&& zAccel >2 && currentState != "Left1"){
            System.out.println("Left1");
            mSocket.emit("send message",setJsonObject("L1",roomId,userName));
            currentState = "Left1";
        }
        else if(((xAccel < 2 && xAccel > -7 )&& (yAccel < -4 && yAccel > -9 )&& zAccel >7) && currentState != "LeftUp0"){
            System.out.println("LeftUp0");
            mSocket.emit("send message",setJsonObject("LU0",roomId,userName));
            currentState = "LeftUp0";
        }
        else if(((xAccel < 2 && xAccel > -7 )&& (yAccel < -4 && yAccel > -9 )&& zAccel <7) && currentState != "LeftUp1"){
            System.out.println("LeftUp1");
            mSocket.emit("send message",setJsonObject("LU1",roomId,userName));
            currentState = "LeftUp1";
        }
        else if(((xAccel < 2 && xAccel > -7 )&& (yAccel > 4 && yAccel < 9 )&& zAccel >7) && currentState != "RightUp0"){
            System.out.println("RightUp0");
            mSocket.emit("send message",setJsonObject("RU0",roomId,userName));
            currentState = "RightUp0";
        }
        else if(((xAccel < 2 && xAccel > -7 )&& (yAccel > 4 && yAccel < 9 )&& zAccel <7) && currentState != "RightUp1"){
            System.out.println("RightUp1");
            mSocket.emit("send message",setJsonObject("RU1",roomId,userName));
            currentState = "RightUp1";
        }
       // Up
        else if(((xAccel< 1 && xAccel> -2 )&&(yAccel > -4 && yAccel < 4 )&& zAccel >2) && currentState != "Up0"){
            System.out.println("Up0");
            mSocket.emit("send message",setJsonObject("U0",roomId,userName));
            currentState = "Up0";
        }
        else if(((xAccel< -2 && xAccel> -7 )&&(yAccel > -4 && yAccel < 4 )&& zAccel >2) && currentState != "Up1"){
            System.out.println("Up1");
            mSocket.emit("send message",setJsonObject("U1",roomId,userName));
            currentState = "Up1";
        }

    }
}

