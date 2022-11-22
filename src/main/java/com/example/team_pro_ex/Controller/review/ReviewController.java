package com.example.team_pro_ex.Controller.review;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Entity.review.Review;
import com.example.team_pro_ex.Security.Member.PrincipaDetailsMember;
import com.example.team_pro_ex.Service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    protected ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/mypetboard/review/insertreview")
    public void insertReview(@AuthenticationPrincipal PrincipaDetailsMember userDetails, Model model) {
        model.addAttribute(userDetails.getMember());
        System.out.println("-----GET insertreview");
    }

    @PostMapping("/mypetboard/review/insertreview")
    public String insertReview(@AuthenticationPrincipal PrincipaDetailsMember userDetails, Review review) {
        System.out.println("-----POST insertreview");
        review.setCreateDate(new Date());

        //시퀀스 아이디만 넣어줌 => entity에 @toString(계속 엔티티끼리 순환참조를 해서 스택오버플로우)
        reviewService.insertReview(review.setMember(Member.builder()
                .memberNumberSeq(userDetails.getMember()
                        .getMemberNumberSeq()).build()));

        // review.setMember(userDetails.getMember()) => 멤버를 넘겨줌
       // reviewService.insertReview(review.setMember(userDetails.getMember()));
        System.out.println("-----insertreview= " + review);
        return String.format("redirect:/mypetboard/review/reviewList?page=0&size=5");
    }
    //0929 카테고리 선택 후 검색기능
    @GetMapping("/mypetboard/review/reviewList")
    public String getreviewList(
            @RequestParam(value = "scategory",required = false, defaultValue = "")String scategory,
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword,
            Pageable pageable, Model model) {
        System.out.println("-----GET getreviewList");
        System.out.println("scategory= " + scategory);
        System.out.println("keyword= " + keyword);

        Page<Review> reviews = null;
            if(scategory.equals("title")){
                reviews = reviewService.findByTitle(pageable, keyword);
            }else if (scategory.equals("content")) {
                reviews = reviewService.findByContent(pageable, keyword);
            } else if (scategory.equals("writer")) {
                reviews = reviewService.findByWriter(pageable, keyword);
            }else{
                reviews = reviewService.findAll(pageable, keyword);
            }
        model.addAttribute("reviewList", reviews);
        model.addAttribute("scategory", scategory);
        model.addAttribute("keyword", keyword);
        return "mypetboard/review/reviewList";
    }

    @GetMapping("/mypetboard/review/getreview")
    public String getreview(Model model, Review review) {
        System.out.println("-----GET getreview");
        model.addAttribute("reviewList", reviewService.getReview(review));
        return "mypetboard/review/getreview";
    }
    @GetMapping("/mypetboard/review/removeReview")
    public String removereview(@AuthenticationPrincipal PrincipaDetailsMember userDetails, Review review) {
        System.out.println("-----GET removereview");
        Review findReview = reviewService.findById(review.getSeq());
        if(userDetails.getMember().getMemberNumberSeq() == findReview.getMember().getMemberNumberSeq()){
            reviewService.removeReview(review);
        }else{
            System.out.println("아이디가 아닙니다.");
        }
        return String.format("redirect:/mypetboard/review/reviewList?page=0&size=5");
    }
    @PostMapping("/mypetboard/review/updateReview")
    public String updatereview(@AuthenticationPrincipal PrincipaDetailsMember userDetails, Review review) {
        System.out.println("-----POST updatereview");

        Review findReview = reviewService.findById(review.getSeq());
        if(userDetails.getMember().getMemberNumberSeq() == findReview.getMember().getMemberNumberSeq()){
            reviewService.updateReview(review);
        }else{
            System.out.println("아이디가 아닙니다.");
        }
        return String.format("redirect:/mypetboard/review/reviewList?page=0&size=5");
    }
}
