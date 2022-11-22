package com.example.team_pro_ex.Entity.mypetboard.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FoodCafeImage {

    @Id
    @GeneratedValue
    private Long seq;

    private String uuid;

    private String contentType;

    private String name;

    private String originalFilename;

    private Long foodcafeSeq;
}

