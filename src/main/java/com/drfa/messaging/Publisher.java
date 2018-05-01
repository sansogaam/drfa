package com.drfa.messaging;

public interface Publisher {
    void publish(String message, String topic);
}
