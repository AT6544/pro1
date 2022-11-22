package com.example.team_pro_ex.repository.review;

import com.example.team_pro_ex.Entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    @Query(value = "select r from Review r where r.writer like %?1% or r.title like %?1% or r.content like %?1%")
//    List<Review> findByTitleLikeAndWriterLikeAndContentLike(String keyword);
    /**제목, 내용, 작성자 3개의 카테고리에서 입력한 keyword로 검색하는 Query문 page처리까지*/
    @Query(value = "select r from Review r where r.writer like %?1% or r.title like %?1% or r.content like %?1%")
    Page<Review> findByTitleLikeAndWriterLikeAndContentLike(Pageable pageable, String keyword);

    //일반 Join : join 조건을 제외하고 실제 질의하는 대상 Entity에 대한 컬럼만 SELECT
    //Fetch Join : 실제 질의하는 대상 Entity와 Fetch join이 걸려있는 Entity를 포함한 컬럼 함께 SELECT
    @Query(value = "select t from Review t join fetch t.member")
    List<Review> findAllByMember();





    //0929
    Page<Review> findByTitleContaining(Pageable pageable, String keyword);

    Page<Review> findByContentContaining(Pageable pageable, String keyword);

    Page<Review> findByWriterContaining(Pageable pageable, String keyword);
}