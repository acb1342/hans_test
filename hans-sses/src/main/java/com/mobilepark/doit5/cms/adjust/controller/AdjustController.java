package com.mobilepark.doit5.cms.adjust.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.history.model.HistCharge;
import com.mobilepark.doit5.statistics.dao.LogHistoryDaoMybatis;
import com.mobilepark.doit5.statistics.dao.StatAdjustDaoMybatis;
import com.mobilepark.doit5.statistics.service.KepcoExcelServiceImpl;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

@Controller
@SessionAttributes("adjust")
public class AdjustController {
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private StatAdjustDaoMybatis statAdjustDaoMybatis;

	@Autowired
	private LogHistoryDaoMybatis logHistoryDaoMybaits;
	
	/**
	 * 요금제 조회
	 */
	@RequestMapping("/adjust/rateSystem.htm")
	public ModelAndView rateSystem() throws Exception {
		
		TraceLog.info("[rateSystem START]");
		
			
		List<Map<String, Object>> basePriceList = new ArrayList<Map<String, Object>>();
		Map<String, Object> basePrice = new HashMap<String, Object>();

		basePrice.put("basePrice", statAdjustDaoMybatis.getPriceOfType("502101")); //기본료
		basePrice.put("cardPrice", statAdjustDaoMybatis.getPriceOfType("502102")); //카드발급비
		
		basePriceList.add(basePrice);
		
		List<Map<String, Object>> rateSystemList = new ArrayList<Map<String, Object>>();
		rateSystemList = statAdjustDaoMybatis.getPriceOfTypeList();

		String applyStartDate = "";
		String applyStartTime = "";
		if(rateSystemList.size() > 0){
			String startDate = String.valueOf(rateSystemList.get(0).get("START_YMDHHMI")); 
			applyStartDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8);
			applyStartTime = startDate.substring(8, 10) + ":" + startDate.substring(10, 12);
		}
		
		
		ModelAndView mav = new ModelAndView("adjust/rateSystem");
		mav.addObject("basePriceList", basePriceList);
		mav.addObject("applyStartDate", applyStartDate);
		mav.addObject("applyStartTime", applyStartTime);
		mav.addObject("rateSystemList", rateSystemList);

		return mav;
	}
	
	/**
	 * 요금제 수정
	 */
	@RequestMapping("/adjust/rateSystemForm.htm")
	public ModelAndView rateSystemForm() throws Exception {
		
		TraceLog.info("[rateSystemForm START]");
		
		ModelAndView mav = this.rateSystem();
		mav.setViewName("adjust/rateSystemForm");

		return mav;
	}
	
	/**
	 * 요금제 수정
	 */
	@RequestMapping("/adjust/rateSystemUpdate.htm")
	public ModelAndView rateSystemUpdate(
			@RequestParam(value = "basePay", required = false) String basePay,
			@RequestParam(value = "cardPay", required = false) String cardPay,
			@RequestParam(value = "snId", required = false) String[] snId,
			@RequestParam(value = "setId", required = false) String[] setId,
			@RequestParam(value = "feeName", required = false) String[] feeName,
			@RequestParam(value = "amtPrice", required = false) String[] amtPrice,
			@RequestParam(value = "smApplyPeriod", required = false) String[] smApplyPeriod,
			@RequestParam(value = "sdApplyPeriod", required = false) String[] sdApplyPeriod,
			@RequestParam(value = "emApplyPeriod", required = false) String[] emApplyPeriod,
			@RequestParam(value = "edApplyPeriod", required = false) String[] edApplyPeriod,
			@RequestParam(value = "shApplyTime", required = false) String[] shApplyTime,
			@RequestParam(value = "smApplyTime", required = false) String[] smApplyTime,
			@RequestParam(value = "ehApplyTime", required = false) String[] ehApplyTime,
			@RequestParam(value = "empplyTime", required = false) String[] empplyTime,
			@RequestParam(value = "applyStartDate", required = false) String applyStartDate,
			@RequestParam(value = "applyStartTime", required = false) String applyStartTime,
			HttpSession session) throws Exception {
		
		
		TraceLog.info("[basePay:%s] [cardPay:%s] [applyStartDate:%s] [applyStartTime:%s]", basePay, cardPay, applyStartDate, applyStartTime);
		
		int maxSetId = statAdjustDaoMybatis.getMaxSetId();
		
		String userId = (String) session.getAttribute(SessionAttrName.USER_ID);
		Map<String, Object> map = new HashMap<String, Object>();
		
		/**
		 * 1. 기존버전의 삭제나 수정은 불가
		 * 2. 적용종료일을 현재입력받은 적용시작일로 변경
		 * 3. 새로운 SET을 생성
		 */
		applyStartDate = applyStartDate.replace("-", "");
		applyStartTime = applyStartTime.replace(":", "");
		for(int i=0; i<feeName.length; i++) {
			TraceLog.info("[snId:%s] [setId:%s] [feeName:%s] [smApplyPeriod:%s] [sdApplyPeriod:%s] [emApplyPeriod:%s]  "
					+ "[edApplyPeriod:%s] [shApplyTime:%s] [smApplyTime:%s] [ehApplyTime:%s] [empplyTime:%s] ",
					snId[i], setId[i], feeName[i], smApplyPeriod[i], sdApplyPeriod[i], emApplyPeriod[i], 
					edApplyPeriod[i], shApplyTime[i], smApplyTime[i], ehApplyTime[i], empplyTime[i]);
		
			if(i == 0){
				//기타항목을 copy
				map = new HashMap<String, Object>();
				map.put("setId", maxSetId);
				map.put("userId", userId);
				map.put("applyStartDate", applyStartDate);
				map.put("applyStartTime", applyStartTime);
				statAdjustDaoMybatis.insertPriceOfTypeForEtc(map);
				
				//기본요금
				map = new HashMap<String, Object>();
				map.put("setId", maxSetId);
				map.put("feeName", "기본료");
				map.put("priceType", "502101");
				map.put("amtPrice", basePay);
				map.put("applyStartDate", applyStartDate);
				map.put("applyStartTime", applyStartTime);
				map.put("userId", userId);
				statAdjustDaoMybatis.insertPriceOfType(map); 
				
				//카드발급비용
				map = new HashMap<String, Object>();
				map.put("setId", maxSetId);
				map.put("feeName", "카드발급비");
				map.put("priceType", "502102");
				map.put("amtPrice", cardPay);
				map.put("applyStartDate", applyStartDate);
				map.put("applyStartTime", applyStartTime);
				map.put("userId", userId);
				statAdjustDaoMybatis.insertPriceOfType(map);
				
				//기존 SET 적용종료일 변경
				statAdjustDaoMybatis.updatePriceOfTypeForOld(map);
				
			}
			
			map = new HashMap<String, Object>();
			
			map.put("snId", snId[i]);
			map.put("setId", maxSetId);
			map.put("feeName", feeName[i]);
			map.put("priceType", "502103");
			map.put("amtPrice", amtPrice[i]);
			map.put("smApplyPeriod", smApplyPeriod[i]);
			map.put("sdApplyPeriod", sdApplyPeriod[i]);
			map.put("emApplyPeriod", emApplyPeriod[i]);
			map.put("edApplyPeriod", edApplyPeriod[i]);
			map.put("shApplyTime", shApplyTime[i]);
			map.put("smApplyTime", smApplyTime[i]);
			map.put("ehApplyTime", ehApplyTime[i]);
			map.put("empplyTime", empplyTime[i]);
			map.put("applyStartDate", applyStartDate);
			map.put("applyStartTime", applyStartTime);
			map.put("userId", userId);
			
			statAdjustDaoMybatis.insertPriceOfType(map); //신규 SET 생성
		}
		
		ModelAndView mav = this.rateSystem();
		mav.setViewName("adjust/rateSystemForm");

		return mav;
	}
	
	/**
	 * 한전용 통계 조회
	 */
	@RequestMapping("/adjust/kepco.htm")
	public ModelAndView kepco(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "paymentPeriod", required = false) String paymentPeriod,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [buildingDetail:%s] [paymentPeriod:%s] [fromDate:%s] [toDate:%s]",
				page, searchType, bdId, paymentPeriod, fromDate, toDate);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "building")) {
			param.put("bdId", bdId);
			count = statAdjustDaoMybatis.getChargeDayCount(param);
			list = statAdjustDaoMybatis.getChargeDayList(param);
		}
		else if (StringUtils.equals(searchType, "paymentPeriod")) {
			param.put("fromDate", changeFormat(fromDate, 6));
			param.put("toDate", changeFormat(toDate, 6));
			param.put("paymentPeriod", paymentPeriod);
			count = statAdjustDaoMybatis.getChargeDayCount(param);
			list = statAdjustDaoMybatis.getChargeDayList(param);
		}

		PaginatedList kepcoList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("adjust/kepco");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("kepcoList", kepcoList);
		
		// 화면에서 상세/동명 유지
		if (bdGroupId != null && bdGroupId > 0) {
			BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
			Bd selBd = new Bd();
			selBd.setBdGroup(selGroupList);
			List<Bd> selBdList = this.bdService.search(selBd);
			mav.addObject("selBdList", selBdList);
			mav.addObject("selBdGroupId", bdGroupId);
		}		

		return mav;
	}
	
	/**
	 * 한전용 통계 조회
	 */
	@RequestMapping("/adjust/kepcoExcel.json")
	public void kepcoExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "paymentPeriod", required = false) String paymentPeriod,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [buildingDetail:%s] [paymentPeriod:%s] [fromDate:%s] [toDate:%s]",
				page, searchType, bdId, paymentPeriod, fromDate, toDate);

		Map<String, Object> param = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "building")) {
			param.put("bdId", bdId);
		}
		else if (StringUtils.equals(searchType, "paymentPeriod")) {
			param.put("fromDate", changeFormat(fromDate, 6));
			param.put("toDate", changeFormat(toDate, 6));
			param.put("paymentPeriod", paymentPeriod);
		}
		long count = statAdjustDaoMybatis.getChargeDayCount(param);
		
		param.put("startRow", 0);
		param.put("rowPerPage", count);

		list = statAdjustDaoMybatis.getChargeDayList(param);
		
		String name = "kepco.xlsx";
		
		KepcoExcelServiceImpl biz = new KepcoExcelServiceImpl();
        Workbook wb = biz.getWorkbook(name, list);

        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Transfer-Encoding","binary");
        response.setHeader("Content-Disposition","attachment;fileName=\""+ name + "\";");

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
	}
	
	/**
	 * 설치자용 통계 조회
	 */
	@RequestMapping("/adjust/installer.htm")
	public ModelAndView installer(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "usname", required = false) String usname) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [usname:%s]", page, searchType, fromDate, toDate, usname);

		Admin adminSession = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroupSession =  (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("wkName", usname);
		if (adminGroupSession.getId() == 2) param.put("wkUsid", adminSession.getId());
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "daily")) {
			param.put("fromDate", changeFormat(fromDate, 8));
			param.put("toDate", changeFormat(toDate, 8));
			
			count = logHistoryDaoMybaits.getInstallerCount(param);
			list = logHistoryDaoMybaits.getInstallerList(param);
			
			for (Map<String, Object> map : list) {
				
				if (map.get("WK_TYPE") == null) continue;
				if (StringUtils.equals(map.get("WK_TYPE").toString(), "802102")) {
					map.put("EXTRA_PAY", statAdjustDaoMybatis.getPriceOfType("502105").toString());
					continue;
				}
				
				param.put("bdId", (Long)map.get("BD_ID"));
				map.put("CHARGER_GROUP_NAME", logHistoryDaoMybaits.installerChargerGroupCount(param));
				map.put("MGMT_NO", logHistoryDaoMybaits.installerChargerCount(param));
				map.put("EXTRA_PAY", statAdjustDaoMybatis.getPriceOfType("502104").toString());
			}
			
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));
			param.put("toDate", changeFormat(toDate, 6));
		
			count = statAdjustDaoMybatis.getInstallerMonthCount(param);
			list = statAdjustDaoMybatis.getInstallerMonthList(param);
		}

		PaginatedList installerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("adjust/installer");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("installerList", installerList);

		return mav;
	}
	
	/**
	 * 사용자조회 팝업
	 */
	@RequestMapping("/adjust/installerPopup.htm")
	public ModelAndView installerPopup(@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		TraceLog.info("[searchValue:%s]", searchValue);
		
		List<HistCharge> installerList = new ArrayList<HistCharge>();
		
		for(int i=0; i<11; i++) {
			HistCharge histCharge = new HistCharge();
			
			histCharge.setChargerName(String.valueOf(i+1));
			
			installerList.add(histCharge);
		}
		
		ModelAndView mav = new ModelAndView("adjust/installerPopup");
		mav.addObject("installerList", installerList);
		return mav;
	}
	
	/**
	 * 건물조회 팝업
	 */
	@RequestMapping("/adjust/buildingPopup.htm")
	public ModelAndView buildingPopup(@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		TraceLog.info("[searchValue:%s]", searchValue);
		
		List<HistCharge> buildingList = new ArrayList<HistCharge>();
		
		for(int i=0; i<11; i++) {
			HistCharge histCharge = new HistCharge();
			
			histCharge.setChargerName(String.valueOf(i+1));
			
			buildingList.add(histCharge);
		}
		
		ModelAndView mav = new ModelAndView("adjust/buildingPopup");
		mav.addObject("buildingList", buildingList);
		return mav;
	}
	
	
	public String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			
			return date;
		}
		return null;
	}
}
