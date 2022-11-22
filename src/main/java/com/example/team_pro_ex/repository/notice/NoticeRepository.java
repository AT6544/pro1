package com.example.team_pro_ex.repository.notice;

import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeNotice, Long> {

//  Page<NoticeNotice> findAll(Pageable pageable, String keyword);

  //검색
//  @Query(value = "SELECT n FROM NoticeNotice n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword% OR n.writer LIKE %:keyword%")
//  List<NoticeNotice> findAllSearch(String keyword);

//    @Query(value = "SELECT n FROM NoticeNotice n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword% OR n.writer LIKE %:keyword%")
//  List<NoticeNotice> findAllSearch(String keyword);

//  @Query(value = "SELECT n FROM NoticeNotice n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword% OR n.writer LIKE %:keyword%")
//  List<NoticeNotice> findAllSearch(String keyword);

  @Query(value = "select r from NoticeNotice r where r.writer like %:keyword% or r.title like %:keyword% or r.content like %:keyword%")
  Page<NoticeNotice> findByTitleLikeAndWriterLikeAndContentLike(Pageable pageable, String keyword);


//  @Query(value = "SELECT n from Notice n where n.category like %n.title% and %:keyword%" )
//  조회수 기능
  @Modifying
  @Query("UPDATE NoticeNotice n set n.cnt = n.cnt + 1 WHERE n.seq = :seq")
  int updateCnt(long seq);

}
