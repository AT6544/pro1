package com.example.team_pro_ex.Service.mypetboard.accommodation;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.common.AccommodationImage;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationService {

    Page<Accommodation> getAccommodationList(Pageable pageable);

    Long insertAccommodation(Accommodation accommodation);

    Accommodation getAccommodation(Accommodation accommodation);

    Accommodation getAccommodationAnswer(Long seq);

    void updateAccommodation(Accommodation accommodation);

    void deleteAccommodation(Accommodation accommodation);

    Long insertAccommodationImage(AccommodationImage accommodationImage);

    AccommodationImage getAccommodationImageEntity(Long accommodationSeq);


    Page<Accommodation> getAccommodationContainsName(String Keyword, Pageable pageable);


    Page<Accommodation> findCategoryAndKeyword(String Keyword, Pageable pageable);


    Accommodation getAccommodationRequest(Long seq);

    List<AccommodationImage> getAccommodationImageList(AccommodationImage accommodationImage);
}
