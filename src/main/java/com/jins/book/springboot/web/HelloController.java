package com.jins.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 json을 반환하는 컨트롤러로 만들어줌
                // (예전에 @ResponseBody를 각 메소드마다 선언했던걸 한번에 사용할 수 있게 해줌)
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
