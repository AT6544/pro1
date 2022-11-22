package com.example.team_pro_ex.Controller.member;

import com.example.team_pro_ex.Security.Member.PrincipaDetailsMember;
import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.Service.member.memberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Map;

@Controller
// 세션에 상태 정보를 저장할 때 사용하는데, @SessionAttributes 뒤에 ("member") 라고 설정했기 때문에
// "member" 라는 이름으로 저장된 데이터를 자동으로 세션으로 등록
//@RequestMapping(path = "/Member")
public class memberController {

    private final memberService memberService;

    @Autowired
    protected memberController(memberService memberservice){
        this.memberService = memberservice;
    }


    @GetMapping("/Member/index")
    public void index(){

    }

    @GetMapping("/Member/memberList/members")
    public String membrerList(Model model){
        model.addAttribute("member", memberService.getMemberListEncodingByMemberList(
                memberService.getMemberList()));
        return  "/Member/memberList/members";
    }
    //일반 회원가입
    @GetMapping("/Member/mJoin/Join")
    public String insertMember(Model model){
        System.out.println("get mapping account !!");
        System.out.println("get방식으로 인한 Join페이지 = 우리가 처음 join페이지를 들어갈 떄는 null값이 뜰 수 밖에없다.");
        System.out.println("왜냐!!?!? 값이 없으니까!");
        model.addAttribute(new Member());
        return "Member/mJoin/Join";
    }
    @PostMapping("/Member/mJoin/Join")
    public String insertMember(@Valid Member member, Errors errors, Model model){
        System.out.println("---check---");
        System.out.println("---PostMapping 실제로 여기서 값이 들어감---");
        System.out.println("아이디 : "+ member.getId());
        System.out.println("비밀번호 : "+ member.getPassword());
        System.out.println("이름 : "+ member.getName());
        System.out.println("폰번 : "+ member.getPhoneNumber());
        System.out.println("주소 : "+ member.getAddress());
        System.out.println("펫 종류 :"+ member.getPetT());
        System.out.println("펫 생년 : "+ member.getPetD());
        System.out.println("펫 몸무게 :" +member.getPetW());
        System.out.println("권한 : " + member.getRole());
        System.out.println("가입상태 : " +member.getJoinM());

        //@Valid : 클라이언트 입력 데이터가 dto클래스로 캡슐화되어 넘어올 때, 유효성을 체크하라는 어노테이션
        //Member에서 작성한 어노테이션을 기준으로 유효성 체크
        //여기서 Errors객체는 Member의 필드 유효성 검사 오류에 대한 정보를 저장하고 노출한다.
        //errors.hasErrors() : 유효성 검사에 실패한 필드가 있는지 확인
        if(errors.hasErrors()){
            //회원가입 실패 시, 입력 데이터를 유지
            model.addAttribute("member", member);
            System.out.println(member.getId());
            //회원가입 실패 시, 회원가입 페이지에서 입력했던 정보들을 그대로 유지하기 위해 입력받았던 데이터를 그대로 할당합니다.
            //insertMember(Member member) 함수에 파라미터를 정의해준 이유입니다.
            //Validation 관점에서는 필요없는 부분이지만, UX 측면에서 구현해주는 것이 좋다.
            //물론, thymeleaf에서도 코드가 들어가야 한다.

            //유효성 통과 못한 필드와 메세지를 핸들링
            Map<String, String> member_Availability = memberService.member_Availability(errors);
            for(String key : member_Availability.keySet()){
                model.addAttribute(key, member_Availability.get(key));
            }
            return "/Member/mJoin/Join";
        }
//        Member findMember = memberService.getMemberWhereId(member.getId());
//        if(findMember != null){
//            System.out.println("아이디 중복  : " + member.getId());
//        }else{
//            System.out.println("회원가입 : " + member.getId());
//
//        }
        memberService.insertMember(member);

        return "redirect:/Member/Login";
    }


    @GetMapping("/Member/mUpdate/Update") //마이 페이지 수정폼
    //@AuthenticationPrincipal PrincipaDetailsMember userDetails => 로그인한 인증 된 정보를 가져온다.
    public String myPage(@AuthenticationPrincipal PrincipaDetailsMember userDetails, Model model){
        System.out.println("get mapping account !!");
        System.out.println("get방식으로 인한 Join페이지 = 우리가 처음 join페이지를 들어갈 떄는 null값이 뜰 수 밖에없다.");
        System.out.println("왜냐!!?!? 값이 없으니까!");
        //객체로 주입하면 thyreef에서는 객체 member로 받는다.
        model.addAttribute(userDetails/**/.getMember());
        return "/Member/mUpdate/Update";
    }


    @PostMapping("/Member/mUpdate/Update") // 실제로 수정 되는 메소드
    public String updateM(@Valid Member member,
                          @AuthenticationPrincipal PrincipaDetailsMember userDetails,
                          Errors errors, Model model){
        System.out.println("---회원 정보 수정이 이루어 진다.---");
        System.out.println("---check---");
        System.out.println("---PostMapping 실제로 여기서 값이 들어감---");
        System.out.println("시퀀스 넘버 : "+ member.getMemberNumberSeq());
        System.out.println("아이디 : "+ member.getId());
        System.out.println("비밀번호 : "+ member.getPassword());
        System.out.println("이름 : "+ member.getName());
        System.out.println("폰번 : "+ member.getPhoneNumber());
        System.out.println("주소 : "+ member.getAddress());
        System.out.println("펫 종류 :"+ member.getPetT());
        System.out.println("펫 생년 : "+ member.getPetD());
        System.out.println("펫 몸무게 :" +member.getPetW());
        System.out.println("권한 : " + member.getRole());
        System.out.println("가입상태 : " +member.getJoinM());
        //@Valid : 클라이언트 입력 데이터가 dto클래스로 캡슐화되어 넘어올 때, 유효성을 체크하라는 어노테이션
        //Member에서 작성한 어노테이션을 기준으로 유효성 체크
        //여기서 Errors객체는 Member의 필드 유효성 검사 오류에 대한 정보를 저장하고 노출한다.
        //errors.hasErrors() : 유효성 검사에 실패한 필드가 있는지 확인
        if(errors.hasErrors()){
            //회원가입 실패 시, 입력 데이터를 유지
            model.addAttribute("member", member);
            System.out.println(member.getId());
            //회원가입 실패 시, 회원가입 페이지에서 입력했던 정보들을 그대로 유지하기 위해 입력받았던 데이터를 그대로 할당합니다.
            //insertMember(Member member) 함수에 파라미터를 정의해준 이유입니다.
            //Validation 관점에서는 필요없는 부분이지만, UX 측면에서 구현해주는 것이 좋다.
            //물론, thymeleaf에서도 코드가 들어가야 한다.

            //유효성 통과 못한 필드와 메세지를 핸들링
            Map<String, String> member_Availability = memberService.member_Availability(errors);
            for(String key : member_Availability.keySet()){
                model.addAttribute(key, member_Availability.get(key));
            }
            return "redirect:/Member/mUpdate/Update"; //로그안 페이지로 왜 안돌아가는지 모르겠다.
        }
        System.out.println("컨트롤라 : "+member.getId());
        //userDetails에 있는 pk를 가져온다 -> 그리고 userDetails에 있는 로그인정보도 수정
        member.setMemberNumberSeq(userDetails.getMember().getMemberNumberSeq());
        member.setPassword(userDetails.getMember().getPassword());
        Member findMember = memberService.updateMember(member);
        System.out.println("동물 타입 변경 : "+findMember.getPetT());
        userDetails.setMember(findMember);

        return "redirect:/";
    }

    @GetMapping("/Member/mUpdate/updatePassword")
    public void updatePassword(){
    }
    // DB에서는 변경이 되지만 내 화면에서는 바뀌지가 않는다.
    @PostMapping("/Member/mUpdate/updatePassword")
    public String updatePassword(@Valid Member member,
                                 @AuthenticationPrincipal PrincipaDetailsMember userDetails,
                                 Errors errors, Model model){
        if(errors.hasErrors()){
            Map<String, String> member_Availability = memberService.member_Availability(errors);
            for(String key : member_Availability.keySet()){
                model.addAttribute(key, member_Availability.get(key));
            }
            return "redirect:/Member/mUpdate/updatePassword"; //로그안 페이지로 왜 안돌아가는지 모르겠다.
        }
            Member findMember = memberService.getMembers(userDetails.getMember());
            findMember.setPassword(member.getPassword());

            memberService.updateMember(findMember);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/Member/Login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/Member/Login";
    }

    public void loginPage(){
    }




    //회원을 삭제하는게 아니라 수정한다. ID, Name, Join_m 및 날짜 테이블의 join_O을 제외한 값 전부 Null
    //model.addAttribute(userDetails.getMember());
    @PostMapping("/Member/mDelete/upDelete")
    public String deleteUpdateMember(@AuthenticationPrincipal PrincipaDetailsMember userDetails){
        System.out.println("-------delete-------");
        memberService.deleteUpdateMember(userDetails.getMember());

        return "redirect:/logout";
    }

    //아이디 찾기 = 핸드폰 번호로 찾기(from 화면만 보임)
    @GetMapping("/Member/selectMember/select")
    public String selectMember() {
        return "/Member/selectMember/select";
    }

    //핸드폰으로 아이디찾기 => 결과값을 보여준다.
    @PostMapping("/Member/selectMember/select")
    public String resultMember(Member member, Model model) {
        System.out.println("------select ID--------");
        System.out.println(memberService.booleanSearchUserById(member));
        model.addAttribute("member", memberService.getMemberWhereId(member.getId()));
        return "/Member/selectMember/result";
    }





}
