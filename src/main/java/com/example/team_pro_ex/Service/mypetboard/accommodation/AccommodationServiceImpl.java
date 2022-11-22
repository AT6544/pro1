package com.example.team_pro_ex.Service.mypetboard.accommodation;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.common.AccommodationImage;
import com.example.team_pro_ex.exception.DataNotFoundException;
import com.example.team_pro_ex.repository.image.AccommodationImageRepository;

import com.example.team_pro_ex.repository.mypetboard.Accommodation.RoomRepository;
import com.example.team_pro_ex.repository.mypetboard.Accommodation.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepo;
    private final AccommodationImageRepository accommodationImageRepo;

    private final RoomRepository accommodationRoomRepo;

    /* 생성자 주입
     숙소, 숙소이미지, 룸 Repository 정보를 사용하기 위해*/
    @Autowired
    protected AccommodationServiceImpl(AccommodationRepository accommodationRepo, AccommodationImageRepository accommodationImageRepo, RoomRepository accommodationRoomRepo) {
        this.accommodationRepo = accommodationRepo;
        this.accommodationImageRepo = accommodationImageRepo;
        this.accommodationRoomRepo = accommodationRoomRepo;
    }

    /** 숙소 List
     * findAll로 찾아오기 */
    @Override
    public Page<Accommodation> getAccommodationList(Pageable pageable) {

        return accommodationRepo.findAll(pageable);
    }

    /** 숙소 정보 입력 */
    @Override
    public Long insertAccommodation(Accommodation accommodation) {

       return accommodationRepo.save(accommodation).getSeq();
    }

    /** 숙소 상세정보 page */
    @Override
    public Accommodation getAccommodation(Accommodation accommodation) {

        return accommodationRepo.findById(accommodation.getSeq()).get();
    }

    /** 숙소 댓글후기를 위한 정보(구현안됨) */
    @Override
    public Accommodation getAccommodationAnswer(Long seq) {
        Optional<Accommodation> accommodation = accommodationRepo.findById(seq);
        if(accommodation.isPresent()) {
            return accommodation.get();
        }
        else {
            throw new DataNotFoundException("Accommodation not found");
        }

    }
    /** 숙소정보 수정 */
    @Override
    public void updateAccommodation(Accommodation accommodation) {
        Accommodation updateAccommodation = accommodationRepo.findById(accommodation.getSeq()).get();

        updateAccommodation.setDetailCategory(accommodation.getDetailCategory());
        updateAccommodation.setStoreName(accommodation.getStoreName());
        updateAccommodation.setInfo(accommodation.getInfo());
        updateAccommodation.setAddress(accommodation.getAddress());
        updateAccommodation.setDetailAddress(accommodation.getDetailAddress());
        updateAccommodation.setPostcode(accommodation.getPostcode());
        updateAccommodation.setPetSize(accommodation.getPetSize());

        accommodationRepo.save(updateAccommodation);
    }

    /** 숙소 정보 삭제*/
    @Override
    public void deleteAccommodation(Accommodation accommodation) {
        accommodationRepo.deleteById(accommodation.getSeq());
    }

    /** 숙소 대표 사진 등록*/
    @Override
    public Long insertAccommodationImage(AccommodationImage accommodationImage) {

        return accommodationImageRepo.save(accommodationImage).getSeq();
    }

    /** 저장한 숙소 대표 사진 불러오기 */
    @Override
    public AccommodationImage getAccommodationImageEntity(Long accommodationSeq) {

        return accommodationImageRepo.findByAccommodationSeq(accommodationSeq);
    }

    /** 숙소 이름으로 검색 기능 */
    @Override
    public Page<Accommodation> getAccommodationContainsName(String keyword, Pageable pageable) {
        return accommodationRepo.findByStoreNameContaining(keyword, pageable);
    }

    /** 숙소 카테고리(모텔, 호텔, 펜션) 선택 후 이름으로 검색 기능 */
    @Override
    public Page<Accommodation> findCategoryAndKeyword(String searchKeyword, Pageable pageable) {
        return accommodationRepo.findCategoryAndKeyword(searchKeyword, pageable);
    }

    /** 룸 사진 등록을 위한 숙소seq 확인 과정 */
    @Override
    public Accommodation getAccommodationRequest(Long seq) {

        Optional<Accommodation> question = accommodationRepo.findById(seq);

        if(question.isPresent()) {
            return question.get();
        }
        else {
            throw new DataNotFoundException("not found");
            }
        }

        /** 숙소 사진을 List에 띄움*/
    @Override
    public List<AccommodationImage> getAccommodationImageList(AccommodationImage accommodationImage) {

        return accommodationImageRepo.findAll();
    }


}


//    @Override
//    public Page<Accommodation> getAccommodationCategoryLikeAndStoreNameContaining(String detailCategory, String keyword, Pageable pageable) {
//        return accommodationRepo.findByDetailCategoryLikeAndStoreNameContaining(detailCategory,keyword,pageable);
//    }



