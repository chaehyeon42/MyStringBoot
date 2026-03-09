package com.basic.myspringboot.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

//Spring Data : In Memory 데이터베이스
//H2 데이터베이스 기본 연결 정보 확인
@Component
@Order(1) //해당 데이터베이스 오더가 1이므로 MyRunner.java보다 우선순위가 높음
@Slf4j //롬복에 해당 어노테이션을 선언하면 로거 객체를 생성 해줘서 따로 작성 안해도 됨.
public class DatabaseRunner implements ApplicationRunner {
   //데이터소스를 인젝션 받음
    @Autowired
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("DataSource 구현객체는 {} ", dataSource.getClass().getName());

        //sql의 Connection
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData(); //db 메타 데이터 가져옴
            log.info("DB Product Name = {}", metaData.getDatabaseProductName());
            log.info("DB URL = {}", metaData.getURL());
            log.info("DB Username = {}", metaData.getUserName());
            log.info("DB Producer version={}",metaData.getDatabaseProductVersion());
        }
    }
}