package com.mb.practicalprojectprocessor;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataStream {

    Random rand = new Random();

    public DataStream() throws InterruptedException, MqttException {
        MqttHandler.connect();
        while(true) {
            MqttHandler.send(stream());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public int stream(){
        return rand.nextInt(100);
    }
}
