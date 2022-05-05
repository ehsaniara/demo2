package com.example.demo2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@MappedSuperclass
public abstract class EntityAudit {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createDate;

    @LastModifiedDate
    @Column()
    private Instant updateDate;
}
