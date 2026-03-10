package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //이름은 값이 여러개 나올수 있음
    List<User> findByNameContains(String name);
    //이메일은 중복 허용 X
    Optional<User> findByEmail(String email);
}