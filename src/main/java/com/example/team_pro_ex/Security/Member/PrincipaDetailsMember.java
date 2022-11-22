package com.example.team_pro_ex.Security.Member;

import com.example.team_pro_ex.Entity.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipaDetailsMember implements UserDetails {

    @Getter
    @Setter
    private Member member;

    @Autowired
    PrincipaDetailsMember(Member member){
        this.member = member;
    }
    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                //MemberEntity에 있는 컬럼 role에 MEMBER가 있는데 이 앞에 ROLE_을 붙인다는 의미
                return "ROLE_" + member.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }
    //패스워드를 리턴 : "{noop}"을 붙이지 않으면 password부분에 {id}값을 붙여준다.

    //아이디를 리턴
    @Override
    public String getUsername() {
        return member.getId();
    }
    //계정이 완료되지 않았나?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 잠기지 않았나?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //계정의 비밀번호가 만료되지 않았나? 너무 오래 사용한 거 아니니? ex) 1년이 지나지 않았니?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정이 활성화되어 있니?
    @Override
    public boolean isEnabled() {
        return true;
    }
}

