package com.example.team_pro_ex.persistence.member;

import com.example.team_pro_ex.Entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//DAO
public interface MemberRepository extends JpaRepository<Member, Long> {
    //아이디 찾기? = 아이디가 완전히 일치해야 됨 = like로 해결가능
    @Query(value = "select m from Member m where m.id = :id")
    Member findById(@Param("id") String id);

    //아이디 찾기
    List<Member> findByIdContains(String id);


    //고객의 데이터 정보는 돈이다!? 라는게 있어서 고객의 ID, 이름, 가입상태(Y=>N으로 변경)을 제외한 모든
    //데이터를 Null값으로 변경
    //날짜 테이블에 있는 가입날짜, 탈퇴날짜 수정은 차후에 하기로 한다.
    @Query(value = "update MEMBER m set m.member_password = null, m.member_address = null," +
            " m.member_name = null , m.member_year = null, m.member_phone_number = null, m.member_pet_D = null, " +
            "m.member_pet_T =  null , m.member_pet_W = null, m.member_join_M = 'N', m.join_D = null, m.join_O = to_date(SYSDATE, 'yyyy.mm.dd') where m.memberNumberSeq = :memberNumberSeq", nativeQuery = true)
    Member updateDelete(Long memberNumberSeq);

    //Member_info_Update , join_O, join_D





}
