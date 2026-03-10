package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    @Disabled //잠깐 일시정지(실행 안되도록) 어노테이션
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

    //2. Customer 조회 - 정상인경우
    @Test
    void testFindBy() {
        Optional<Customer> optionalCustomer = customerRepository.findById(1L); //정수와 구분하기 위해 Long타입인 경우 뒤에 L을 붙여줌
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            assertThat(customer.getId()).isEqualTo(1L);
        }else{
            System.out.println("Customer Not Found");
        }
        //ifPresent(Consumer)
        //Consumer의 추상메서드는 void accept(T t)
        optionalCustomer.ifPresent(customer -> System.out.println(customer.getCustomerName()));

        //orElseGet(Supplier)
        //Supplier의 추상메서드는 T get()
        Customer existCustomer = customerRepository.findById(2L).orElseGet(() -> new Customer());
        assertThat(existCustomer.getId()).isNull();
        //assertThat(existCustomer.getId()).isEqualTo(2L);
    }

    //2-1. Customer 조회 - 정상적이지 않은경우
    @Test
    void testFindByNotFound(){
        //orElseGet(Supplier)
        //Supplier의 추상메서드는 T get()
        Customer existCustomer = customerRepository.findById(2L)
                .orElseGet(() -> new Customer());  //조회 값이 없으면 빈 인자를 반환
        assertThat(existCustomer.getId()).isNull();
        //assertThat(existCustomer.getId()).isEqualTo(2L);

        //public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
        Customer notFoundCustomer = customerRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Customer Not Found")); //조회 값이 없으면 RuntimeException 반환 있으면 Customer 반환
    }
}