package com.example.team_pro_ex.repository.hotelImage;

import com.example.team_pro_ex.Entity.mypetboard.common.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {

      HotelImage findByHotelSeq(Long hotelSeq);
}
