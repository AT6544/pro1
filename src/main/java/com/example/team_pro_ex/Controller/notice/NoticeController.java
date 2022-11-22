package com.example.team_pro_ex.Controller.notice;

import com.example.team_pro_ex.Entity.Data.FileUploadEntity;
import com.example.team_pro_ex.Entity.notice.NoticeNotice;
import com.example.team_pro_ex.Service.notice.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import com.example.team_pro_ex.Service.notice.NoticeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
    @RequestMapping(path = "/Notice")
    public class NoticeController {

    private final NoticeService noticeService;
    private final ReplyService replyService;

    @Autowired
    private NoticeController (NoticeService noticeService, ReplyService replyService) {
        this.noticeService = noticeService;
        this.replyService = replyService;
    }

    //공지사항
//    @PostMapping("/searchList")
//    public String search(@RequestParam("keyword") String keyword, Model model) {
//        model.addAttribute("keyword", noticeService.searchNotice(keyword));
//        return "/Notice/searchList";
//    }

    @GetMapping("/getNoticeList")
    public String NoticeList(
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword,
            Pageable pageable, Model model) {
        Page<NoticeNotice> noticeList = noticeService.searchNotice(pageable, keyword);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("keyword", keyword);
        return "/Notice/getNoticeList";
    }
//    @GetMapping("/getNoticeList")
//    public String NoticeList(
//            @PageableDefault(size=5, sort="seq", direction = Sort.Direction.DESC) Pageable pageable, Model model, Notice notice) {
//        List<Notice> noticeList = noticeService.NoticeList(notice);
//        Page<Notice> noticePage = noticeService.NOTICE_PAGE(pageable);
//        System.out.println("전체 페이지 개수=" + noticePage.getTotalPages());
//        System.out.println("전체 레코드 개수=" + noticePage.getTotalElements());
//        System.out.println("현재 페이지 개수=" + noticePage.getNumber());
//        System.out.println("한 페이지의 글 개수=" + noticePage.getSize());
//        System.out.println("정렬=" + noticePage.getSort());
//        System.out.println("number,size,sort=" + noticePage.getPageable());
//        model.addAttribute("noticeList",noticeList);
//        model.addAttribute("noticePage",noticePage);
//        return "/Notice/getNoticeList";

    //공지사항 쓰기
    @GetMapping("/insertNotice")
    public String insertNotice() {
        return "/Notice/insertNotice";
    }

    @PostMapping("/insertNotice")
    public String insertNotice(NoticeNotice notice, @Nullable @RequestParam("uploadfile") MultipartFile[] uploadfile) {
        notice.setCreateDate(new Date());
        try {
            //boardService.insertBoard 메서드에서는 DB에 데이터를 저장하고 저장된 board_seq를 리턴 받음
            Long notice_seq = noticeService.insertNotice(notice);
            List<FileUploadEntity> list = new ArrayList<>();
            for (MultipartFile file : uploadfile) {
                //MultipartFile로 클라이언트에서 온 데이터가
                //무결성 조건에 성립을 안하거나 메타데이터가 없거나
                // 문제가 생길 여지를 if문으로 처리
                if (!file.isEmpty()) {
                    FileUploadEntity entity = new FileUploadEntity(null,
                            UUID.randomUUID().toString(),
                            file.getContentType(),
                            file.getName(), //저장될 때의 이름
                            file.getOriginalFilename(), //원래 이름
                            notice_seq
                    );
                    //fileuploadtable에 데이터 저장
                    noticeService.insertFileUploadEntity(entity);
                    list.add(entity);
                    File newFileName = new File(entity.getUuid() + "_" + entity.getOriginalFilename());
                    //서버에 이미지 파일 업로드(저장)
                    file.transferTo(newFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/Notice/getNoticeList";
//            return "/message";
    }


    /*-----READ------*/
    //공지사항 상세
//    @GetMapping("/getNotice")
//    public String getNotice(Notice notice, Reply reply, Model model) {
//        List<FileUploadEntity> fileUploadEntity = noticeService.getFileUploadEntity2(notice.getSeq());
//        List<String> path = new ArrayList<>();
//        for (FileUploadEntity fe : fileUploadEntity) {
//            String savePath = "/Notice/image/" + fe.getUuid() + "_" + fe.getOriginalFilename();
//            path.add(savePath);
//        }
//        /* 조회수 */
//        noticeService.updateCnt(notice.getSeq());
//        model.addAttribute("reply", replyService.getReplyList(reply));
//        List<Reply> replyList = replyService.getReplyList(reply);
//        model.addAttribute("notice", noticeService.getNotice(notice));
//        model.addAttribute("imgLoading", path);
//        return "/Notice/getNotice";
//    }
    @GetMapping("/getNotice")
    public String getNotice(NoticeNotice notice, Model model) {
        List<FileUploadEntity> fileUploadEntity = noticeService.getFileUploadEntity2(notice.getSeq());
        List<String> path = new ArrayList<>();
        for (FileUploadEntity fe : fileUploadEntity) {
            String savePath = "/Notice/image/" + fe.getUuid() + "_" + fe.getOriginalFilename();
            path.add(savePath);
        }
        noticeService.updateCnt(notice.getSeq());
        model.addAttribute("notice", noticeService.getNotice(notice));
        model.addAttribute("imgLoading", path);
        return "/Notice/getNotice";

    }

    //공지사항 수정
    @PostMapping("/updateNotice")
    public String updateNotice(NoticeNotice notice) {
        noticeService.updateNotice(notice);
        return "redirect:/Notice/getNotice?seq=" + notice.getSeq();
    }

    @GetMapping("/updateNotice")
    public String updateNoticeView(NoticeNotice notice, Model model) {
        model.addAttribute("notice", noticeService.getNotice(notice));
        return "/Notice/getNotice";
    }

    //공지사항 삭제
    @GetMapping("/deleteNotice")
    public String deleteNotice(NoticeNotice notice) {
        noticeService.deleteNotice(notice);
        return "redirect:/Notice/getNoticeList";
    }

    //파일 업로드
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
                             @RequestParam("seq") Long input_seq) throws IOException {
        log.info("img load session");
        List<FileUploadEntity> list = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            if (!file.isEmpty()) {
                FileUploadEntity entity = new FileUploadEntity(null,
                        UUID.randomUUID().toString(),
                        file.getContentType(),
                        file.getName(),
                        file.getOriginalFilename(),
                        input_seq
                );
                Long output = noticeService.insertFileUploadEntity(entity);
                log.info("seq check!");
                log.info(output.toString());
                list.add(entity);
                File newFileName = new File(entity.getUuid() + "_" + entity.getOriginalFilename());
                //file을 서버에 저장하는 스트림행위는 서버가 성공할지 여부를 체크하므로 exception처리 필요
                //메서드에 throws IOException 처리 = try catch처리 필요
                file.transferTo(newFileName);
            }
        }
        return "/Notice/getNoticeList";
    }

    @GetMapping(value = "/image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> imageLoading(@PathVariable("imagename") String imgname) throws IOException {
        String path = "C:/NewFolder/PetProject01/src/main/resources/static/upload/" + imgname;
        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] imgByteArr = bis.readAllBytes();
        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
    }

    // 검색
//    @PostMapping("/searchList")
//    public String search(@RequestParam("keyword") String keyword, Model model) {
//        model.addAttribute("keyword", noticeService.searchNotice(keyword));
//        return "/Notice/searchList";
//    }
}

