package com.drfa.jms;


public interface Publisher {
     void publisher(String message , String queueName) throws Exception;
}
