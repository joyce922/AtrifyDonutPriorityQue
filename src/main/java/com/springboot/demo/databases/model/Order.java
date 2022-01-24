package com.springboot.demo.databases.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Orders")
public class Order 
{
	//Order class and its attributes
    private long id;
    private long clientID;
    private long donutQuantity;
    private LocalDateTime orderDate;
    private int orderStatus;
    
    @Transient
    private int approximateWaitTime; //This attribute doesn't inserted into the database tables
 
    public Order() 
    {
  
    }
 
    public Order(long clientID, long donutQuantity,LocalDateTime orderDate,int orderStatus) 
    {
         this.clientID = clientID;
         this.donutQuantity = donutQuantity;
         this.orderDate = orderDate;
         this.orderStatus = orderStatus;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	//To create our id values automatically
    public long getId() 
    {
        return id;
    }
    public void setId(long id) 
    {
        this.id = id;
    }
 
    @Column(name = "client_ID", nullable = false)
    public long getClientID() 
    {
        return clientID;
    }
    public void setClientID(long clientID) 
    {
        this.clientID = clientID;
    }
 
    @Column(name = "donut_quantity", nullable = false)
    public long getDonutQuantity() 
    {
        return donutQuantity;
    }
    public void setDonutQuantity(long donutQuantity) 
    {
        this.donutQuantity = donutQuantity;
    }
    
    @Column(name = "order_date", nullable = false)
    public LocalDateTime getOrderDate() 
    {
        return orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) 
    {
        this.orderDate = orderDate;
    }
    
    @Column(name = "order_status", nullable = false)
    public int getOrderStatus() 
    {
        return orderStatus;
    }
    
    public void setOrderStatus(int orderStatus) 
    {
        this.orderStatus = orderStatus;
    }
    
    @Transient
    public int getApproximateWaitTime() 
    {
    	return approximateWaitTime;
    }
    
    public void setApproximateWaitTime(int approximateWaitTime) 
    {
        this.approximateWaitTime = approximateWaitTime;
    }
    
 
    @Override
    public String toString() 
    {
        return "Order [id=" + id + ", clientID=" + clientID + ", donutQuantity=" + donutQuantity + "]";
    }
 
}