package com.example.team_pro_ex.Entity.mypetboard.hospital;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Entity.mypetboard.common.BoardCommon;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital extends BoardCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq" )
    private Long seq;

    private Long categorySeq;

    private String detailCategory;


    private String imgname;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberNumberSeq")
    private Member member;
}
