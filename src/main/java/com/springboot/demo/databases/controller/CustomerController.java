package com.springboot.demo.databases.controller;

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
import com.springboot.demo.databases.model.Customer;
import com.springboot.demo.databases.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class CustomerController 
{
    @Autowired
    private CustomerRepository customerRepository;

    //It's a getting all orders example with "@GetMapping"
    /*@GetMapping("/customers")
    public List<Customer> getAllEmployees() 
    {
        return customerRepository.findAll();
    }*/

    //It's a getting customer by its id example with "@GetMapping"
    /*@GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId) throws ResourceNotFoundException 
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id : " + customerId));
        return ResponseEntity.ok().body(customer);
    }*/
    
    
    // For creating accounts
    @PostMapping("/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) throws ResourceNotFoundException
    {
    	if(customer.getFirstName() == null || customer.getFirstName().isEmpty() == true) //controlling if they send a first name
    	{
    		throw new ResourceNotFoundException("Please enter a name" );

    	}
    	if(customer.getLastName() == null || customer.getLastName().isEmpty() == true)	//controlling if they send a last name
    	{
    		throw new ResourceNotFoundException("Please enter a surname" );

    	}
    	if(customer.getEmailId() == null || customer.getEmailId().isEmpty() == true)	//controlling if they send an email
    	{
    		throw new ResourceNotFoundException("Please enter an email" );

    	}
        return customerRepository.save(customer);
    }

    
    //It's a updating example with @PutMapping
    /*@PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
         @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customer.setEmailId(customerDetails.getEmailId());
        customer.setLastName(customerDetails.getLastName());
        customer.setFirstName(customerDetails.getFirstName());
        final Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }*/

    //It's a deleting example with "@DeleteMapping"
    /*@DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
         throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + customerId));

        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }*/
}