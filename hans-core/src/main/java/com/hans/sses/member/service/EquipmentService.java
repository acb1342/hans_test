package com.hans.sses.member.service;

import com.hans.sses.member.model.Equipment;

import java.util.List;
import java.util.Map;

public interface EquipmentService {

	int getCount(Map<String, Object> param);

	List<Map<String, String>> getList(Map<String, Object> param);

	void equipmentCreate(Equipment equipment);

	Equipment getDetail(String macaddress);

	void equipmentUpdate(Equipment equipment);

	int equipmentDelete(String macaddress);
}