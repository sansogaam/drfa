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
            broker.addConnector("tcp://localhost:5672");


            broker.start();
        } catch (Exception e) {
            System.out.println(String.format("Something wrong with the broker %s", e.getMessage()));
        }

    }

    public static void stopBroker() {
        try {
            broker.stop();
        } catch (Exception e) {
            System.out.println(String.format("Some problem with broker %s", e.getMessage()));
        }

    }
}
