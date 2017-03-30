package com.mobilepark.doit5.admin.service;

import com.mobilepark.doit5.admin.dao.AdminDao;
import com.mobilepark.doit5.admin.dao.AdminDaoMybatisTest;
import com.mobilepark.doit5.admin.dao.EquipmentDao;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.Equipment;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
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


	@Autowired
	private AdminDao cmsUserDao;

	@Autowired
	private AdminDaoMybatisTest adminDaoMybatisTest;

	@Override
	public int getCount(Map<String, Object> param) {
		return equipmentDao.getCount(param);
	}

	@Override
	public List<Map<String, String>> getList(Map<String, Object> param) {
		return equipmentDao.getList(param);
	}

	@Override
	public void equipmentCreate(Equipment equipment) {
		this.equipmentDao.equipmentCreate(equipment);
	}

	@Override
	public Equipment getDetail(String equip_seq) {
		return this.equipmentDao.getDetail(equip_seq);
	}




	@Override
	public List<Admin> searchByGroup(Integer groupId) {
		return this.cmsUserDao.searchByGroup(groupId);
	}

	@Override
	public int searchCountByGroup(Integer groupId) {
		return this.cmsUserDao.searchCountByGroup(groupId);
	}

	@Override
	public List<Admin> searchRelatedCp(String mcpId) {
		return this.cmsUserDao.searchRelatedCp(mcpId);
	}

	@Override
	public List<Admin> searchByGroupName(String name) {
		return this.cmsUserDao.searchByGroupName(name);
	}

	@Override
	public int searchCountByGroupName(String name) {
		return this.cmsUserDao.searchCountByGroupName(name);
	}

	@Override
	public List<Admin> searchByGroupName(String groupName1, String groupName2) {
		return this.cmsUserDao.searchByGroupName(groupName1, groupName2);
	}

	@Override
	public int searchCountByGroupName(String groupName1, String groupName2) {
		return this.cmsUserDao.searchCountByGroupName(groupName1, groupName2);
	}

	@Override
	public List<Admin> searchByMCPName(String mcpId) {
		return this.cmsUserDao.searchByMCPName(mcpId);
	}

	@Override
	public Admin getById(String id) {
		return this.cmsUserDao.getById(id);
	}

	@Override
	public Admin getMybatis(String id) {
		return adminDaoMybatisTest.getAdmin(id);
	}
	

	

	
	@Override
	public Map<String, Object> getMemberDetail(String id) {
		return adminDaoMybatisTest.getMemberDetail(id);
	}
	
	@Override
	public void MemberUpdate(Map<String, Object> param) {
		this.adminDaoMybatisTest.MemberUpdate(param);
	}
	
	@Override
	public void MemberPasswdUpdate(Map<String, Object> param) {
		this.adminDaoMybatisTest.MemberPasswdUpdate(param);
	}
	
	@Override
	public void MemberCreate(Map<String, Object> param) {
		this.adminDaoMybatisTest.MemberCreate(param);
	}
	
	@Override
	public int MemberDelete(String id) {
		return adminDaoMybatisTest.MemberDelete(id);
	}
	
	@Override
	public List<Map<String, Object>> selectGroup() {
		return adminDaoMybatisTest.selectGroup();
	}
	
}
