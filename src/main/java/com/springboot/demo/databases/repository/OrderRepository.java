package com.springboot.demo.databases.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.demo.databases.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query(value="SELECT * FROM orders WHERE client_id = ?1 AND order_status = 1 ;",nativeQuery=true) 
    List<Order> getOrderWithClientID(Long clientID);
    
    @Query(value="SELECT * FROM ORDERS WHERE order_status = 1 "
    		+ "ORDER BY CASE WHEN client_id > 1000 THEN order_date END ASC,"
    		+ "CASE WHEN client_id < 1000 THEN order_date END ASC ;",nativeQuery=true) 
    List <Order> getOrdersByManager();   
    
    @Modifying
    @Query(value="UPDATE orders SET order_status = 2 WHERE id = ?1 ;",nativeQuery=true) 
    @Transactional 
    void getNextOrdersByEmployee(Long orderID);
    
    @Modifying
    @Query(value="UPDATE orders SET order_status = 3 WHERE client_id = ?1 ;",nativeQuery=true) 
    @Transactional 
    void getCancelOrder(Long clientID);
    
    
}