package com.mobilepark.doit5.statistics.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatAdjustDaoMybatis {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 한전용 통계 count
	 * @param param
	 * @return
	 */
	public int getChargeDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statAdjust.getKepcoPeriodCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 한전용 통계 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargeDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getKepcoPeriodList", param);
	}
	/**
	 * 일별 설치자 통계 count
	 * @param param
	 * @return
	 */
	public int getInstallerDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statAdjust.getInstallerDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 일별 설치자 통계 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getInstallerDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getInstallerDayList", param);
	}
	/**
	 * 월별 설치자 통계 count
	 * @param param
	 * @return
	 */
	public int getInstallerMonthCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statAdjust.getInstallerMonthCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 월별 설치자 통계 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getInstallerMonthList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getInstallerMonthList", param);
	}
	
	/** 
	 * 설치자 정보
	 */
	public List<Map<String, Object>> getInstallerInfo(String param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getInstallerInfo", param);
	}
	/**
	 * 건물주용 충전기 설치 관리 count
	 * @param param
	 * @return
	 */
	public int getChargerCountByLandlord(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statAdjust.getChargerCountByLandlord", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 건물주용 충전기 설치 관리 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargerListByLandlord(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getChargerListByLandlord", param);
	}
	/**
	 * 설치자용 충전 그룹 관리 count
	 * @param param
	 * @return
	 */
	public int getForInstallerChargerGroupCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statAdjust.getForInstallerChargerGroupCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 설치자용 충전 그룹 관리 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getForInstallerChargerGroupList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statAdjust.getForInstallerChargerGroupList", param);
	}
	/**
	 * 요금제 코드별 가격
	 * @param string
	 * @return
	 */
	public Object getPriceOfType(String priceCode) {
		Map<String, Object> obj = sqlSessionTemplate.selectOne("statAdjust.getPriceOfType", priceCode);
		if (obj == null) return "없음";
		return obj.get("FEE");
	}
	
	/**
	 * 요금제 목록
	 * @return
	 */
	public List<Map<String, Object>> getPriceOfTypeList() {
		return sqlSessionTemplate.selectList("statAdjust.getPriceOfType", null);
	}
	
	/**
	 * 요금제 신규등록
	 * @param map
	 */
	public void insertPriceOfType(Map<String, Object> map) {
		sqlSessionTemplate.insert("statAdjust.insertPriceOfType", map);
	}
	
	/**
	 * 요금제 - 기존버전 적용종료일 변경
	 * @param map
	 */
	public void updatePriceOfTypeForOld(Map<String, Object> map) {
		sqlSessionTemplate.insert("statAdjust.updatePriceOfTypeForOld", map);
	}
	
	/**
	 * 요금제 - 기타항목 copy
	 * @param map
	 */
	public void insertPriceOfTypeForEtc(Map<String, Object> map) {
		 sqlSessionTemplate.insert("statAdjust.insertPriceOfTypeForEtc", map);
		
	}
	
	/**
	 * 요금제 - Max SetId
	 * @return
	 */
	public int getMaxSetId() {
		return sqlSessionTemplate.selectOne("statAdjust.getMaxSetId");
	}
}
