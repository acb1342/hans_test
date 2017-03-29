package com.mobilepark.doit5.company.dao;

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

    abstract public List<Map<String,Object>> getTree();

    abstract public int orderUpdate(@Param("param") Map<String, Object> param);

    abstract public int orderInsert(@Param("param") Map<String, Object> param);

    abstract public int checkMenu(String id);
}
