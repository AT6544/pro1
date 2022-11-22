package com.example.team_pro_ex.repository.mypetboard.Accommodation;


import com.example.team_pro_ex.Entity.mypetboard.accommodation.AccommodationAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationAnswerRepository extends JpaRepository<AccommodationAnswer,Long> {
}
