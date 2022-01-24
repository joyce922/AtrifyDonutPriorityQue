package com.springboot.demo.databases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.databases.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{

}