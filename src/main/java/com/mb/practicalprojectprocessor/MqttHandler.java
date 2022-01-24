package com.mb.practicalprojectprocessor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

public class MqttHandler {

    static Random rand = new Random();
    static String topic        = "numbers";
    static int qos             = 0;
    static String broker       = "tcp://localhost:1883";
    static String clientId     = "datastream-numbers";
    static MemoryPersistence persistence = new MemoryPersistence();
    static MqttClient sampleClient;

    public MqttHandler() throws InterruptedException {
        connect();
    }

    public static void send(Integer number) throws MqttException {
        int rndnumber = rand.nextInt(100);
        String content      = Integer.toString(rndnumber);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        sampleClient.publish(topic, message);
    }

    public static void connect() throws InterruptedException{

            try {
                sampleClient = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                sampleClient.connect(connOpts);
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
