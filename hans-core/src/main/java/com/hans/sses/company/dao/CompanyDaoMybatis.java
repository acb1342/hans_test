package com.hans.sses.company.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 3. 29..
 */
@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface CompanyDaoMybatis {

    abstract public List<Map<String,Object>> getTree(@Param("groupId") int groupId);

    abstract public int orderUpdate(@Param("param") Map<String, Object> param);

    abstract public int orderInsert(@Param("param") Map<String, Object> param);

    abstract public int checkMenu(String id);

    abstract public Map<String, Object> get(@Param("param") Integer param);
    
    abstract public List<Map<String,Object>> getDepartmentList(@Param("groupId") int groupId);

    abstract public int deleteMenu(Integer id);
}
