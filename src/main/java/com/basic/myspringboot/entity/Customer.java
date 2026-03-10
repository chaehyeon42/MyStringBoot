package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity //테이블하고 매핑되는 class
@Table(name = "customers")  //테이블 명 지정
@Getter @Setter
public class Customer {
    //@Id = Customer테이블의 Primary Key를 나타내는 id 어노테이션
    //@GeneratedValue =  SEQUENCE한 값을 자동으로 생성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerName;

}