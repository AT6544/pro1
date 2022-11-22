package com.example.team_pro_ex.Entity.time;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;
@Data
/*@MappedSuperclass : JPA Entity 클래스들이 basetime 을 상속할 경우 필드
  (createDate, modifiedDate)도 컬럼으로 인식하도록 해준다.
* */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    /*@CreatedDate : Entity 가 생성되어 저장될때 시간이 자동 저장된다.*/
    @CreatedDate
    private Date createDate;

    /*@LastModifiedDate : 조회한 Entity 값을 변경할 때 시간의 자동 저장된다.*/
    @LastModifiedDate
    private Date updateDate;

}
