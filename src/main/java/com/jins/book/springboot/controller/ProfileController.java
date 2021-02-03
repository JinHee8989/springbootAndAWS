package com.jins.book.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping(value="/profile")
    public String profile(){
        List<String> profiles = Arrays.asList(env.getActiveProfiles()); //env.getActiveProfiles()는 현재 실행중인 ActiveProfile을 모두 가져옴
                                                                        // 즉, real,oauth,real-db 등이 활성화되어있으면 3개가 모두 들어있음

        List<String> realProfiles = Arrays.asList("real","real1","real2");  // 여기서 real, real1, real2 모두 배포에 사용되므로 하나라도 있으면 그 값을 반환
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
