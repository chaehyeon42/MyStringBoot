package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApplication.class, args);
	}


    @Bean
    public String hello(){
        System.out.println(("=====Stirng Bean 입니다.====="));
        return "Hello bean";
    }
}
