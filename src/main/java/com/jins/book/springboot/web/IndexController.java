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


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){      //@LoginUser를 생성함으로 인해 이 어노테이션을 사용하면 어디서든지 세션정보를 가져올 수 있다.
        model.addAttribute("posts",postsService.findAllDesc());

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
