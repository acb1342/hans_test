package com.mobilepark.doit5.statistics.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatChargeDaoMybatis {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 일별 충전 통계 count
	 * @param param
	 * @return
	 */
	public int getChargeDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statCharge.getChargeDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 일별 충전 통계 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargeDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statCharge.getChargeDayList", param);
	}
	/**
	 * 월별 충전 통계 count
	 * @param param
	 * @return
	 */
	public int getChargeMonthCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statCharge.getChargeMonthCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 월별 충전 통계 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargeMonthList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statCharge.getChargeMonthList", param);
	}
	/**
	 * 충전기별 충전 통계 - 일별 count
	 * @param param
	 * @return
	 */
	public int getChargerDayCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statCharge.getChargerDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 충전기별 충전 통계 - 일별 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargerDayList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statCharge.getChargerDayList", param);
	}
	/**
	 * 충전기별 충전 통계 - 월별 count
	 * @param param
	 * @return
	 */
	public int getChargerMonthCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statCharge.getChargerDayCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 충전기별 충전 통계 - 월별 list
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getChargerMonthList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statCharge.getChargerMonthList", param);
	}
	/**
	 * 과금 통계
	 * @param param
	 * @return
	 */
	public int getStatPaymentCount(Map<String, Object> param)
	{
		Object obj = sqlSessionTemplate.selectOne("statCharge.getStatPaymentCount", param);
		if (obj == null) return 0;
		return (Integer) obj;
	}
	/**
	 * 과금 통계
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStatPaymentList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectList("statCharge.getStatPaymentList", param);
	}
	/**
	 * 과금 통계 합계
	 * @param param
	 * @return
	 */
	public Map<String, Object> getTotalPaymentList(Map<String, Object> param)
	{
		return sqlSessionTemplate.selectOne("statCharge.getTotalPayment", param);
	}
}
