package com.jins.book.springboot.domain.posts;

import com.jins.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //엔티티 클래스에는 절대 Setter를 만들지 않는다(해당 클래스의 인스턴스값들이 언제 어디서 변해야하는지 코드상으로 명확하게 구분할수 없어서, 변경이 필요할 경우 목적과 의도를 나타낼수 있는 메소드추가)
@NoArgsConstructor
@Entity //테이블과 링크될 클래스를 나타냄(클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭 (ex:SalesManeger.java --> sales_manager table)
public class Posts extends BaseTimeEntity {

    @Id //해당 테이블의 PK필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성규칙을 나타냄. GenerationType.IDENTITY옵션을 추가해야 auto_increment가 됨
    private Long id;

    @Column(length = 500, nullable = false)     //문자열의 경우 VARCHAR2(255)가 기본값인데 사이즈를 500으로 늘리고 싶거나 타입을 TEXT로 변경하고 싶을 경우 @Column을 사용용
   private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //굳이 @Column을 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다. 사용하는 이유는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
