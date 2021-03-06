package com.jins.book.springboot.web;

import com.jins.book.springboot.config.auth.LoginUser;
import com.jins.book.springboot.config.auth.dto.SessionUser;
import com.jins.book.springboot.service.posts.PostsService;
import com.jins.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){      //@LoginUser 어노테이션쓰면  어디서든 세션값 가져올 수 있음
        model.addAttribute("posts",postsService.findAllDesc());
//        SessionUser user = (SessionUser)httpSession.getAttribute("user");  //@LoginUser 어노테이션으로 불필요해져서 주석처리함

        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }


}
