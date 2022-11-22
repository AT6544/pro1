package com.example.team_pro_ex.Service.review;

import com.example.team_pro_ex.Entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewList(Review review);

    /** 문의사항 insert */
    void insertReview(Review review);

    /** 문의사항 상세page */
    Review getReview(Review review);

    /** 문의사항 삭제를 위한것 */
    Review findById(Long seq);

    /** 문의사항 업데이트 */
    void updateReview(Review review);

    /** 문의사항 삭제*/
    void removeReview(Review review);

//    List<Review> searchReviewAll(String keyword);

    /** 문의사항 전체 카테고리 검색기능*/
    Page<Review> findAll(Pageable pageable, String keyword);

    /** 문의사항 카테고리 "제목" 선택 후 입력받은 키워드로 검색*/
    Page<Review> findByTitle(Pageable pageable, String keyword);

    /** 문의사항 카테고리 "내용" 선택 후 입력받은 키워드로 검색*/
    Page<Review> findByContent(Pageable pageable, String keyword);

    /** 문의사항 카테고리 "작성자" 선택 후 입력받은 키워드로 검색*/
    Page<Review> findByWriter(Pageable pageable, String keyword);

    //회원 아이디(자신의 아이디)로만 문의사항 삭제하기 위해서
    public List<Review> findALLMemberorReview();

}
