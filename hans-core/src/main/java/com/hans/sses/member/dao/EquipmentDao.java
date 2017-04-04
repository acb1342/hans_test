package com.hans.sses.member.dao;

import java.util.List;
import java.util.Map;

import com.hans.sses.member.model.Equipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface EquipmentDao {

	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getList(@Param("param") Map<String,Object> param);

	void equipmentCreate(@Param("equipment")Equipment equipment);

	Equipment getDetail(@Param("equip_seq")String equip_seq);

	void equipmentUpdate(@Param("equipment")Equipment equipment);

	int equipmentDelete(@Param("equip_seq") int equip_seq);
}