package com.jins.book.springboot;

import com.jins.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴(여기서는 SpringRunner라는 스프링 실행자)
                             //즉, 스프링부트테스트와 JUnit사이에 연결자
// 역할을 함
@WebMvcTest(controllers = HelloController.class) //스프링 어노테이션 중에서 web에 집중할 수 잇는 어노테이션으로
                                                 //선언할 경우 @Controller, @ControllerAdvice등을 사용할 수 있음
                                                 //(단, @Service, @Component, @Repository는 사용불가)
public class HelloControllerTest {

    @Autowired  //스프링이 관리하는 Bean을 주입받음
    private MockMvc mvc;    // 웹API를 테스트할 때 사용, 스프링MVC테스트의 시작점임

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello"))  //MockMvc를 통해 /hello주소로 HTTP GET요청을 함
                                              //체이닝 지원으로 아래처럼 여러 검증기능을 이어서 선언할 수 있음
                .andExpect(status().isOk())     //mvc.perform결과를 검증함. HTTP Header의 Status를 검증(200,400,500등의 상태)
                                                //여기서 OK는 200인지 아닌지를 검증
                .andExpect(content().string(hello));    //mvc.perform결과를 검증함. 응답 본문의 내용을 검증.
    }
}

