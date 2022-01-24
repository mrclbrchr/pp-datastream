package com.mb.practicalprojectprocessor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MqttPublisher {

    public MqttPublisher() throws InterruptedException {
        connect();
    }

    public static void connect() throws InterruptedException{

        Random rand = new Random();
        String topic        = "numbers";
        int qos             = 0;
        String broker       = "tcp://localhost:1883";
        String clientId     = "datastream-numbers";
        MemoryPersistence persistence = new MemoryPersistence();

        while(true) {
            try {
                int rndnumber = rand.nextInt(100);
                String content      = Integer.toString(rndnumber);
                MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                sampleClient.connect(connOpts);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);
                TimeUnit.SECONDS.sleep(1);
            } catch(MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }
        }
    }
}
