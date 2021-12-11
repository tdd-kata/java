package com.markruler.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 등록일, 수정일은 어디서나 사용되지만
 * 등록자, 수정자는 사용되지 않는 곳도 있다.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class AuditTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
