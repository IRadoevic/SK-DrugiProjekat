package com.raf;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class MessageBroker {

    public static void main(String[] args) {
        try {
            BrokerService broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            System.out.println("Starting broker");
            broker.start();
            System.out.println("Broker started successfully.");
        } catch (Exception e) {
            System.out.println("Failed to start broker " + e.getMessage());
        }
    }
}