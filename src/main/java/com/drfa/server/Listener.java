package com.drfa.server;


public interface Listener {
    public void listener(String queueName) throws Exception;
}
