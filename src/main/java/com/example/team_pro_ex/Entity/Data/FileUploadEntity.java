package com.example.team_pro_ex.Entity.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor/* 숙소 사진 업로드를 위한 Entity */
public class FileUploadEntity {

    @Id @GeneratedValue
    private Long id;
    /* 사진이름 중복처리를 위해 필요한정보 UUID */
    private String uuid;

    private String contentType;
    private String name;
    private String originalFilename;
    private Long noticeSeq;
}
