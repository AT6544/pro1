package com.example.team_pro_ex.Service.mypetboard.Beauty;

import com.example.team_pro_ex.Entity.mypetboard.beauty.Beauty;
import com.example.team_pro_ex.Entity.mypetboard.common.BeautyImage;
import com.example.team_pro_ex.exception.DataNotFoundException;
import com.example.team_pro_ex.repository.image.BeautyImageRepository;
import com.example.team_pro_ex.repository.mypetboard.beauty.BeautyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeautyServiceImpl implements BeautyService {

    private final BeautyRepository beautyRepo;

    private final BeautyImageRepository beautyImageRepo;

    @Autowired
    protected BeautyServiceImpl(BeautyRepository beautyRepo, BeautyImageRepository beautyImageRepo) {
        this.beautyImageRepo = beautyImageRepo;
        this.beautyRepo = beautyRepo;
    }

    @Override
    public Page<Beauty> getBeautyList(Pageable pageable) {
        return beautyRepo.findAll(pageable);
    }

    @Override
    public Long insertBeauty(Beauty beauty) {
        return beautyRepo.save(beauty).getSeq();
    }

    @Override
    public Beauty getBeauty(Beauty beauty) {
        return beautyRepo.findById(beauty.getSeq()).get();
    }

    @Override
    public void updateBeauty(Beauty beauty) {
        Beauty updatebeauty = beautyRepo.findById(beauty.getSeq()).get();

        updatebeauty.setStoreName(beauty.getStoreName());
        updatebeauty.setAddress(beauty.getAddress());
        updatebeauty.setDetailAddress(beauty.getDetailAddress());
        updatebeauty.setPostcode(beauty.getPostcode());
        updatebeauty.setPetSize(beauty.getPetSize());
        updatebeauty.setInfo(beauty.getInfo());

        beautyRepo.save(updatebeauty);
    }
    @Override
    public void deleteBeauty(Beauty beauty) {
        beautyRepo.deleteById(beauty.getSeq());
    }

    @Override
    public Long insertBeautyImage(BeautyImage beautyImage) {
        return beautyImageRepo.save(beautyImage).getSeq();
    }

    @Override
    public BeautyImage getBeautyImageEntity(Long beautySeq) {
        return beautyImageRepo.findByBeautySeq(beautySeq);
    }

    @Override
    public Page<Beauty> getBeautyContainsName(String keyword, Pageable pageable) {
        return beautyRepo.findByStoreNameContaining(keyword, pageable);
    }

    @Override
    public Beauty getBeautyRequest(Long seq) {
        Optional<Beauty> question = beautyRepo.findById(seq);
        if(question.isPresent()) {
            return question.get();
        }else {
            throw new DataNotFoundException("not found");
        }
    }

    @Override
    public List<BeautyImage> getBeautyImageList(BeautyImage BeautyImage) {
        return beautyImageRepo.findAll();
    }
}