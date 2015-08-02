package com.drfa.server;

/**
 * Created by Sanjiv on 8/1/2015.
 */
public interface Publisher {
    public void publisher(String message , String queueName) throws Exception;
}
