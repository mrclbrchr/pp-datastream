package com.mb.practicalprojectdatastream;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
@SpringBootApplication
public class DataStreamApplication {

    static Consul consulClient = Consul.builder().build();

    static AgentClient agentClient = consulClient.agentClient();

    static String serviceId = "dataprocessor-numbers";

    public static void main(String[] args) throws NotRegisteredException, InterruptedException, MqttException {

        SpringApplication.run(DataStreamApplication.class, args);

        Map<String, String> serviceMetadata = new HashMap<>();
        serviceMetadata.put("host","localhost");
        serviceMetadata.put("port","1883");
        serviceMetadata.put("communication-type","mqtt");
        serviceMetadata.put("type","stream");
        serviceMetadata.put("topic", "numbers");

        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name("pp-library")
                .port(8302)
                .check(Registration.RegCheck.ttl(5L))
                .meta(serviceMetadata)
                .build();

        agentClient.register(service);
        agentClient.pass(serviceId);
        DataStream dataStream = new DataStream();
    }

    @Scheduled(fixedRate = 3000)
    public static void aliveSignal() throws NotRegisteredException {
        agentClient.pass(serviceId);
    }

}
