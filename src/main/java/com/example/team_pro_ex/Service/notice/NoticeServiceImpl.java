package com.example.team_pro_ex.Service.notice;

import com.example.team_pro_ex.Entity.Data.FileUploadEntity;
import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import com.example.team_pro_ex.Entity.notice.Reply;
import com.example.team_pro_ex.exception.DataNotFoundException;
import com.example.team_pro_ex.repository.notice.FileRepository;
import com.example.team_pro_ex.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeRepository noticeRepo;
    private FileRepository fileRepo;

    @Autowired
    protected NoticeServiceImpl(NoticeRepository noticeRepo, FileRepository fileRepo){
        this.noticeRepo = noticeRepo;
        this.fileRepo = fileRepo;
    }

//    @Override
//    public List<NoticeNotice> NoticeList(NoticeNotice notice) {
//        return (List<NoticeNotice>) noticeRepo.findAll();
//    }

//    @Override
//    public Page<NoticeNotice> NOTICE_PAGE(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        return this.noticeRepo.findAll(pageable);
//    }

    @Override
    public Long insertNotice(NoticeNotice notice) {
        Reply reply = new Reply();
        reply.setNotice(notice);
        return noticeRepo.save(notice).getSeq();
    }

    @Override
    public NoticeNotice getNotice(NoticeNotice notice) {

        return noticeRepo.findById(notice.getSeq()).get();
    }

    @Override
    public NoticeNotice getNotice1(Long seq) {
        Optional<NoticeNotice> notice = noticeRepo.findById(seq);
        if(notice.isPresent()) {
            return notice.get();
        }
        else {
            throw new DataNotFoundException("not found");
        }
    }

    @Override
    public void updateNotice(NoticeNotice notice) {
        NoticeNotice findNotice = noticeRepo.findById(notice.getSeq()).get();
        findNotice.setTitle(notice.getTitle());
        findNotice.setContent(notice.getContent());
        noticeRepo.save(findNotice);
    }

    @Override
    public void deleteNotice(NoticeNotice notice) {
        noticeRepo.deleteById(notice.getSeq());
    }

    @Override
    public Long insertFileUploadEntity(FileUploadEntity fileUploadEntity) {
        return fileRepo.save(fileUploadEntity).getId();
    }

    @Override
    public List<FileUploadEntity> getFileUploadEntity2(Long notice_seq) {
        return fileRepo.findByNoticeSeq(notice_seq);
    }

    @Override
    public Page<NoticeNotice> searchNotice(Pageable pageable, String keyword){
        return noticeRepo.findByTitleLikeAndWriterLikeAndContentLike(pageable, keyword);
    }

    @Transactional
    public int updateCnt(Long seq) {
        return noticeRepo.updateCnt(seq);
    }

//    @Override
//    public void cReply(long notice_seq, Reply reply) {
//        Notice notice=noticeRepo.findById(notice_seq).orElseThrow(() -> {
//            return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다.");
//        });
//
//        reply.setNotice((notice));
//        replRepo.save(reply);
//    }
}
