package com.example.team_pro_ex.Entity.mypetboard.beauty;

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
public class Beauty extends BoardCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Beauty_seq" )
    private Long seq;

    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberNumberSeq")
    private Member member;

}
