package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // test 데이터기 때문에 해당 어노테이션을 입력하면 테이블에 반영 X(롤백이 자동으로 됨)
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    //1. Customer 등록
    @Test
    @Rollback(value = false) // @Transactional시 DB에 반영 해달라는 어노테이션-롤백 처리 하지 않도록
    void testCreate() {
        //Given(준비단계)
        Customer customer = new Customer();
        customer.setCustomerId("A004");
        customer.setCustomerName("스프링부트4");
        //When(실행단계)
        Customer addCustomer = customerRepository.save(customer);
        //Then(검증단계)
        assertThat(addCustomer).isNotNull();
        assertThat(addCustomer.getCustomerName()).isEqualTo("스프링부트4");
    }

}