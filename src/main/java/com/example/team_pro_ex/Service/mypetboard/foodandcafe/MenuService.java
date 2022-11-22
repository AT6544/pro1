package com.example.team_pro_ex.Service.mypetboard.foodandcafe;


import com.example.team_pro_ex.Entity.mypetboard.common.MenuImage;
import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuList();

    Long insertMenu(Menu menu);

    Menu getMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Menu menu);

    Long insertMenuImage(MenuImage menuImage);

    List<MenuImage>getMenuImageEntity(Long MenuSeq);
    
}
