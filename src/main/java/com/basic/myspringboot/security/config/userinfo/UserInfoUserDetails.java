package com.basic.myspringboot.security.config.userinfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    private UserInfo userInfo;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.email=userInfo.getEmail();
        this.password=userInfo.getPassword();
        //roles 문자열에 : ROLE_ADMIN, ROLE_USER 이런식으로 저장됨
                                        //Stream 배열 -> (userInfo.getRoles().split(",")
        this.authorities= Arrays.stream(userInfo.getRoles().split(","))
                //람다식                 Stream 출력 : SimpleGrantedAuthority   / 입력값 : roleName
                .map(roleName -> new SimpleGrantedAuthority(roleName))
                // .map(SimpleGrantedAuthority::new)
                //Stream에 담겨진 SimpleGrantedAuthority를 List의 SimpleGrantedAuthority로 변경 -> List<SimpleGrantedAuthority>
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /*getUsername과 getPassword 메서드는 AuthenticationManager가
       인증처리를 할때 호출하는 메서드*/
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}