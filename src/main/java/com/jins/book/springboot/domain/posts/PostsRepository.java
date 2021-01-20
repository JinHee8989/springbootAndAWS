package com.jins.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts,Long> { //JpsRepository<Entity클래스, PK타입>하면 기본적인 CRUD 메소드가 자동으로 생성됨
}
