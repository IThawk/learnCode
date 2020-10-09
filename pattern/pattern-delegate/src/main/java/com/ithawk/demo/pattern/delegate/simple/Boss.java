package com.ithawk.demo.pattern.delegate.simple;

/**
 * Created by Tom.
 */
public class Boss {

    public void command(String command,Leader leader){
        leader.doing(command);
    }

}

