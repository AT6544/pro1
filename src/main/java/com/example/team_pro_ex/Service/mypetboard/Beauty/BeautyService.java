package com.example.team_pro_ex.Service.mypetboard.Beauty;

import com.example.team_pro_ex.Entity.mypetboard.beauty.Beauty;
import com.example.team_pro_ex.Entity.mypetboard.common.BeautyImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BeautyService {
    Page<Beauty> getBeautyList(Pageable pageable);

    Long insertBeauty(Beauty beauty);

    Beauty getBeauty(Beauty beauty);

    void updateBeauty(Beauty beauty);

    void deleteBeauty(Beauty beauty);

    Long insertBeautyImage(BeautyImage beautyImage);

    BeautyImage getBeautyImageEntity(Long beautySeq);

    Page<Beauty> getBeautyContainsName(String Keyword, Pageable pageable);

    Beauty getBeautyRequest(Long seq);

    List<BeautyImage> getBeautyImageList(BeautyImage BeautyImage);
}