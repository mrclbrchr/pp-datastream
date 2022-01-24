package com.mb.practicalprojectprocessor;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.HashMap;
import java.util.Map;


public class DataStream{

    public static void main(String[] args) throws NotRegisteredException, InterruptedException, MqttException {

        Map<String, String> serviceMetadata = new HashMap<>();
        serviceMetadata.put("host","localhost");
        serviceMetadata.put("port","1883");
        serviceMetadata.put("communication-type","mqtt");
        serviceMetadata.put("type","stream");
        serviceMetadata.put("topic", "numbers");

        Consul client = Consul.builder().build();

        AgentClient agentClient = client.agentClient();

        String serviceId = "datastream-numbers";
        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name("pp-library")
                .port(8300)
                .check(Registration.RegCheck.ttl(1000000L))
                .meta(serviceMetadata)
                .build();

        agentClient.register(service);
        agentClient.pass(serviceId);
        MqttPublisher mypublisher = new MqttPublisher();
    }

}
