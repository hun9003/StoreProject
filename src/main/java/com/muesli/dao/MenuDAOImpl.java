package com.muesli.dao;

import com.muesli.domain.MenuBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Repository
public class MenuDAOImpl implements MenuDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "com.muesli.mapper.MenuMapper";

	@Override
	public List<MenuBean> getMenuList() {
		System.out.println("MenuDAOImpl - getMenuList()");
		return sqlSession.selectList(namespace+".getMenuList");
	}

	@Override
	public MenuBean getMenu(int men_id) {
		System.out.println("MenuDAOImpl - getMenu()");
		return sqlSession.selectOne(namespace+".getMenu", men_id);
	}

	@Override
	public int updateMenu(MenuBean menuBean) {
		System.out.println("MenuDAOImpl - updateMenu()");
		return sqlSession.update(namespace+".updateMenu", menuBean);
	}

	@Override
	public int getMenuGroupCount() {
		System.out.println("MenuDAOImpl - getMenuGroupCount()");
		return sqlSession.selectOne(namespace+".getMenuGroupCount");
	}

	@Override
	public int getMenuCount(String men_parent) {
		System.out.println("MenuDAOImpl - getMenuCount()");
		return sqlSession.selectOne(namespace+".getMenuCount", men_parent);
	}

	@Override
	public int updateOrder(Map<String, Object> menuMap) {
		System.out.println("MenuDAOImpl - updateOrder()");
		return sqlSession.update(namespace+".updateOrder", menuMap);
	}

	@Override
	public int updateOtherOrder(Map<String, Object> menuMap) {
		System.out.println("MenuDAOImpl - updateOtherOrder()");
		return sqlSession.update(namespace+".updateOtherOrder", menuMap);
	}

	@Override
	public int insertMenu(MenuBean menuBean) {
		System.out.println("MenuDAOImpl - insertMenu()");
		return sqlSession.insert(namespace+".insertMenu", menuBean);
	}

	@Override
	public int deleteMenu(int men_id) {
		System.out.println("MenuDAOImpl - deleteMenu()");
		return sqlSession.delete(namespace+".deleteMenu", men_id);
	}

	@Override
	public void pushMenuOrder(MenuBean menuBean) {
		System.out.println("MenuDAOImpl - pushMenuOrder()");
		sqlSession.update(namespace+".pushMenuOrder", menuBean);
	}
}
