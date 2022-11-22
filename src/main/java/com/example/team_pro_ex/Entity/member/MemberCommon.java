package com.example.team_pro_ex.Entity.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@MappedSuperclass
public class MemberCommon {
    @Column(nullable = false)
    private String Id;

    @Column(nullable = false)
    private String Pw;

    @Column(nullable = false)
    private String Name;

    @Column(nullable = false)
    private int PhoneNumber;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

}
