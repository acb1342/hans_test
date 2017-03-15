package com.mobilepark.doit5.elcg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerList;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElcgDaoMybatis {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public List<Object> selectBdGroupList(String searchKeyword, Long usid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("usid", usid);
		
		return (List<Object>) sqlSessionTemplate.selectList("elcg.selectBdGroupList", paramMap);
	}
	
	public List<Object> selectFavoriteBdGroupList(Long usid) {
		return (List<Object>) sqlSessionTemplate.selectList("elcg.selectFavoriteBdGroupList", usid);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBdGroupDetail(Long bdGroupId) {
		return (Map<String, Object>) sqlSessionTemplate.selectOne("elcg.selectBdGroupDetail", bdGroupId);
	}
	
	public List<Object> selectBdGroupDetailBdList(Long bdGroupId, Long bdId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bdGroupId", bdGroupId);
		paramMap.put("bdId", bdId);
		
		return sqlSessionTemplate.selectList("elcg.selectBdGroupDetailBdList", paramMap);
	}
	
	public void saveChargeStatus(String status, Long usid, String chargerId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("usid", usid);
		paramMap.put("chargerId", chargerId);
		
		// status 1=충전전, 2=충전대기, 3=충전중0, 4=충전중10, 5=충전중20
		
		// 충전전일 경우 삭제
		if("1".equals(status)) {
			sqlSessionTemplate.delete("elcg.deleteChargerStatus", paramMap);
			
		} else {
			String chargeCond = sqlSessionTemplate.selectOne("payment.selectLastChargerCond", paramMap);
			
			// 충전대기
			if("2".equals(status)) {
				paramMap.put("status", "406102");
				paramMap.put("chargeCond", chargeCond);
			
			// 충전중
			} else if("3".equals(status)) {
				paramMap.put("status", "406103");
				paramMap.put("chargeAmt", 0);
				paramMap.put("chargeFee", 0);
				paramMap.put("startCd", "3");
				paramMap.put("chargeCond", chargeCond);
				
			} else if("4".equals(status)) {
				paramMap.put("status", "406103");
				paramMap.put("chargeAmt", 10);
				paramMap.put("chargeFee", 20000);
				
			} else if("5".equals(status)) {
				paramMap.put("status", "406103");
				paramMap.put("chargeAmt", 20);
				paramMap.put("chargeFee", 40000);
			}
			
			paramMap.put("lstChUsid", usid);
			
			
			int detailCnt = sqlSessionTemplate.selectOne("elcg.selectChargerStatusDetailCnt", paramMap);
			
			if(detailCnt > 0) {
				sqlSessionTemplate.update("elcg.updateChargerStatus", paramMap);
				
			} else {
				paramMap.put("fstRgUsid", usid);
				
				sqlSessionTemplate.insert("elcg.insertChargerStatus", paramMap);
			}
		}
		
	}
	
	public Map<String, Object> selectChargerStatusDetail(Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.selectOne("elcg.selectChargerStatusDetail", paramMap);
	}
	
	public Map<String, Object> selectChargerStatus(Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.selectOne("elcg.selectChargerStatus", paramMap);
	}
	
	public Boolean isChargerChargeAvailable(Long chargerGroupId, Integer chargerWatt) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chargerGroupId", chargerGroupId);
		paramMap.put("chargerWatt", chargerWatt);
		
		return sqlSessionTemplate.selectOne("elcg.isChargerChargeAvailable", paramMap);
	}
	
	public int updateBdChargeAvailable(Long bdId, String usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bdId", bdId);
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.update("elcg.updateBdChargeAvailable", paramMap);
	}
	
	public Map<String, Object> selectBrokenReportDetail(Long snId) {
		return sqlSessionTemplate.selectOne("elcg.selectBrokenReportDetail", snId);
	}
	
	
	public int updateChargeStatus(Map<String, Object> paramMap) {
		return sqlSessionTemplate.update("elcg.updateChargeStatus", paramMap);
	}
	
	public int insertCharger(Charger charger) {
		return sqlSessionTemplate.insert("elcg.insertCharger", charger);
	}
	
	public int updateChargerListStatus(ChargerList chargerList) {
		return sqlSessionTemplate.update("elcg.updateChargerListStatus", chargerList);
	}
	
}
