package com.example.team_pro_ex.Security.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //security 어노테이션 활성화
public class SerurityConfigMember {

    //로그인 실패 핸들러 의존성 주입
    private final AuthenticationFailureHandler customAuthFailureHandler;



    @Bean // = > 생성해서 주입한다.
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
                    // qcsrf(). =>  방어하는 것. 로그아웃 처리할 떄 csrf토큰도 보내준다.
        return http.csrf().disable().
                authorizeRequests()
                //매니저는 회원, 사업자에 접근할 수 있다.
                // antMatchers("/**").permitAll() => 모든 경로에 권한없이 접근가능
                // antMatchers().authenticated() => 모든 요청에 인증된
                // .antMatchers("/Member/**") => 와일드카드로 인해 모든 거 허용
                // .antMatchers("/Member/**").hasRole("Role_MANAGER") => 우리 프로젝트 Manager는 회원, 사업자
                // 경로의 모든 경로를 Role_MANAGER가 접근할 수 있다.
                //페이지 권한설정 => 매니저의 권한을 넣으려면 Member옆에 access와 같이 입력을 해줘야한다.
                //시큐리티 권한을 사용하려면 반드시 ROLE_MEMBER와 같이 사용하여야 한다.
                // .regexMatchers() => antMatcher() 메소드가 Ant 스타일 와일드카드를 포함한 패스로 동작하는 반면,
                // 요청 패스에 정규 표현식을 사용할 수 있는 regexMatchers() 메소드도 있다.
                .regexMatchers("/Member/[^(mJoin/Join)|(Login)|(mUpdate/Update)|(mDelete/upDelete)].*").
                access("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('MANAGER')")
                .antMatchers("/mypetboard/**")//리퀘스트랑 맵핑
                .access("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('MANAGER')")
                //설정해준거 외에는 어디든지 접근가능하다. .anyRequest().permitAll()
                .anyRequest().permitAll()
                .and()
                //로그인 권한설정
                .formLogin()
                .loginPage("/Member/Login")
                .usernameParameter("id")
                .loginProcessingUrl("/logins")
                .defaultSuccessUrl("/")
                .failureHandler(customAuthFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout") //시큐리트에서 만들어진 logout을 걸쳐서 logoutSuccessUrl에 설정한 페이지로 넘어간다
                .logoutSuccessUrl("/")
                //로그아웃 후 세션 전체 삭제여부
                .invalidateHttpSession(true).deleteCookies("JSESSIONID", "remember-me")
                //403 예외처리 핸들링
                .and().build();

    }



//     @Override
//     // 이거는 메모리에서 우리꺼로 하자면 사용자와 사업자, 관리자를 만듬
//     // 로그인을 할 때 우리 디비에 있는 사용자의 아이디를 사용할수가 없어....
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.inMemoryAuthentication() => 메모리 상에 존재하는 계정을 만들 수 있다. 서버를 껏다 키면,
//        // 이 계정은 새로 만들어지거나 없어진다
//
//        //withUser() => 사용할 계정명을 설정한다.
//
//        //password() => 사용할 비밀번호를 설정한다. 이 때 반드시 passwordEncoder를 이용해서 비밀번호를 복호화해야한다.
//        // 그렇지 않으면 Exception이 발생한다.
//
//        //roles()  =>계정의 권한을 설정한다.
//        String password = encoder().encode("1111");
//        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN", "MANAGER");
//        auth.inMemoryAuthentication().withUser("manager").password(password).roles("ADMIN","MANAGER","MEMBER");
//    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }



}
//                .regexMatchers("/businessMember/[^(bmJoin/bm_Join)].*").
//                access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")