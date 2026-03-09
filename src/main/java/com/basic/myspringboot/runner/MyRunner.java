package com.basic.myspringboot.runner;

import com.basic.myspringboot.config.CustomerVO;
import com.basic.myspringboot.property.MyBootProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;

    @Value("${myboot.age}")
    private int age;

    @Autowired
    private Environment environment;

    @Autowired
    private MyBootProperties properties;

    @Autowired
    private CustomerVO customerVO;

    //Logger 객체 생성
    private Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Logger 구현체 클래스명 = "+logger.getClass().getName()); //->ch.qos.logback.classic.Logger
        //Alt +Shift +Insert 하면 column selection mode로 변환
        logger.info("현재 활성화된 CustomerVO = {}", customerVO);
        logger.info("MyBootProperties getName() = {}", properties.getName());

        logger.info("${myboot.name}  = {}", name);
        logger.info("${myboot.age}  = {}", age);

        logger.debug("DEBUG 레벨");
        logger.debug("${myboot.fullName}  = {}", environment.getProperty("myboot.fullName"));
        logger.debug("VM 아규먼트 foo : {}", args.containsOption("foo"));
        logger.debug("Program 아규먼트 bar : {}", args.containsOption("bar"));

        /*
            default void forEach(Consumer<? super T> action)
            Consumer 인터페이스의 void accept(T t)
         */
        //Anonymous Inner Class 식
        args.getOptionNames()
                //Consumer 인터페이스를 구현하는 별도의 클래스 파일 없이,
                //직접 객체를 생성(new) 하면서 Consumer가 가진 accept 메서드를 재정의(Override) 함
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });//forEach는 파라미터로 Consumer 타입을 받음.


        //Argument 목록 출력하기 (람다식)
        args.getOptionNames()//Set<String>
                //        함수안에 함수를 작성할때 해당 함수 사용, 아규먼트와 바디를 화살표로 연결함.
                //        아규먼트   ->  바디     : 아규먼트에 넘어온걸 바디에서 사용할때 다른 문자열과 사용시 람다식 이용
                .forEach(name -> System.out.println(name));

        //Methoer Reference (메서드 레퍼런스)
        //람다식이 단순히 "전달받은 파라미터를 다른 메서드에 그대로 전달만 할 때" 더 줄여서 쓰는 방식. :: 기호를 사용하는 것이 특징
        args.getOptionNames()//Set<String>
                .forEach(System.out::println);//아규먼트에 넘어온걸 바디에서 바로 독단으로 사용할때

    }
}