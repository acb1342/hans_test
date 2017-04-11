package com.hans.sses.energy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 4. 10..
 */
@Repository
@Transactional(value="dataSourceTransactionManager")
public interface SimulationDaoMybatis {
    List<Map<String, Object>> getEnergyList(@Param("param") Map<String,Object> param);
}
