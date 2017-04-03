package com.mobilepark.doit5.member.service;

import com.mobilepark.doit5.member.model.Equipment;

import java.util.List;
import java.util.Map;

public interface EquipmentService {

	int getCount(Map<String, Object> param);

	List<Map<String, String>> getList(Map<String, Object> param);

	void equipmentCreate(Equipment equipment);

	Equipment getDetail(String equip_seq);

	void equipmentUpdate(Equipment equipment);

	int equipmentDelete(int equip_seq);
}