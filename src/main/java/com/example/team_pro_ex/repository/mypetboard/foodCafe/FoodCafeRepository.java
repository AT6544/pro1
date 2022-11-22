package com.example.team_pro_ex.repository.mypetboard.foodCafe;



import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.FoodCafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCafeRepository extends JpaRepository<FoodCafe, Long> {
    /**가게 이름으로 검색*/
    Page<FoodCafe> findByStoreNameContaining(String name, Pageable pageable);

    /**카테고리 선택 후 입력받은 가게 이름으로 검색*/
    @Query("SELECT f FROM FoodCafe f WHERE f.storeName LIKE %?1%")
    Page<FoodCafe> findCategoryAndKeyword(String searchKeyword, Pageable pageable);


}
