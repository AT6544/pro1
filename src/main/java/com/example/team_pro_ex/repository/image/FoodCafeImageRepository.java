package com.example.team_pro_ex.repository.image;

import com.example.team_pro_ex.Entity.mypetboard.common.FoodCafeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCafeImageRepository extends JpaRepository<FoodCafeImage, Long> {

    FoodCafeImage findByFoodcafeSeq(Long hotelSeq);
}