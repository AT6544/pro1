package com.example.team_pro_ex.repository.image;


import com.example.team_pro_ex.Entity.mypetboard.common.MenuImage;
import com.example.team_pro_ex.Entity.mypetboard.common.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage,Long> {
    List<MenuImage> findByFoodCafeSeq(Long accSeq);
}
