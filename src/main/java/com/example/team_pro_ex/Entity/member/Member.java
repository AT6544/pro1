package com.example.team_pro_ex.Entity.member;

import com.example.team_pro_ex.Entity.Base.member_BaseEntity;
import com.example.team_pro_ex.Entity.review.Review;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

//@AllArgsConstructor : 모든 매개변수를 갖는 생성자
//@NoArgsConstructor(access = AccessLevel.PROTECTED) : 매개변수 없는 생성자
//@Builder

//@Entity JPA가 이 객체를 기준으로 table을 만들어야 한다고 선언
@Entity
@Getter
@Setter
@NoArgsConstructor // @NoArgsConstructor 이걸로 생성자를 채워서 만든다. (access = AccessLevel.PROTECTED)
@AllArgsConstructor // 전부다 꽉채워진 생성자를 만든다.
@Builder // @Builde 만들면 비어있는 생성자를 만들었다.
@DynamicInsert
@DynamicUpdate
public class Member extends member_BaseEntity {


    //builder패턴을 쓰면 중요하다고 생각 되는 것들은 builder를 사용하여 관리를 하고
    //그 외 요소들은 setter로 받는다.
    //builder를 사용하면 좋은 점? null처리에 대해서 쉽다.
    //나 이외의 다른 팀원이 실행할 경우 나는 어느 부분에서 null이 생겨날지 느낄 수 있지만
    //다른 팀원들은 잘 알 수가 없기 떄문이다.

    //Entity의 튜플의 순서에 따라 들어가는 값이 달라진다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNumberSeq;


    @Column(name = "member_id", length = 20, nullable = false, unique = true)
    //  "(?=.*[0-9])(?=.*[a-z]).{8,16}" => 정규식 : (?=.*[0-9]) = 0~9, (?=.*[a-z]) = 소문자 a~z ,
    //  {8,16} = 8글자 이상 16자 이하 입력가능
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{8,16}", message = "아이디는 8~16자 영문 소문자, 숫자를 사용하세요.")
    private String id;  // 아이디

    @Column(name = "member_password",length = 70)
    //회원 탈퇴를 할 때 어쩔 수 없이 사용해야 됨
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,70}", message = "영문 대 소문자, 숫자를 포함한 특수문자를 사용하세요.")
    private String password; // 비밀번호

    @Column(name = "member_name")
    private String name; // 이름

    @Pattern(regexp = "(?=.*[0-9])(?=.*\\W)(?=\\S+$).{10}", message = "출생년도는 예)20220901")
    @Column(name = "member_year")
    private String year; // 펫주인 생년월일

    @Pattern(regexp = "(?=.*[0-9]).{11}", message = "핸드폰 번호는 예)010xxxxxxxx")
    @Column(name = "member_phone_number")
    private String phoneNumber; // 핸드폰 번호

    @Column(name = "member_address", length = 50)
    private String address; // 주소

    @Column(name = "member_pet_T", length = 20)
    private String petT; //--펫 종류

    @Column(name = "member_pet_D")
    private String petD; //-- 펫 출생

    @Column(name = "member_pet_W", length = 10)
    private Integer petW; //--펫 몸무게

    private String role;

    @Column(name = "member_join_M", length = 1, nullable = false)
    private String joinM = "Y"; //--가입상태

    // mappedBy => 본인 클래스를 적는다.
    // review에 선언한 Member변수를 적음
    // fetch = FetchType.EAGER = 한번 조회할 때 entity 다 가져옴
    // fetch = FetchType.LAZY = 조회할때만 가져옴
    // proxy = 주입하려면 proxy가 필요하다. => entry point가 proxy다
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>(); // new ArrayList<>() => 초기화한다.

    public Member setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        return this;
    }
}

