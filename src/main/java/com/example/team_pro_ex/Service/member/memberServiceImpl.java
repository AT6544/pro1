package com.example.team_pro_ex.Service.member;

import com.example.team_pro_ex.Entity.member.Member;
import com.example.team_pro_ex.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.net.PasswordAuthentication;
import java.util.*;

@Service
@RequiredArgsConstructor
public class memberServiceImpl implements memberService{

    //persistence.account_info => MemberRepository에 있는 CrudRepository<Member, Long> 사용
    private final MemberRepository memberRepo;

    private final PasswordEncoder bCryptPasswordEncoder;


    //회원 전체조회
    @Override
    public List<Member> getMemberList() {
        System.out.println("--------회원목록---------");
        return (List<Member>) memberRepo.findAll();
    }

    @Override
    public List<Member> getMemberListEncodingByMemberList(List<Member> memberList) {
        return memberList;
    }

    //myPage => 회원정보 수정?
    @Override
    public Member getMember(Member member) {
        // 특정회원을 검색하여 리턴하고, 만약 검색 결과에 없으면 null을 리턴한다.
        Optional<Member> findMember = memberRepo.findById(member.getMemberNumberSeq());
        if(findMember.isPresent())
            return memberRepo.findById(member.getMemberNumberSeq()).get();
        else return null;
    }
    //회원정보 업데이트
    @Override
    @Transactional
    public Member updateMember(Member member) {
        //find로 영속화 하고
        Member findMember = memberRepo.findById(member.getMemberNumberSeq()).get();
        System.out.println("서비스 시퀀스 넘버 : "+member.getMemberNumberSeq());

        //바꾸는 값
        findMember.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        findMember.setPhoneNumber(member.getPhoneNumber());
        findMember.setAddress(member.getAddress());
        findMember.setPetT(member.getPetT());
        findMember.setPetD(member.getPetD());
        findMember.setPetW(member.getPetW());


        System.out.println("--------회원정보 수정---------");
        System.out.println(findMember.getPassword());
        System.out.println(findMember.getPhoneNumber());
        System.out.println(findMember.getAddress());
        System.out.println(findMember.getPetT());
        System.out.println(findMember.getPetD());
        System.out.println(findMember.getPetW());
        System.out.println("--------회원정보 수정---------");

        return memberRepo.save(findMember);
    }

    //고객의 데이터 정보는 돈이다!? 라는게 있어서 고객의 ID, 이름, 가입상태(Y=>N으로 변경)을 제외한 모든
    //데이터를 Null값으로 변경
    //날짜 테이블에 있는 가입날짜, 탈퇴날짜 수정은 차후에 하기로 한다.
    @Override
    @Transactional
    public void deleteUpdateMember(Member member) {
        System.out.println("--------회원탈퇴---------");

        Member findMember = getMembers(member);
        findMember.setPetW(null);
        findMember.setPetT(null);
        findMember.setPhoneNumber(null);
        findMember.setAddress(null);
        findMember.setPetD(null);
        findMember.setAddress(null);
        findMember.setJoin_O(new Date());
        findMember.setMember_info_Update(new Date());
        findMember.setName(null);
        findMember.setYear(null);
        findMember.setJoin_D(null);
        findMember.setPassword(null);
        memberRepo.save(findMember);
    }


    //회원가입 시, 유효성 체크
    @Override
    public Map<String, String> member_Availability(Errors errors) {
        //유효성 검사에 실패한 필드들은 Map 자료구조를 통해 키값과 에러 메시지를 응답한다.
        //Key : valid_{dto 필드명}
        //Message : dto에서 작성한 message 값
        //유효성 검사에 실패한 필드 목록을 받아 미리 정의된 메시지를 가져와 Map에 넣어준다.
        Map<String, String> availability_ID = new HashMap<>();
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        // errors.getFieldErrors() : 유효성 검사에 실패한 필드 목록을 가져옴
        for(FieldError error : errors.getFieldErrors()){
            //유효성 검사에 실패한 필드명을 가져옵니다. : error.getField() / 키 : "members_%s"  = > mevers_dto필드명
            String member_availability_ID = String.format("valid_%s", error.getField());
            //error.getDefaultMessage() : 유효성 검사에 실패한 필드에 정의된 메시지를 가져옵니다.
            availability_ID.put(member_availability_ID, error.getDefaultMessage());
        }
        return availability_ID;
    }


    //회원가입 및 중복체크
    @Override
    public void insertMember(Member member) {
        idCheck(member);
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepo.save(member);
    }

    @Override
    public void idCheck(Member member) {
        Member findMember = memberRepo.findById(member.getId());
        if(findMember != null){
            throw  new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public Member getMembers(Member member) {
        return memberRepo.findById(member.getMemberNumberSeq()).get();

    }


    // 아이디 찾기
    @Override
    public boolean booleanSearchUserById(Member member) {
        for(Member findMember : memberRepo.findByIdContains(member.getId())) {
            System.out.println("아이디 : " + findMember.getId());
        }
        return false;
    }

    @Override
    public Member getMemberWhereId(String id) {
        return memberRepo.findById(id);
    }






}
