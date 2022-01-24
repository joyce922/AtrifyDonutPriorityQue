package com.springboot.demo.databases.model;

public class ClientQueue 
{

    private long queuePosition;
    private long approximateWaitTime;

    // A class to send to our getting client function
    public ClientQueue() 
    {
  
    }
 
    public ClientQueue(long queuePosition, long approximateWaitTime) 
    {
         this.queuePosition = queuePosition;
         this.approximateWaitTime = approximateWaitTime;
    }
 
    public long getQueuePosition() 
    {
        return queuePosition;
    }
    public void setQueuePosition(long queuePosition) 
    {
        this.queuePosition = queuePosition;
    }
 
    public long getApproximateWaitTime() 
    {
        return approximateWaitTime;
    }
    public void setApproximateWaitTime(long approximateWaitTime) 
    {
        this.approximateWaitTime = approximateWaitTime;
    }
 

    @Override
    public String toString() {
        return "ClientQueue [queuePosition=" + queuePosition + ", approximateWaitTime=" + approximateWaitTime + "]";
    }
 
}