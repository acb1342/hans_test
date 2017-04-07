package com.hans.sses.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface DashboardDaoMybatis {


	List<Map<String, String>> getEnergyList();

	List<Map<String, String>> getEnergyByEquipment();
}
