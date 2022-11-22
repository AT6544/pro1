package com.example.team_pro_ex.Entity.mypetboard.accommodation;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder /* 숙소 룸 등록*/
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_seq")
    private Long seq;

    //외래키 설정
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Acc_seq")
    private Accommodation accommodation;

    /* 룸 이름 */
    @Column(nullable = false)
    private String roomName;

    /* 룸 정보 */
    private String roomInfo;

    /* 룸 타입(호텔, 모텔, 펜션) */
    @Column(nullable = false)
    private String roomType;

    /* 룸 가격 */
    @Column(nullable = false)
    private Integer roomPrice;




    }










