package com.example.team_pro_ex.Service.mypetboard.hospital;


import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.common.HospitalImage;
import com.example.team_pro_ex.Entity.mypetboard.hospital.Hospital;
import com.example.team_pro_ex.repository.image.HospitalImageRepository;
import com.example.team_pro_ex.repository.mypetboard.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepo;

    private final HospitalImageRepository hospitalImageRepo;

    @Autowired
    protected HospitalServiceImpl(HospitalRepository hospitalRepo, HospitalImageRepository hospitalImageRepo) {
        this.hospitalRepo = hospitalRepo;
        this.hospitalImageRepo = hospitalImageRepo;
    }


    @Override
    public Page<Hospital> getHospitalList(Pageable pageable) {

        return hospitalRepo.findAll(pageable);
    }

    @Override
    public Long insertHospital(Hospital hospital) {

        return hospitalRepo.save(hospital).getSeq();
    }


    @Override
    public Hospital getHospital(Hospital hospital) {
        return hospitalRepo.findById(hospital.getSeq()).get();
    }

    @Override
    public void updateHospital(Hospital hospital) {

    }

    @Override
    public void deleteHospital(Hospital hospital) {
        hospitalRepo.deleteById(hospital.getSeq());
    }

    @Override
    public Long insertHospitalImage(HospitalImage hospitalImage) {

        return hospitalImageRepo.save(hospitalImage).getImageSeq();
    }

    @Override
    public HospitalImage getHospitalImageEntity(Long HospitalSeq) {

        return hospitalImageRepo.findByHospitalSeq(HospitalSeq);
    }

    @Override
    public Page<Hospital> getHospitalContainsName(String keyword, Pageable pageable) {
        return hospitalRepo.findByStoreNameContaining(keyword, pageable);
    }

    @Override
    public Page<Hospital> findCategoryAndKeyword(String searchKeyword, Pageable pageable) {
        return hospitalRepo.findCategoryAndKeyword(searchKeyword, pageable);
    }
}