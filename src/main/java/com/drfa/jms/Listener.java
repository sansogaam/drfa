package com.drfa.jms;


public interface Listener {
     void listener(String queueName) throws Exception;
}
