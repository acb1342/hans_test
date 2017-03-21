package com.mobilepark.doit5.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface AdminGroupDaoMybatis {
	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);
}
