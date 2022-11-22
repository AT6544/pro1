package com.example.team_pro_ex.Entity.mypetboard.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MenuImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuImage_seq")
    private Long seq;

    private String uuid;

    private String contentType;

    private String name;

    private String originalFilename;

    private Long menuSeq;

    private Long foodCafeSeq;

}
