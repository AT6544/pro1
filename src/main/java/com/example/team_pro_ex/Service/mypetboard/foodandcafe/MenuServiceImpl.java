package com.example.team_pro_ex.Service.mypetboard.foodandcafe;

import com.example.team_pro_ex.Entity.mypetboard.common.MenuImage;
import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.Menu;
import com.example.team_pro_ex.repository.image.MenuImageRepository;
import com.example.team_pro_ex.repository.mypetboard.foodCafe.FoodCafeRepository;
import com.example.team_pro_ex.repository.mypetboard.foodCafe.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    private final FoodCafeRepository foodCafeRepository;

    private final MenuImageRepository menuImageRepository;

    private final MenuRepository menuRepository;

    public MenuServiceImpl(FoodCafeRepository foodCafeRepository, MenuImageRepository menuImageRepository, MenuRepository menuRepository) {
        this.foodCafeRepository = foodCafeRepository;
        this.menuImageRepository = menuImageRepository;
        this.menuRepository = menuRepository;
    }


    @Override
    public List<Menu> getMenuList() {
        return menuRepository.findAll();
    }

    @Override
    public Long insertMenu(Menu menu) {
        return menuRepository.save(menu).getSeq();
    }

    @Override
    public Menu getMenu(Menu menu) {
        return menuRepository.findById(menu.getSeq()).get();
    }

    @Override
    public void updateMenu(Menu menu) {

    }

    @Override
    public void deleteMenu(Menu menu) {

    }

    @Override
    public Long insertMenuImage(MenuImage menuImage) {
        return menuImageRepository.save(menuImage).getSeq();
    }

    @Override
    public List<MenuImage> getMenuImageEntity(Long menuSeq) {
        return menuImageRepository.findByFoodCafeSeq(menuSeq);
    }
}
