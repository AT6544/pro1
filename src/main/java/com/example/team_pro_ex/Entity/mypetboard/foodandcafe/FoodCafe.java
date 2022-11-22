package com.example.team_pro_ex.Entity.mypetboard.foodandcafe;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import com.example.team_pro_ex.Entity.mypetboard.common.BoardCommon;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class FoodCafe extends BoardCommon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fc_seq" )
    private Long seq;

    private String detailCategory;

    private String imgname;


    @OneToMany(mappedBy = "foodCafe" , cascade = CascadeType.ALL)
    private List<Menu> menuList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberNumberSeq")
    private Member member;

}
