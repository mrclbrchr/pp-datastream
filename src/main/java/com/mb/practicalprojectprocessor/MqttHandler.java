package com.mb.practicalprojectprocessor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHandler {

    static String topic        = "numbers";
    static int qos             = 0;
    static String broker       = "tcp://localhost:1883";
    static String clientId     = "datastream-numbers";
    static MemoryPersistence persistence = new MemoryPersistence();
    static MqttClient client;

    public MqttHandler() {
    }

    public static void send(Integer number) throws MqttException {
        String content      = Integer.toString(number);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        client.publish(topic, message);
    }

    public static void connect(){

            try {
                client = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                client.connect(connOpts);
            } catch(MqttException me) {
                me.printStackTrace();
            }
        }

}
