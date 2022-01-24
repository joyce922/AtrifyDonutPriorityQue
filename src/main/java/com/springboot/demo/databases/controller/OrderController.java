package com.springboot.demo.databases.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.databases.exception.ResourceNotFoundException;
import com.springboot.demo.databases.model.Order;
import com.springboot.demo.databases.model.SendingOrder;
import com.springboot.demo.databases.model.ClientQueue;
import com.springboot.demo.databases.repository.OrderRepository;
import com.springboot.demo.databases.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class OrderController 
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    //It's a getting all orders example with "@GetMapping"
    /*@GetMapping("/orders")
    public List<Order> getAllOrders() 
    {
        return orderRepository.findAll();
    }*/

    //It's a getting order by its id example with "@GetMapping"
    /*@GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderID)throws ResourceNotFoundException 
    {
    	Order order = orderRepository.findById(orderID).orElseThrow(() -> 
    	new ResourceNotFoundException("Order not found for this id : " + orderID));
        return ResponseEntity.ok().body(order);
    }*/
    
    // For allowing the manager to see orders
    @GetMapping("/orders_manager")
    public List<Order> getAllOrdersByManager () throws ResourceNotFoundException 
    {
    	int sum = 0;

    	List<Order> orderManager = orderRepository.getOrdersByManager(); // Getting the orders list according to requirements
    	if(orderManager.size() == 0)	// Looking for if there is any orders
    	{
    		throw new ResourceNotFoundException("There is not any orders" );
    	}
        for(int i = 0; i < orderManager.size(); i++)
        {
        	sum += orderManager.get(i).getDonutQuantity();
        	//Calculating the approximate wait time
        	if((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), orderManager.get(i).getOrderDate().plusMinutes(5)) <= 0)
        	{
        		orderManager.get(i).setApproximateWaitTime(0);
        	}
        	else
        	{
        		if(sum <= 50)
        		{
        			orderManager.get(i).setApproximateWaitTime((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), 
                			orderManager.get(i).getOrderDate().plusMinutes(5)));
        		}
        		else
        		{
        			orderManager.get(i).setApproximateWaitTime((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), 
                			orderManager.get(i).getOrderDate().plusMinutes(5))*(int)(Math.ceil((sum)/50)));
        		}      	
        	}         	    		      	
        }
        return orderManager;   	  	
    }
    
    
    
    
    //For Jim getting it's first orders according to instructions
    @GetMapping("/orders_client_que/{customerID}")
    public ResponseEntity<ClientQueue> getOrdersQueByClient (@PathVariable(value = "customerID") Long customerID) 
    		throws ResourceNotFoundException 
    {
    	int sum = 0;
    	ClientQueue cq = new ClientQueue();
    	List<Order> orders = orderRepository.getOrdersByManager();	//getting order list from our database with our query
    	if(orders.size() == 0)
    	{
    		throw new ResourceNotFoundException("There is not any orders" );	//checking if there is any order or not
    	}
    	
        for(int i = 0; i < orders.size(); i++)
        {
        	sum += orders.get(i).getDonutQuantity();
        	if(orders.get(i).getClientID() == customerID)	//controling the donut count if it's surpass the 50
        	{
        		cq.setQueuePosition(i+1);
            	if((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), orders.get(i).getOrderDate().plusMinutes(5)) <= 0)
            	{
            		cq.setApproximateWaitTime(0);
            	}
            	else
            	{
        		cq.setApproximateWaitTime((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), 
        				orders.get(i).getOrderDate().plusMinutes(5))*(int)(Math.ceil((sum)/50)));
            	}
        		break; 
        	}     		      	
        }
    	if(cq.getQueuePosition() == 0)	//controlling if the order is still in the queue
    	{
			throw new ResourceNotFoundException("Your order is either canceled or completed" );
    	}
        return ResponseEntity.ok().body(cq); //returning the list   	  	
    }
    
    
    
    
    //For Jim getting it's first orders according to instructions
    @GetMapping("/orders_employee")
    public List<Order> getOrdersByEmployee () throws ResourceNotFoundException 
    {
    	int sum = 0;
    	List<Order> orders = orderRepository.getOrdersByManager();	//getting order list from our database with our query
    	List<Order> orders_employee = new ArrayList<Order>();
    	if(orders.size() == 0)
    	{
    		throw new ResourceNotFoundException("There is not any orders" );	//checking if there is any order or not
    	}
    	
        for(int i = 0; i < orders.size(); i++)
        {
        	sum += orders.get(i).getDonutQuantity();
        	if(sum <= 50)	//controling the donut count if it's surpass the 50
        	{
        		orders_employee.add(orders.get(i));
        	}
        	else
        	{
        		break;
        	}
        }   	
        return orders_employee;	//returning the list   	  	
    }
    
    //For Jim getting the next orders according to instructions
    @GetMapping("/orders_employee_next")
    public List<Order> getNextOrdersByEmployee () throws ResourceNotFoundException 
    {
    	List<Order> orders = getOrdersByEmployee();	//getting order list from our database with our query
        for(int i = 0; i < orders.size(); i++)
        {
        	orderRepository.getNextOrdersByEmployee(orders.get(i).getId());	//updating the previous orders "order_status" as "completed"
        }
        orders = getOrdersByEmployee();	//getting the next orders list to process
        return orders;
    }		
    
    //For customers to check their orders queue position and approximate wait time
    /*@GetMapping("/orders_client_que/{customerID}")
    public ResponseEntity<ClientQueue> getOrdersQueByClient (@PathVariable(value = "customerID") Long customerID) 
    throws ResourceNotFoundException 
    {
    	ClientQueue cq = new ClientQueue();
    	List<Order> orderManager = orderRepository.getOrdersByManager();	//getting the order lists
    	for(int i = 0;i < orderManager.size(); i++)
    	{
    		if(orderManager.get(i).getClientID() == customerID)	//getting the order according to the customer id
    		{
    			cq.setQueuePosition(i + 1);	
    			cq.setApproximateWaitTime(orderManager.get(i).getApproximateWaitTime());
    			break;
    		}
    	}
    	if(cq.getQueuePosition() == 0)	//controlling if the order is still in the queue
    	{
			throw new ResourceNotFoundException("Your order is either canceled or completed" );
    	}
    	return ResponseEntity.ok().body(cq);
    }*/
    

    
    // For creating order
    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody SendingOrder sendingOrder) 
    		throws ResourceNotFoundException //For adding items to the queue(customer ID, donut quantity)
    {
    	Order order = new Order();
    	if(sendingOrder.getDonutQuantity() > 50)
    	{
    		throw new ResourceNotFoundException("You can't order more than 50 donuts");	
    		//Our cart can only takes 50 donuts and we cannot skipping, changing, or splitting any orders.
    	}	//That's why we are checking the donut count if it is exceed the limit																				
    	if(sendingOrder.getClientID() == 0)	//Checking if there is an client ID
    	{
    		throw new ResourceNotFoundException("Please enter the client ID");
    	}    	
    	if(customerRepository.findById(sendingOrder.getClientID()).orElseThrow(() -> 
    	new ResourceNotFoundException("Customer not found for this id : " + order.getClientID())) != null)
    	{
    		//Checking if there is a client with this ID
    	}
    	if(orderRepository.getOrderWithClientID(sendingOrder.getClientID()).size() > 0) 
    		//Checking if there is already an order for this client.
    	{
    		throw new ResourceNotFoundException("There is an order with this client ID");
    	}
    	if(sendingOrder.getDonutQuantity() == 0)	//Checking if there is a donut quantity
    	{
    		throw new ResourceNotFoundException("Please enter the donut quantity");
    	}
    	order.setClientID(sendingOrder.getClientID());
    	order.setDonutQuantity(sendingOrder.getDonutQuantity());
    	order.setOrderStatus(1);
    	order.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return orderRepository.save(order);
    }

    //For canceling order according to client ID
    @PutMapping("/orders_cancel_order/{customerID}")  
    public Map<String, Boolean> getCancelOrder (@PathVariable(value = "customerID") Long customerID) throws ResourceNotFoundException
    {
        Map<String, Boolean> response = new HashMap<>();

    	if(customerRepository.findById(customerID).orElseThrow(() -> 
    	new ResourceNotFoundException("Customer not found for this id : " + customerID)) != null)
    	{
    		//Checking if there is a client with this ID
    	}
        orderRepository.getCancelOrder(customerID);	//using our canceling function	
        response.put("canceled", Boolean.TRUE);	//returning canceled boolean value as true
    	
        return response;
    }
    
       
    //It's a updating example with @PutMapping
    /*@PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long orderId,
         @Valid @RequestBody Order orderDetails) throws ResourceNotFoundException {
    	Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

    	order.setDonutQuantity(orderDetails.getDonutQuantity());
    	order.setClientID(orderDetails.getClientID());
    	order.setOrderStatus(orderDetails.getOrderStatus());
    	order.setOrderDate(orderDetails.getOrderDate());
        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }*/


    //It's a deleting example with "@DeleteMapping"
    /*@DeleteMapping("/orders/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long orderID)
         throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderID)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + orderID));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }*/

}
