package com.example.team_pro_ex.Security.Member;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipaDetailsMemberService implements UserDetailsService {

    // 멤버 레파지토리 선언만 하면 안되는데 @RequiredArgsConstructor 어노테이션을 선언하면 된다!
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //member에서 member.id를 찾는다. null이 아닐경우 PrincipaDetailsMember(member)를 리턴해준다.

        Member member = memberRepository.findById(id);
        if(member != null){
            return new PrincipaDetailsMember(member);
        }
        return null;
    }
}

