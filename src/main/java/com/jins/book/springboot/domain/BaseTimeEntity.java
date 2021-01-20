package com.jins.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, BaseTimeEntity의 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)  //BaseTimeEntity클래스에 Auditing기능(audit은 '감시하다'라는 뜻으로 DB의 작업을 모니터링하고 기록정보를 수집하는 기능)을 포함
public class BaseTimeEntity {

    @CreatedDate //Entity생성되어 저장될때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   //조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
