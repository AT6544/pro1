package com.example.team_pro_ex.Service.notice;

import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import com.example.team_pro_ex.Entity.notice.Reply;
import org.springframework.stereotype.Service;


@Service
public interface ReplyService {


    void insertReply(NoticeNotice notice, String r_content);

    Reply getReply(Long r_seq);

//    void updateReply(Reply reply);

    void deleteReply(Reply reply);

    void replyModify(Reply reply, String r_content);

//    Reply getReply(Long r_seq);
//
//void updateReply(Reply reply, String r_content);

}
