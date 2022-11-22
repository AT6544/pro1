package com.example.team_pro_ex.Entity.mypetboard.common;

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

//호텔,병원,음식점 및 카페 , 애견미용 공통적인 필드값
public class BoardCommon {

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String StoreTelephone;


    private String PetSize;

    @Column(nullable = false)
    private String postcode;

    private String detailAddress;

    private String Address;
    @Column(nullable = false)
    private String Info;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;


    private Date BookingDate;
}
