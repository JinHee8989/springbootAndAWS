package com.jins.book.springboot.config.auth.dto;

import com.jins.book.springboot.domain.user.Role;
import com.jins.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes){       //OAuth2User에서 반환하는 사용자 정보는 Map이기때문에 하나하나 변환해줘야함
        return ofGoogle(userNameAttributeName,attributes);
    }

        private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
            return OAuthAttributes.builder()
                    .name((String)attributes.get("name"))
                    .email((String)attributes.get("email"))
                    .picture((String)attributes.get("picture"))
                    .attributes((attributes))
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }


    public User toEntity(){         //userEntity생성
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
