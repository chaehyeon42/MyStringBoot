package com.basic.myspringboot.security.config.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Spring Security에서 사용자 정보를 조회하기 위한 사용자 정의 서비스 클래스.
 * UserDetailsService 인터페이스를 구현하여 DB의 데이터와 보안 시스템을 연결.
 */
@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository; // DB에 접근하여 사용자 정보를 조회하는 Repository

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호를 안전하게 암호화(Hashing)하기 위한 빈

    /**
     * 사용자가 로그인을 시도할 때 Spring Security가 호출하는 메서드.
     * * @param username 로그인 시 입력한 사용자 ID(여기서는 Email)
     * @return UserDetails 보안 시스템이 인식할 수 있는 형태의 사용자 정보 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  DB에서 이메일(username)을 통해 사용자 정보를 조회 (결과는 Optional로 감싸짐)
        Optional<UserInfo> optionalUserInfo = repository.findByEmail(username);

        //Stream과 유사한 Optional의 map 기능을 사용하여 데이터를 가공
        return optionalUserInfo
                // 값이 존재한다면, 조회된 UserInfo 엔티티를 UserInfoUserDetails 객체로 변환(매핑).
                .map(userInfo -> new UserInfoUserDetails(userInfo))
                // .map(UserInfoUserDetails::new) // 위 코드를 이처럼 '생성자 참조' 방식으로 더 깔끔하게 쓸 수 있음.

                // 만약 Optional이 비어있다면(사용자가 없다면) 예외를 던짐.
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

    /**
     * 회원 가입 시 새로운 사용자를 DB에 저장하는 메서드.
     * * @param userInfo 저장할 사용자 정보 객체
     * @return 저장 성공 메시지
     */
    public String addUser(UserInfo userInfo) {
        //보안을 위해 사용자가 입력한 평문 비밀번호를 암호화하여 다시 세팅.
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        //암호화된 정보가 포함된 객체를 DB에 저장.
        UserInfo savedUserInfo = repository.save(userInfo);

        //저장이 완료된 후 사용자 이름과 함께 성공 메시지를 반환.
        return savedUserInfo.getName() + " user added!!";
    }
}