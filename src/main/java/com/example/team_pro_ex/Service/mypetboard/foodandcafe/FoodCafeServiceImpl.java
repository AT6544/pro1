package com.example.team_pro_ex.Service.mypetboard.foodandcafe;


import com.example.team_pro_ex.Entity.mypetboard.common.FoodCafeImage;

import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.FoodCafe;
import com.example.team_pro_ex.exception.DataNotFoundException;
import com.example.team_pro_ex.repository.image.FoodCafeImageRepository;
import com.example.team_pro_ex.repository.mypetboard.foodCafe.FoodCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodCafeServiceImpl implements FoodCafeService  {

        private final FoodCafeRepository foodcafeRepo;

        private final FoodCafeImageRepository foodcafeImageRepo;

        @Autowired
        protected FoodCafeServiceImpl(FoodCafeRepository foodcafeRepo,FoodCafeImageRepository foodcafeImageRepo) {
            this.foodcafeRepo = foodcafeRepo;
            this.foodcafeImageRepo = foodcafeImageRepo;
        }


        @Override
        public Page<FoodCafe> getFoodCafeList(Pageable pageable) {

            return foodcafeRepo.findAll(pageable);
        }

        @Override
        public Long insertFoodCafe(FoodCafe foodcafe) {

            return foodcafeRepo.save(foodcafe).getSeq();
        }



        @Override
        public FoodCafe getFoodCafe(FoodCafe foodcafe) {

            return foodcafeRepo.findById(foodcafe.getSeq()).get();
        }

        @Override
        public void updateFoodCafe(FoodCafe foodcafe) {
            FoodCafe updateFoodCafe = foodcafeRepo.findById(foodcafe.getSeq()).get();


            updateFoodCafe.setDetailCategory(foodcafe.getDetailCategory());
            updateFoodCafe.setStoreName(foodcafe.getStoreName());
            updateFoodCafe.setInfo(foodcafe.getInfo());
            updateFoodCafe.setAddress(foodcafe.getAddress());
            updateFoodCafe.setDetailAddress(foodcafe.getDetailAddress());
            updateFoodCafe.setPostcode(foodcafe.getPostcode());
            updateFoodCafe.setPetSize(foodcafe.getPetSize());

            foodcafeRepo.save(updateFoodCafe);
        }

        @Override
        public void deleteFoodCafe(FoodCafe foodcafe) {
            foodcafeRepo.deleteById(foodcafe.getSeq());

        }

        @Override
        public Long insertFoodCafeImage(FoodCafeImage foodcafeImage) {

            return foodcafeImageRepo.save(foodcafeImage).getSeq();
        }

        @Override
        public FoodCafeImage getFoodCafeImageEntity(Long foodCafeSeq) {

            return foodcafeImageRepo.findByFoodcafeSeq(foodCafeSeq);
        }

    @Override
    public Page<FoodCafe> getFoodCafeContainsName(String keyword, Pageable pageable) {
        return foodcafeRepo.findByStoreNameContaining(keyword, pageable);
    }

    @Override
    public Page<FoodCafe> findCategoryAndKeyword(String keyword, Pageable pageable) {
        return foodcafeRepo.findCategoryAndKeyword(keyword,pageable);
    }

    @Override
    public FoodCafe getFoodCafeRequest(Long seq) {
        Optional<FoodCafe> question = foodcafeRepo.findById(seq);

        if(question.isPresent()) {
            return question.get();
        }
        else {
            throw new DataNotFoundException("not found");
        }
    }
    }
