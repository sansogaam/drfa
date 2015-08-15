package com.drfa.jms;


public interface Listener {
    public void listener(String queueName) throws Exception;
}
