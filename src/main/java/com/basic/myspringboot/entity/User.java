package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "users") //테이블 명 지정
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // NULL 허용 X , 중복 허용 O

    @Column(unique = true, nullable = false)
    private String email; // NULL 허용 X , 중복 허용 X

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    //LocalDateTime.now(); => createdAt 컬럼에 현재 년월시분초 저장
    private LocalDateTime createdAt = LocalDateTime.now();
}
