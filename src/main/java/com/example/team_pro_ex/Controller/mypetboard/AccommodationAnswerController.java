package com.example.team_pro_ex.Controller.mypetboard;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Service.mypetboard.accommodation.AccommodationAnswerService;
import com.example.team_pro_ex.Service.mypetboard.accommodation.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AccommodationAnswerController {
    private final AccommodationService accommodationService;
    private final AccommodationAnswerService accommodationAnswerService;

    @Autowired

    public AccommodationAnswerController(AccommodationService accommodationService, AccommodationAnswerService accommodationAnswerService) {
        this.accommodationService = accommodationService;
        this.accommodationAnswerService = accommodationAnswerService;
    }

    @PostMapping("/insertAnswer/{seq}")
    public String insertAnswer(Model model , @PathVariable("seq") Long seq , @RequestParam String content) {
        Accommodation accommodation = accommodationService.getAccommodationAnswer(seq);
        accommodationAnswerService.insertAnswer(accommodation,content);

        return String.format("redirect:/mypetboard/accommodation/getAccommodation/%s" , seq);
    }
}
