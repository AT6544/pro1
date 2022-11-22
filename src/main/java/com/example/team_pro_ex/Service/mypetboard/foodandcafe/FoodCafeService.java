package com.example.team_pro_ex.Service.mypetboard.foodandcafe;


import com.example.team_pro_ex.Entity.mypetboard.common.FoodCafeImage;

import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.FoodCafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodCafeService {
    Page<FoodCafe> getFoodCafeList(Pageable pageable);

    Long insertFoodCafe(FoodCafe foodcafe);

    FoodCafe getFoodCafe(FoodCafe foodcafe);

    void updateFoodCafe(FoodCafe foodcafe);

    void deleteFoodCafe(FoodCafe foodcafe);

    Long insertFoodCafeImage(FoodCafeImage foodcafeImage);

    FoodCafeImage getFoodCafeImageEntity(Long foodCafeSeq);


    Page<FoodCafe> getFoodCafeContainsName(String keyword , Pageable pageable);

    Page<FoodCafe> findCategoryAndKeyword(String keyword, Pageable pageable);

    FoodCafe getFoodCafeRequest(Long seq);
}