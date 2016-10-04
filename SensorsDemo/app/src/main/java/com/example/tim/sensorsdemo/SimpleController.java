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

        if ((xAccel > 3 && xAccel < 7) && (yAccel < 2 && yAccel > -2) && zAccel > 7) {
            return "C";
        }
        //Right move
        else if ((xAccel < 7 && xAccel > 2) && (yAccel > 2 && yAccel < 5) && zAccel > 2) {
            return "R0";
        }
        else if ((xAccel < 7 && xAccel > 2) && (yAccel > 5 && yAccel < 9) && zAccel > 2) {
            return "R1";
        }
        // Down right
        else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > 2 && yAccel < 5) && (zAccel < 6 && zAccel > 2))) {
            return "RD0";
        }
        else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > 2 && yAccel < 5) && (zAccel < 6 && zAccel < 2))) {
            return "RD1";
        }
        else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > -5 && yAccel < -2) && (zAccel < 6 && zAccel > 2))) {
            return "LD0";
        }
        else if (((xAccel > 7 && xAccel < 9.5) && (yAccel > -9.5 && yAccel < -2) && (zAccel < 6 && zAccel < 2))) {
            return "LD1";
        }
        // Down move
        else if (((xAccel > 7 && xAccel < 9) && (yAccel > -2 && yAccel < 2) && zAccel > 2)) {
            return "D0";
        }
        else if ((xAccel > 9 && (yAccel > -2 && yAccel < 2) && zAccel > -2)) {
            return "D1";
        }
        // left move
        else if ((xAccel < 7 && xAccel > 2) && (yAccel < -2 && yAccel > -5) && zAccel > 2) {
            return "L0";
        }
        else if ((xAccel < 7 && xAccel > 3) && (yAccel < -5 && yAccel > -9) && zAccel > 2) {
            return "L1";
        }
        else if (((xAccel < 2 && xAccel > -7) && (yAccel < -4 && yAccel > -9) && zAccel > 7)) {
            return "LU0";
        }
        else if (((xAccel < 2 && xAccel > -7) && (yAccel < -4 && yAccel > -9) && zAccel < 7)) {
            return "LU1";
        }
        else if (((xAccel < 2 && xAccel > -7) && (yAccel > 4 && yAccel < 9) && zAccel > 7)) {
            return "RU0";
        }
        else if (((xAccel < 2 && xAccel > -7) && (yAccel > 4 && yAccel < 9) && zAccel < 7)) {
            return "RU1";
        }
        // Up
        else if (((xAccel < 1 && xAccel > -2) && (yAccel > -4 && yAccel < 4) && zAccel > 2)) {
            return "U0";
        }
        else if (((xAccel < -2 && xAccel > -7) && (yAccel > -4 && yAccel < 4) && zAccel > 2)) {
            return "U1";
        }

        return null;
    }
}
