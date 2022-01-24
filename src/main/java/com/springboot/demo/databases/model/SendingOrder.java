package com.springboot.demo.databases.model;

public class SendingOrder 
{

    private long clientID;
    private long donutQuantity;

    //A class to sending orders
    public SendingOrder() 
    {
  
    }
 
    public SendingOrder(long clientID, long donutQuantity) 
    {
         this.clientID = clientID;
         this.donutQuantity = donutQuantity;
    }
 
    public long getClientID() 
    {
        return clientID;
    }
    public void setClientID(long clientID) 
    {
        this.clientID = clientID;
    }
 
    public long getDonutQuantity() 
    {
        return donutQuantity;
    }
    public void setDonutQuantity(long donutQuantity) 
    {
        this.donutQuantity = donutQuantity;
    }
 
    @Override
    public String toString() 
    {
        return "SendingOrder [id=" + clientID + ", donutQuantity=" + donutQuantity + "]";
    }
 
}