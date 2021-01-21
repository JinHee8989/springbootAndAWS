package com.jins.book.springboot.config.auth;

import com.jins.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
            .headers().frameOptions().disable() //h2-console화면을 사용하기 위해 해당 옵션들을 disable함
            .and()
                .authorizeRequests()            //URL별 권한 관리를 설정하는 옵션의 시작점. 이게 선언되어야 antMatchers(권한관리대상을 지정)옵션을 사용할 수 있음
                .antMatchers("/","/ss/**","/images/**","/js/**","/h2-cnsole/**").permitAll()    // permitAll() 옵션으로 전체가 열람가능한 권한 줌
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())                            // "/api/v1/**" 주소가지면 USER권한 가진 사람만 가능하도록 함
                .anyRequest().authenticated()   //anyRequest()는 설정된 값들 이외 나머지 URL들을 나타냄. authenticated()는 모든 인증된 사용자들에게만 허용
            .and()
                .logout()       //로그아웃 기능에 대한 설정 시작점.
                    .logoutSuccessUrl("/")  //로그아웃 성공하면 "/"주소로 이동
            .and()
                .oauth2Login()          //oauth2로그인 기능에 대한 설정 시작점
                    .userInfoEndpoint()         //oauth로그인 성공 후 사용자 정보를 가져올때 설정 담당
                        .userService(customOAuth2UserService);          //소셜 로그인 성공 시 후속조치를 진행할 UserService인터페이스 구현체를 등록
    }
}
