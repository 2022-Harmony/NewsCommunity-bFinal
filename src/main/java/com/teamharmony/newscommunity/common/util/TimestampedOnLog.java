package com.teamharmony.newscommunity.common.util;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/**
 * AccessLog에 적용할 Timestamped
 * @Author hyeoking
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimestampedOnLog {
    @CreatedDate
    private LocalDateTime createAt;
}
