package com.example.team_pro_ex.Entity.review;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Entity.time.BaseTime;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Setter
//@ToString // member, review에 @ToString 객체주소가 나옴 연관관계를 걸거면 한쪽에다만 걸어라 둘다
@Entity
//@ToString 을 쓸거면  @ToString.Exclude을 이용해서 컬럼 위에다가 넣어라
// ex
//@Column(name = "review_Title",length = 40, nullable = false)
//@ToString.exclude
//private String title;
public class Review extends BaseTime {
    //문의사항~!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_Seq")
    private Long seq;
    //제목
    @Column(name = "review_Title",length = 40, nullable = false)
    private String title;
    //작성자
    @Column(name="review_Writer")
    private String writer;
    //내용
    @Column(name= "review_Content",length = 500, nullable = false)
    private String content;
    //분류
    @Column(name="review_Category")
    private String category;
    //검색할 떄 키워드
    @Column(name="review_Keyword")
    private String keyword;

    // optional = false => 외래키니까 무조건 존재해야한다.
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name="memberNumberSeq")
    private Member member;

//    private String scategory;

    public Review setMember(Member member){
        Assert.notNull(member, "member must Not be null");
        if(this.member != null){ // member가 null일 경우 연관관계를 끊어주고
            this.member.getReviewList().remove(this);
        }
        this.member = member;
        System.out.println("ddddddddd"+member.getReviewList());
        //member.getReviewList().add(this);
        return this;
    }



}
