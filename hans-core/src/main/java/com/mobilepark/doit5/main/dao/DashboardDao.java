package com.mobilepark.doit5.main.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by kodaji on 2017. 2. 1..
 */
@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface DashboardDao {
    /**
     * 충전기 현황
     * @return
     */
    abstract public List<Map<String, Object>> chargeCount();

    /**
     * 충전기 현황 - 건물주
     * @return
     */
    abstract public List<Map<String, Object>> ownerChargeCount(@Param("value") String value);
    /**
     * 설치신청 현황
     * @return
     */
    abstract public List<Map<String, Object>> installCount();
    /**
     * 설치신청 현황 - 건물주
     * @return
     */
    abstract public List<Map<String, Object>> ownerInstallCount(@Param("value") String value);
    /**
     * 고장신고 현황
     * @return
     */
    abstract public List<Map<String, Object>> brokenCount();
    /**
     * 고장신고 현황 - 건물주
     * @return
     */
    abstract public List<Map<String, Object>> ownerBrokenCount(@Param("value") String value);
    /**
     * 오류 이벤트  현황
     * @return
     */
    abstract public List<Map<String, Object>> dashboardHistChargerIf();
    /**
     * 설치자 월별 처리내역 - 전월, 전전월
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerMonth(@Param("param") Map<String, Object> param);
    /**
     * 설치자 월별 처리내역 - 당월
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerDaily(@Param("param") Map<String, Object> param);
    /**
     * 설치 신청 현황
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerApplicationCount(@Param("param") Map<String, Object> param);
    /**
     * 설치 신청 목록
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerApplicationList(@Param("param") Map<String, Object> param);
    /**
     * 고장 신고 현황
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerBrokenCount(@Param("param") Map<String, Object> param);
    /**
     * 고장 신고 목록
     * @param param
     * @return
     */
    abstract public List<Map<String, Object>> dashboardInstallerBrokenList(@Param("param") Map<String, Object> param);
}
