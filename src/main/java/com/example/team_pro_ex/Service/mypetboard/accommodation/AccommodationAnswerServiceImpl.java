package com.example.team_pro_ex.Service.mypetboard.accommodation;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.AccommodationAnswer;
import com.example.team_pro_ex.repository.mypetboard.Accommodation.AccommodationAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccommodationAnswerServiceImpl implements AccommodationAnswerService{

    private final AccommodationAnswerRepository accommodationAnswerRepo;

    @Autowired
    public AccommodationAnswerServiceImpl(AccommodationAnswerRepository accommodationAnswerRepo) {
        this.accommodationAnswerRepo = accommodationAnswerRepo;
    }

    @Override
    public void insertAnswer(Accommodation accommodation, String content) {
        AccommodationAnswer accommodationAnswer = new AccommodationAnswer();
        accommodationAnswer.setContent(content);
        accommodationAnswer.setCreateDate(new Date());
        accommodationAnswer.setAccommodation(accommodation);

        accommodationAnswerRepo.save(accommodationAnswer);

    }
}
