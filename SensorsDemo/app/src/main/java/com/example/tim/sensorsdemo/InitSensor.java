package com.example.tim.sensorsdemo;

import android.content.Context;
import android.hardware.SensorManager;

/**
 * Created by TimSvensson on 2016-09-28.
 */

public class InitSensor {

    private SensorManager sensorManager;
    Context mContext;

    public SensorManager(Context mContext){
        this.mContext = mContext;

        sensorManager = (SensorManager)mContext.(Context.SENSOR_SERVICE);

    }

}
