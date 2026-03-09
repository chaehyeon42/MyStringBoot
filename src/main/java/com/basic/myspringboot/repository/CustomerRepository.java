package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    // finder(query) method
    //1. customerID(고객번호, Unique - 중복불가)를 조회하는 finder 메서드
    Optional<Customer> finByCustomerId(String customerId);

    //2.CustomerName(고객명, 중복허용)로 조회하는 finder 메서드
    List<Customer> findByCustomerName(String customerName);
}
