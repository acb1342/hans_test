package com.mobilepark.doit5.statistics.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogHistoryDaoMybatis {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 로그인 이력 count
	 * @param param
	 * @return
	 */
	public int getHistCustCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getHistCustCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 로그인 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getHistCustList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getHistCustList", param);
	}
	/**
	 * PUSH 발신 이력 count
	 * @param param
	 * @return
	 */
	public int getPushMsgCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getPushMsgCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * PUSH 발신 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPushMsgList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getPushMsgList", param);
	}
	/**
	 * 충전기 이력 - 개통 / 재설치 이력 count
	 * @param param
	 * @return
	 */
	public int getStationApplicationCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getStationApplicationCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 충전기 이력 - 개통 / 재설치 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStationApplicationList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getStationApplicationList", param);
	}

	/**
	 * 충전기 이력 - 고장 / 탈거 이력 count
	 * @param param
	 * @return
	 */
	public int getBrokenReportCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getBrokenReportCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 충전기 이력 - 고장 / 탈거 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getBrokenReportList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getBrokenReportList", param);
	}
	/**
	 * 건물주 이력 - 설치, 고장수리 이력 count
	 * @param param
	 * @return
	 */
	public int getLandlordCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLandlordCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 건물주 이력 - 설치, 고장수리 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLandlordList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLandlordList", param);
	}
	/**
	 * 설치자 이력 - 설치, 고장수리 이력 count
	 * @param param
	 * @return
	 */
	public int getInstallerCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getInstallerCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 설치자 이력 - 설치, 고장수리 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getInstallerList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getInstallerList", param);
	}
	/**
	 * 설치자 이력 - 충전기 그룹 count
	 * @param param
	 * @return
	 */
	public int installerChargerGroupCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.installerChargerGroupCount", param);
		if (obj == null) return 0;
		return (int) obj;
	}
	/**
	 * 설치자 이력 - 충전기 count
	 * @param param
	 * @return
	 */
	public int installerChargerCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.installerChargerCount", param);
		if (obj == null) return 0;
		return (int) obj;
	}
	/**
	 * 설치자 이력 - 충전기 목록
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> installerChargerList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.installerChargerList", param);
	}
	/**
	 * 건물주 / 설치자 이력 - PUSH 발신 이력 count
	 * @param param
	 * @return
	 */
	public int getLandlordPushMsgCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLandlordPushMsgCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 건물주 / 설치자 이력 - PUSH 발신 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLandlordPushMsgList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLandlordPushMsgList", param);
	}
	/**
	 * 한전연동이력 count
	 * @param param
	 * @return
	 */
	public int getLogKepcoCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLogKepcoCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 한전연동이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLogKepcoList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLogKepcoList", param);
	}
	/**
	 * ThingPlug 연동이력 count
	 * @param param
	 * @return
	 */
	public int getLogThingPlugCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLogThingPlugCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * ThingPlug 연동이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLogThingPlugList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLogThingPlugList", param);
	}
	/**
	 * GCM/APNS 연동 이력 count
	 * @param param
	 * @return
	 */
	public int getLogGcmApnsCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLogGcmApnsCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * GCM/APNS 연동 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLogGcmApnsList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLogGcmApnsList", param);
	}
	/**
	 * 후불 결재사 연동 이력 count
	 * @param param
	 * @return
	 */
	public int getLogPaymentCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getLogPaymentCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 후불 결재사 연동 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getLogPaymentList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getLogPaymentList", param);
	}
	/**
	 * 건물주 / 설치자 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getAdminLandlordList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getAdminLandlordList", param);
	}
	/**
	 * 일별 충전 이력 count
	 * @param param
	 * @return
	 */
	public int getChargeDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getChargeDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 일별 충전 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargeDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getChargeDayList", param);
	}
	/**
	 * 월별 충전 이력 count
	 * @param param
	 * @return
	 */
	public int getChargeMonthCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getChargeMonthCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 월별 충전 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargeMonthList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getChargeMonthList", param);
	}
	/**
	 * 일별 인증 이력 count
	 * @param param
	 * @return
	 */
	public int getCertCustDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getCertCustDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 일별 인증 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCertCustDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getCertCustDayList", param);
	}
	/**
	 * 월별 인증 이력 count
	 * @param param
	 * @return
	 */
	public int getCertCustMonthCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getCertCustMonthCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 월별 인증 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCertCustMonthList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getCertCustMonthList", param);
	}
	/**
	 * 실시간 이벤트 count
	 * @param param
	 * @return
	 */
	public int getHistChargeIfCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("logHistory.getHistChargeIfCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 월별 인증 이력 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getHistChargeIfList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("logHistory.getHistChargeIfList", param);
	}
}
