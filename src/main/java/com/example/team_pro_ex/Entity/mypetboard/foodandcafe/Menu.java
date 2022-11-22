package com.example.team_pro_ex.Entity.mypetboard.foodandcafe;

import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.FoodCafe;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
//@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String menuName;

    private int menuPrice;

    private String menuInfo;

    private String menuType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fc_seq")
    private FoodCafe foodCafe;

}
