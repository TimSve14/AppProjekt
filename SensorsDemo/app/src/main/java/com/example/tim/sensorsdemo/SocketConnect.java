package com.example.tim.sensorsdemo;

import android.app.Activity;
import android.os.Vibrator;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by TimSvensson on 2016-10-04.
 */

public class SocketConnect extends Activity {

    private Socket mSocket;
    Vibrator v;
    String roomId = "";
    String input = "";
    String userName = "";

    public SocketConnect() {

    }
    public SocketConnect(String roomId, String input, String userName){

        try {
            mSocket = IO.socket("http://130.211.111.100");

        } catch (URISyntaxException e) {
        }

        this.roomId = roomId;
        this.input = input;
        this.userName = userName;
    }

    public void startSocketConnection(){


       // v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);


        mSocket.on("move received", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
               // v.vibrate(100);
            }
        });

        mSocket.connect();

        JSONObject connect = new JSONObject();
        try {
            connect.put("id", roomId);
            connect.put("type", input);
            connect.put("nick", userName);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mSocket.emit("register", connect);

    }

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
    public void sendMove(String move){
        mSocket.emit("send message",setJsonObject(move,roomId,userName));
    }

}