package com.muesli.service;


import com.muesli.domain.MenuBean;

import java.util.List;
import java.util.Map;

public interface MenuService {

    List<MenuBean> getMenuList();

    MenuBean getMenu(int men_id);

    int updateMenu(MenuBean menuBean);

    int getMenuGroupCount();

    int getMenuCount(String men_parent);

    int updateOrder(Map<String, Object> menuMap);

    int updateOtherOrder(Map<String, Object> menuMap);

    int insertMenu(MenuBean menuBean);

    int deleteMenu(int men_id);

    void pushMenuOrder(MenuBean menuBean);
}
