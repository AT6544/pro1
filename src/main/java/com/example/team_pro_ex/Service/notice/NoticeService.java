package com.example.team_pro_ex.Service.notice;

import com.example.team_pro_ex.Entity.Data.FileUploadEntity;
import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {

//    List<NoticeNotice> NoticeList(NoticeNotice notice);
//    Page<NoticeNotice> NOTICE_PAGE(int page, String keyword);

    Long insertNotice(NoticeNotice notice);

    NoticeNotice getNotice(NoticeNotice notice);

    NoticeNotice getNotice1(Long seq);


    void updateNotice(NoticeNotice notice);

    void deleteNotice(NoticeNotice notice);


    /*  파일 업로드  */

    Long insertFileUploadEntity(FileUploadEntity fileUploadEntity);

    List<FileUploadEntity> getFileUploadEntity2(Long notice_seq);

    /*  검색  */
    Page<NoticeNotice> searchNotice(Pageable pageable, String keyword);

//    List<NoticeNotice> searchNotice(String keyword);

    int updateCnt(Long seq);

    }

