package com.example.team_pro_ex.Entity.mypetboard.common;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomImage_seq")
    private Long seq;

    private String uuid;

    private String contentType;

    private String name;

    private String originalFilename;

    private Long roomSeq;

    private Long accSeq;

}