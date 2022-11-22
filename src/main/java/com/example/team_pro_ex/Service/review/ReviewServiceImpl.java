package com.example.team_pro_ex.Service.review;

import com.example.team_pro_ex.Entity.review.Review;
import com.example.team_pro_ex.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepo;

    /** reviewRepo 생성자 주입 */
    @Autowired
    protected ReviewServiceImpl(ReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    /** 문의사항 List페이지에 표시
     * findAll로 찾아온다. */
    @Override
    public List<Review> getReviewList(Review review){
        System.out.println("-----service getReviewList-----");
        return (List<Review>) reviewRepo.findAll();
    }
    /** 문의사항 insert */
    @Override
    public void insertReview(Review review){
        System.out.println("-----service insertReview-----");
        review.setCreateDate(new Date());
        reviewRepo.save(review);
    }
    /** 문의사항 상세 표시
     * seq번호로 get해와서 표시 */
    @Override
    public Review getReview(Review review){
        System.out.println("-----service getReview-----");
        return reviewRepo.findById(review.getSeq()).get();
    }

    /** member가 쓴 문의사항 삭제를 위한 seq검색*/
    @Override
    public Review findById(Long seq){
        return reviewRepo.findById(seq).orElse(null);
    }

    /** 문의사항 update
     * Review 타입으로 입력 돼 있는 seq를 찾아와서 수정할 제목 내용에 set을 해준다음
     * 다시 reviewRepo에 save한다 */
    @Override
    public void updateReview(Review review){
        System.out.println("-----service updateReview-----");
        Review findReview = reviewRepo.findById(review.getSeq()).get();
        findReview.setTitle(review.getTitle());
        findReview.setContent(review.getContent());
        reviewRepo.save(findReview);
    }

    /** 문의사항 삭제
     * seq를 찾아온 다음에 deleteById를 한다 */
    @Override
    public void removeReview(Review review){
        System.out.println("-----service deleteReview------");
        reviewRepo.deleteById(review.getSeq());
    }

//    @Override
//    public List<Review> searchReviewAll(String keyword){
//        System.out.println("-----service findReview-----");
//        return reviewRepo.findByTitleLikeAndWriterLikeAndContentLike(keyword);
//    }

    /** 문의사항 검색기능
     * 검색할 카테고리를 선택하지 않았을때 제목&작성자&내용을 입력받은 keyword로 검색해 준다.*/
    @Override
    public Page<Review> findAll(Pageable pageable, String keyword){
        return reviewRepo.findByTitleLikeAndWriterLikeAndContentLike(pageable, keyword);
    }

    /** 문의사항 검색기능
     * 카테고리 "제목" 선택후 입력받은 keyword로 검색
     * Containing은 like절과 같다고 생각하면 된다 */
    @Override
    public Page<Review> findByTitle(Pageable pageable, String keyword) {
        System.out.println("-----service findByTitle-----");
        return reviewRepo.findByTitleContaining(pageable, keyword);
    }

    /** 문의사항 검색기능
     * 카테고리 "내용" 선택후 입력받은 keyword로 검색*/
    @Override
    public Page<Review> findByContent(Pageable pageable, String keyword) {
        System.out.println("-----service findByContent-----");
        return reviewRepo.findByContentContaining(pageable, keyword);
    }

    /** 문의사항 검색기능
     * 카테고리 "작성자" 선택후 입력받은 keyword로 검색*/
    @Override
    public Page<Review> findByWriter(Pageable pageable, String keyword) {
        System.out.println("-----service findByWriter-----");
        return reviewRepo.findByWriterContaining(pageable, keyword);
    }

    /** 회원 아이디(자신의 아이디)로만 문의사항 삭제하기 위해서 */
    @Override
    public List<Review> findALLMemberorReview() {
        return reviewRepo.findAllByMember();
    }


}
