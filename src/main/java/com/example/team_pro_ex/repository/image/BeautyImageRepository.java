package com.example.team_pro_ex.repository.image;

import com.example.team_pro_ex.Entity.mypetboard.common.BeautyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyImageRepository extends JpaRepository<BeautyImage, Long> {

    BeautyImage findByBeautySeq(Long beautySeq);
}
