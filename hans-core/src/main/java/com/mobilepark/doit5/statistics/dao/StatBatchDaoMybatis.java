package com.mobilepark.doit5.statistics.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatBatchDaoMybatis {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 충전 통계 : 일배치
	 */
	public int tbStatChargeDay()
	{
		return sqlSessionTemplate.insert("statBatch.tbStatChargeDay");
	}
	/**
	 * 충전 통계 : 월배치
	 */
	public int tbStatChargeMonth()
	{
		return sqlSessionTemplate.insert("statBatch.tbStatChargeMonth");
	}
	/**
	 * 설치자 통계 : 일배치
	 */
	public int tbStatInstaller()
	{
		return sqlSessionTemplate.insert("statBatch.tbStatInstaller");
	}
	/**
	 * 설치자 통계 : 월배치
	 */
	public int tbStatInstallerMonth()
	{
		return sqlSessionTemplate.insert("statBatch.tbStatInstallerMonth");
	}
}
