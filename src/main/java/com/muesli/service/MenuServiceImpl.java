package com.muesli.service;

import com.muesli.dao.MemberDAO;
import com.muesli.dao.MenuDAO;
import com.muesli.domain.MenuBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

	@Inject
	MenuDAO menuDAO;

	@Override
	public MenuBean getMenu(int men_id) {
		System.out.println("MenuServiceImpl - getMenu()");
		return menuDAO.getMenu(men_id);
	}

	@Override
	public List<MenuBean> getMenuList() {
		System.out.println("MenuServiceImpl - getMenuList()");
		return menuDAO.getMenuList();
	}

	@Override
	public int updateMenu(MenuBean menuBean) {
		System.out.println("MenuServiceImpl - updateMenu()");
		return menuDAO.updateMenu(menuBean);
	}

	@Override
	public int getMenuGroupCount() {
		System.out.println("MenuServiceImpl - getMenuGroupCount()");
		return menuDAO.getMenuGroupCount();
	}

	@Override
	public int getMenuCount(String men_parent) {
		System.out.println("MenuServiceImpl - getMenuCount()");
		return menuDAO.getMenuCount(men_parent);
	}

	@Override
	public int updateOrder(Map<String, Object> menuMap) {
		System.out.println("MenuServiceImpl - updateOrder()");
		return menuDAO.updateOrder(menuMap);
	}

	@Override
	public int updateOtherOrder(Map<String, Object> menuMap) {
		System.out.println("MenuServiceImpl - updateOtherOrder()");
		return menuDAO.updateOtherOrder(menuMap);
	}

	@Override
	public int insertMenu(MenuBean menuBean) {
		System.out.println("MenuServiceImpl - insertMenu()");
		return menuDAO.insertMenu(menuBean);
	}

	@Override
	public int deleteMenu(int men_id) {
		System.out.println("MenuServiceImpl - deleteMenu()");
		return menuDAO.deleteMenu(men_id);
	}

	@Override
	public void pushMenuOrder(MenuBean menuBean) {
		System.out.println("MenuServiceImpl - pushMenuOrder()");
		menuDAO.pushMenuOrder(menuBean);
	}
}
