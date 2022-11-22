package com.example.team_pro_ex.Service.mypetboard.hospital;


import com.example.team_pro_ex.Entity.mypetboard.common.HospitalImage;
import com.example.team_pro_ex.Entity.mypetboard.hospital.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {
    Page<Hospital> getHospitalList(Pageable pageable);

    Long insertHospital(Hospital hospital);

    Hospital getHospital(Hospital hospital);

    void updateHospital(Hospital hospital);

    void deleteHospital(Hospital hospital);

    Long insertHospitalImage(HospitalImage hospitalImage);

    HospitalImage getHospitalImageEntity(Long HotelSeq);

    Page<Hospital> getHospitalContainsName(String Keyword, Pageable pageable);

    Page<Hospital> findCategoryAndKeyword(String Keyword, Pageable pageable);
}
