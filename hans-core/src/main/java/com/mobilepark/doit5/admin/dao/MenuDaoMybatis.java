package com.mobilepark.doit5.admin.dao;

import com.mobilepark.doit5.admin.model.MenuFunc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.model.Menu;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface MenuDaoMybatis {

	abstract public List<Map<String, Object>> getRootMenu();

	abstract public Map<String, Object> get(@Param("param") Integer param);

	abstract public int update(@Param("param") Map<String, Object> param);

	abstract public int deleteMenu(Integer id);

	abstract public int orderUpdate(@Param("param") Map<String, Object> param);

	abstract public int orderInsert(@Param("param") Map<String, Object> param);

	abstract public int checkMenu(String id);

	abstract public int createFunction(@Param("param") Map<String, Object> param);

	abstract public List<Map<String, Object>> getFuncList(@Param("param") Integer param);

	abstract public MenuFunc getFunc(@Param("param") Integer param);

	abstract public int deleteFunction(@Param("param") Integer param);

	abstract public int updateFunction(@Param("param") Map<String, Object> param);

	abstract public MenuFunc getFuncUrl(@Param("param") String param);

}
