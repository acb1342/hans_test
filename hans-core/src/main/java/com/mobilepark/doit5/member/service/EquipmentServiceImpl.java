package com.mobilepark.doit5.member.service;

import com.mobilepark.doit5.member.dao.EquipmentDao;
import com.mobilepark.doit5.member.model.Equipment;
import com.mobilepark.doit5.member.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentDao equipmentDao;

	@Override
	public int getCount(Map<String, Object> param) {
		return equipmentDao.getCount(param);
	}

	@Override
	public List<Map<String, String>> getList(Map<String, Object> param) {
		return equipmentDao.getList(param);
	}

	@Override
	public void equipmentCreate(Equipment equipment) { this.equipmentDao.equipmentCreate(equipment); }

	@Override
	public Equipment getDetail(String equip_seq) {
		return this.equipmentDao.getDetail(equip_seq);
	}

	@Override
	public void equipmentUpdate(Equipment equipment) { this.equipmentDao.equipmentUpdate(equipment); }

	@Override
	public int equipmentDelete(int equip_seq) {
		return equipmentDao.equipmentDelete(equip_seq);
	}
}