package com.example.team_pro_ex.repository.image;

import com.example.team_pro_ex.Entity.mypetboard.common.HospitalImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalImageRepository extends JpaRepository<HospitalImage, Long> {

    HospitalImage findByHospitalSeq(Long hotelSeq);
}