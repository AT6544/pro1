package com.example.team_pro_ex.Entity.notice;

/*
 * @package : com.example.petproject01.entity;
 * @name : Notice.java
 * @date : 2022-09-19 오후 5:30
 * @author : Rubisco
 * @version : 1.0.0
 * @modifyed :
 * @description : 공지사항
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@DynamicInsert
@DynamicUpdate

@Getter
@Setter
@ToString
@Entity
public class NoticeNotice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long seq;

    @Column(length = 40, nullable = false)
    private String title;

    @Column(updatable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @ColumnDefault("0")
    private long cnt;

    private String keyword;

    @OrderBy("r_seq desc")
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"notice"}) //reply 클래스에 notice 객체를 다시 참조하는 무한 참조를 막아줌
    private List<Reply> replyList;

}

