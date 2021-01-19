package com.jins.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //이 어노테이션으로 스프링부트의 자동설정, 스프링 Bean읽기와 생성을 모두 자동으로 설정
                        //이 위치부터 설정을 읽어나가기 때문에 항상 프로젝트의 최상단에 위치해야만 함
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args); //SpringApplication.run으로 인해 내장 WAS를 실행
                                                        //내장 WAS란 외부에 별도의 WAS를 두지않고 애플리케이션을 실행할 때
                                                        // 내부에서 WAS를 실행하는 것으로 항상 서버에 톰캣을 설치할 필요가 없게되고
                                                        // 스프링 부트로 만들어진 Jar 파일로 실행하면 됨
                                                        // 스프링 부트에서는 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있도록
                                                        // 내장 WAS를 사용할 것을 권장함
    }
}
