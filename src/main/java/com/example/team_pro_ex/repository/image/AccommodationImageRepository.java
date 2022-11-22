package com.example.team_pro_ex.repository.image;

import com.example.team_pro_ex.Entity.mypetboard.common.AccommodationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {

      AccommodationImage findByAccommodationSeq(Long accommodationSeq);
}
