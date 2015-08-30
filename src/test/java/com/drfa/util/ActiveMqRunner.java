package com.drfa.util;

import org.apache.activemq.broker.BrokerService;

public class ActiveMqRunner {

    private static BrokerService broker;

    public static void main(String[] args) {
        startBroker();
    }


    public static void startBroker() {
        try {
            broker = new BrokerService();

            broker.addConnector("tcp://localhost:61616");
            broker.start();
        } catch (Exception e) {
            System.out.println("Something wrong with the broker");
        }

    }

    public static void stopBroker() {
        try {
            broker.stop();
        } catch (Exception e) {
            System.out.println("Something wrong with the broker");
        }

    }
}
