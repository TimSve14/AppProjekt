package com.example.tim.sensorsdemo;

import android.content.Context;

/**
 * Created by TimSvensson on 2016-10-04.
 */

public class SimpleController implements Controller{

    public SimpleController(){

    }

    @Override
    public String getMove(float[] coordinates) {

        float xAccel = coordinates[0];
        float yAccel = coordinates[1];
        float zAccel = coordinates[2];


        String currentState = "";

        if ((xAccel > 3 && xAccel < 7) && (yAccel < 2 && yAccel > -2) && zAccel > 7) {
            //System.out.println("Center");
            //mSocket.emit("send message",setJsonObject("C",roomId,userName));
            return "C";
        }
        //Right move
        else if ((xAccel < 7 && xAccel > 2) && (yAccel > 2 && yAccel < 5) && zAccel > 2) {
            //System.out.println("Right0");
            //mSocket.emit("send message",setJsonObject("R0",roomId,userName));
            return "R0";
        } else if ((xAccel < 7 && xAccel > 2) && (yAccel > 5 && yAccel < 9) && zAccel > 2 && currentState != "Right1") {
            // System.out.println("Right1");
            // mSocket.emit("send message",setJsonObject("R1",roomId,userName));
            return "R1";
        }
        // Down right
        else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > 2 && yAccel < 5) && (zAccel < 6 && zAccel > 2)) && currentState != "RightDown0") {
            // System.out.println("RightDown0");
            // mSocket.emit("send message",setJsonObject("RD0",roomId,userName));
            return "RD0";
        } else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > 2 && yAccel < 5) && (zAccel < 6 && zAccel < 2)) && currentState != "RightDown1") {
            // System.out.println("RightDown1");
            // mSocket.emit("send message",setJsonObject("RD1",roomId,userName));
            return "RD1";
        } else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > -5 && yAccel < -2) && (zAccel < 6 && zAccel > 2)) && currentState != "LeftDown0") {
            // System.out.println("LeftDown0");
            // mSocket.emit("send message",setJsonObject("LD0",roomId,userName));
            return "LD0";
        } else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > -9.5 && yAccel < -2) && (zAccel < 6 && zAccel < 2)) && currentState != "LeftDown1") {
            // System.out.println("LeftDown1");
            // mSocket.emit("send message",setJsonObject("LD1",roomId,userName));
            return "LD1";
        }
        // Down move
        else if (((xAccel > 7 && xAccel < 9) && (yAccel > -2 && yAccel < 2) && zAccel > 2) && currentState != "Down0") {
            // System.out.println("Down0");
            // mSocket.emit("send message",setJsonObject("D0",roomId,userName));
            return "D0";
        } else if ((xAccel > 9 && (yAccel > -2 && yAccel < 2) && zAccel > -2) && currentState != "Down1") {
            //  System.out.println("Down1");
            //  mSocket.emit("send message",setJsonObject("D1",roomId,userName));
            return "D1";
        }
        // left move
        else if ((xAccel < 7 && xAccel > 2) && (yAccel < -2 && yAccel > -5) && zAccel > 2 && currentState != "Left0") {
            //   System.out.println("Left0");
            //   mSocket.emit("send message",setJsonObject("L0",roomId,userName));
            return "L0";
        } else if ((xAccel < 7 && xAccel > 3) && (yAccel < -5 && yAccel > -9) && zAccel > 2 && currentState != "Left1") {
            // System.out.println("Left1");
            // mSocket.emit("send message",setJsonObject("L1",roomId,userName));
            return "L1";
        } else if (((xAccel < 2 && xAccel > -7) && (yAccel < -4 && yAccel > -9) && zAccel > 7) && currentState != "LeftUp0") {
            // System.out.println("LeftUp0");
            // mSocket.emit("send message",setJsonObject("LU0",roomId,userName));
            return "LU0";
        } else if (((xAccel < 2 && xAccel > -7) && (yAccel < -4 && yAccel > -9) && zAccel < 7) && currentState != "LeftUp1") {
            // System.out.println("LeftUp1");
            // mSocket.emit("send message",setJsonObject("LU1",roomId,userName));
            return "LU1";
        } else if (((xAccel < 2 && xAccel > -7) && (yAccel > 4 && yAccel < 9) && zAccel > 7) && currentState != "RightUp0") {
            // System.out.println("RightUp0");
            //  mSocket.emit("send message",setJsonObject("RU0",roomId,userName));
            return "RU0";
        } else if (((xAccel < 2 && xAccel > -7) && (yAccel > 4 && yAccel < 9) && zAccel < 7) && currentState != "RightUp1") {
            // System.out.println("RightUp1");
            // mSocket.emit("send message",setJsonObject("RU1",roomId,userName));
            return "RU1";
        }
        // Up
        else if (((xAccel < 1 && xAccel > -2) && (yAccel > -4 && yAccel < 4) && zAccel > 2) && currentState != "Up0") {
            //System.out.println("Up0");
            //mSocket.emit("send message",setJsonObject("U0",roomId,userName));
            return "U0";
        } else if (((xAccel < -2 && xAccel > -7) && (yAccel > -4 && yAccel < 4) && zAccel > 2) && currentState != "Up1") {
            // System.out.println("Up1");
            // mSocket.emit("send message",setJsonObject("U1",roomId,userName));
            return "U1";
        }

        return null;
    }
}
