package com.example.team_pro_ex.Controller.notice;

import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import com.example.team_pro_ex.Entity.notice.Reply;
import com.example.team_pro_ex.Service.notice.NoticeService;
import com.example.team_pro_ex.Service.notice.ReplyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Setter
@Getter
public class ReplyController {

    //    @Autowired
    private final NoticeService noticeService;
    private final ReplyService replyService;


    //댓글 등록
    @PostMapping("/Reply/insertReply/{seq}")
    public String insertReply(Model model, @PathVariable("seq") Long seq, @RequestParam String r_content) {
        NoticeNotice notice = this.noticeService.getNotice1(seq);
        this.replyService.insertReply(notice, r_content);
        return String.format("redirect:/Notice/getNotice?seq=" + seq);
    }

    //댓글 수정
    @PostMapping("/Reply/modify/{r_seq}")
    public String replyModify(Reply reply, @PathVariable("r_seq") Long r_seq) {
        Reply reply1 = this.replyService.getReply(r_seq);
        this.replyService.replyModify(reply1, reply.getR_content());
        return String.format("redirect:/Notice/getNotice?seq=" + reply.getNotice().getSeq());
    }

    @GetMapping("/Reply/modify/{r_seq}")
    public String replyModify(Reply reply, Model model) {
        model.addAttribute("reply", replyService.getReply(reply.getR_seq()));
        System.out.println("GET댓글");
        return "Reply/replyForm";
    }

        //댓글 삭제
        @GetMapping("/deleteReply/{r_seq}")
        public String deleteReply(Reply reply, @PathVariable("r_seq")Long r_seq ) {
        reply= this.replyService.getReply(r_seq);
        this.replyService.deleteReply(reply);
            return String.format("redirect:/Notice/getNotice?seq=%s", reply.getNotice().getSeq());
        }
    }



