package com.jins.book.springboot.config.auth;

import com.jins.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver { // HandlerMethodArgumentResolver는 조건에 맞는 경우 메소드가 있다면
                                                                                  // HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있다.
                                                                                  // HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addrgumentResolver()를 통해 추가해줘야한다.
                                                                                  // 추가안하면 컨트롤러 오기전부터 어노테이션 해놓은거 인식못해서 null포인트익셉션뜸
    private final HttpSession httpSession;

    @Override   //컨트롤러 메소드의 특정 파라미터를 지원하는지 판단함. 여기선 파라미터에 @LoginUser어노테이션이 붙어있고 파라미터클래스 타입이 SessionUser.class인 경우 true를 반환
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass= SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override   //파라미터에 전달할 객체를 생성, 여기서는 세션 객체를 가져옴
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
