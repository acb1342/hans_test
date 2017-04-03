package com.mobilepark.doit5.admin.service;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface EquipmentService {

	int getCount(Map<String, Object> param);

	List<Map<String, String>> getList(Map<String, Object> param);

	void equipmentCreate(Equipment equipment);

	Equipment getDetail(String equip_seq);






	List<Admin> searchByGroup(Integer groupId);

	int searchCountByGroup(Integer groupId);

	List<Admin> searchByGroupName(String name);

	List<Admin> searchByGroupName(String groupName1, String groupName2);

	List<Admin> searchByMCPName(String mcpId);

	int searchCountByGroupName(String name);

	int searchCountByGroupName(String groupName1, String groupName2);

	List<Admin> searchRelatedCp(String mcpId);

	Admin getById(String id);
	
	Admin getMybatis(String id);
	


	
	
}
