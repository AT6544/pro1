package com.example.team_pro_ex.repository.notice;

import com.example.team_pro_ex.Entity.Data.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileUploadEntity, Long> {
    List<FileUploadEntity> findByNoticeSeq(Long noticeSeq);
}
