package com.example.team_pro_ex.Entity.mypetboard.accommodation;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Entity.mypetboard.common.BoardCommon;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accommodation extends BoardCommon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Acc_seq")
    private Long seq;

    /* 숙소 카테고리 (호텔, 모텔, 펜션) */
    private String detailCategory;

    /* 숙소 대표 사진 이름 저장 */
    private String imgname;

    //조인 외래키 설정 1:N  양방향 매핑 참조삭제(숙소:룸)
    @OneToMany(mappedBy = "accommodation" , cascade = CascadeType.ALL)
    private List<Room> RoomList;

    @OneToMany(mappedBy = "accommodation" , cascade = CascadeType.ALL)
    private List<AccommodationAnswer> accommodationAnswerList;

    //조인 사업자회원:숙소
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberNumberSeq")
    private Member member;



    public static Room createRoom(Accommodation accommodation){
        Room room = new Room();
        room.setAccommodation(accommodation);
        return room;
    }

}
