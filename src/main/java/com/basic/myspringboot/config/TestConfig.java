package com.basic.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public CustomerVO customerVO(){
        return CustomerVO.builder() //CustomerVOBuilder라는 이너클래스가 반환
                .mode("테스트 환경")
                .rate(0.5)  //rate까지는 CustomerVOBuilder라는 이너클래스가 반환
                .build();   //다시 CustomerVO를 반환해야하기 때문에 다시 지정
    }
}
