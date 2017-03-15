package com.mobilepark.doit5.cms.home;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import com.mobilepark.doit5.cms.auth.Authority;
import com.mobilepark.doit5.main.dao.DashboardDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.dao.MenuDaoMybatis;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.Menu;
import com.mobilepark.doit5.admin.service.MenuService;
import com.mobilepark.doit5.client.service.SecurityKeyService;
import com.mobilepark.doit5.cms.auth.Authentication;
import com.mobilepark.doit5.common.util.TimeUtilz;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.uangel.platform.collection.JsonObject;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.home
 * @Filename     : HomePageController.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 7.       최초 버전
 * =================================================================================
 */
@Controller
public class HomePageController {
	@Autowired
	private MenuService cmsMenuService;

	@Autowired
	private SecurityKeyService securityKeyService;
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private DashboardDao dashboardDao;
	
	private String CR = "\n";

	@RequestMapping("/home/home.htm")
	public String home() {
		return "home/home";
	}

	@RequestMapping("/home/copyright.htm")
	public String copyright() {
		return "home/footer";
	}

	@RequestMapping("/home/menu_back.htm")
	public String left() {
		return "home/menu_back";
	}
	
	/////////
	@RequestMapping("/home/menu.htm")
	public String leftt() {
		return "home/menu";
	}

	@RequestMapping("/home/top.htm")
	public ModelAndView top(HttpSession session) {
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		AdminGroup cmsGroup = authentication.getGroup();

		ModelAndView mav = new ModelAndView("home/top");
		mav.addObject("cmsGroup", cmsGroup);
		mav.addObject("userType", cmsGroup.getName());

		return mav;
	}

	@RequestMapping("/home/title.htm")
	public String title() throws Exception {
		return "home/title";
	}
	
	
	@RequestMapping("/home/main.htm")
	public ModelAndView mainPage(HttpSession session) throws ParseException {
		ModelAndView mav = new ModelAndView("home/main");
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		// 건물주 대시보드
		if (adminGroup.getId() == 3) {
			// 건물 수
			Bd bd = new Bd();
			bd.setAdminId(admin.getId());
			List<Bd> bdList = this.bdService.search(bd);
		
			// 충전그룹 수 , 충전기 수
			int cgCnt = 0;
			int cCnt = 0;
			if (bdList.size() > 0) {
				for (Bd bds : bdList) {
					cgCnt += bds.getChargerGroupSize();
					cCnt += bds.getChargerSize();
				}
			}
			
			mav.addObject("bdCount", bdList.size());
			mav.addObject("chargerGroupCount", cgCnt);
			mav.addObject("chargerCount", cCnt);
		}
		Calendar cal = Calendar.getInstance();
		String month = TimeUtilz.get8StrFormatFromTick(cal.getTimeInMillis());
		mav.addObject("toDate", (month.substring(0,4) + "-" + month.substring(4,6)));

		cal.add(Calendar.MONTH, -2);
		month = TimeUtilz.get8StrFormatFromTick(cal.getTimeInMillis());
		mav.addObject("fromDate", (month.substring(0,4) + "-" + month.substring(4,6)));
		
		return mav;
	}
	
	
	@RequestMapping(value = "/home/dashboardMap.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBdGroupList(HttpSession session,
												@RequestParam(required = false) String searchKeyword) throws Exception {
		
		List<Object> bdGroupList = bdGroupService.getBdGroupList(searchKeyword, 1000016L);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bdGroupList", bdGroupList);
		
		return resultMap;
	}
	
	private Map<String, Object> dashboardData(Admin admin, AdminGroup adminGroup) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("charge1", 0L);
		data.put("charge2", 0L);
		data.put("charge3", 0L);

		data.put("install1", 0L);
		data.put("install2", 0L);
		data.put("install3", 0L);

		data.put("borken1", 0L);
		data.put("borken2", 0L);
		data.put("borken3", 0L);
		
		// 충전기 현황
		List<Map<String, Object>> list = null;
		// 건물주
		if (adminGroup.getId() == 3) list = dashboardDao.ownerChargeCount(admin.getId());
		else list = dashboardDao.chargeCount();
		
		for (Map<String, Object> map : list) {
			
			if (map.get("STATUS") == null) continue;
			if (map.get("CNT") == null) continue;
			String status = map.get("STATUS").toString();
			Long count = (Long) map.get("CNT");
			Long plusCount = 0L;
			
			// 충전가능
			if (StringUtils.equals(status, "406101") || StringUtils.equals(status, "406102")) {
				if (data.get("charge1") != null) plusCount += count;
				data.put("charge1", plusCount);
			}
			if (StringUtils.equals(status, "406103")) data.put("charge2", count); // 충전중
			if (StringUtils.equals(status, "406104")) data.put("charge3", count); // 고장/오류
		}
		
		// 설치신청 현황
		// 건물주
		if (adminGroup.getId() == 3) list = dashboardDao.ownerInstallCount(admin.getId());
		else list = dashboardDao.installCount();
		
		for (Map<String, Object> map : list) {
			
			if (map.get("STATUS") == null) continue;
			if (map.get("CNT") == null) continue;
			String status = map.get("STATUS").toString();
			Long count = (Long) map.get("CNT");
			
			if (StringUtils.equals(status, "407101")) data.put("install1", count); // 접수중
			if (StringUtils.equals(status, "407102")) data.put("install2", count); // 처리중
			if (StringUtils.equals(status, "407103")) data.put("install3", count); // 처리완료
		}
		
		// 고장신고 현황
		// 건물주
		if (adminGroup.getId() == 3) list = dashboardDao.ownerBrokenCount(admin.getId());
		else list = dashboardDao.brokenCount();
		
		for (Map<String, Object> map : list) {
			
			if (map.get("STATUS") == null) continue;
			if (map.get("CNT") == null) continue;
			String status = map.get("STATUS").toString();
			Long count = (Long) map.get("CNT");
			
			if (StringUtils.equals(status, "409101")) data.put("broken1", count); // 접수중
			if (StringUtils.equals(status, "409102")) data.put("broken2", count); // 처리중
			if (StringUtils.equals(status, "409103")) data.put("broken3", count); // 처리완료
		}
		
		// 이벤트 현황
		list = dashboardDao.dashboardHistChargerIf();
		StringBuffer strBuf = new StringBuffer();
		for (Map<String, Object> map : list) {
			
			String cmd = map.get("CMD").toString();
			String description = "-";
			if (StringUtils.equals(cmd, "C00100")) description = "충전기 대기중";
			else if (StringUtils.equals(cmd, "C00101")) description = "차량 플러그 장착";
			else if (StringUtils.equals(cmd, "C00103")) description = "RFID 인식";
			else if (StringUtils.equals(cmd, "C00104")) description = "충전 시작";
			else if (StringUtils.equals(cmd, "C00105")) description = "충전 중";
			else if (StringUtils.equals(cmd, "C00106")) description = "충전 완료";
			else if (StringUtils.equals(cmd, "C00107")) description = "차량 플러그 탈거";
			else if (StringUtils.equals(cmd, "C00108")) description = "디바이스 고장";
			else if (StringUtils.equals(cmd, "C00109")) description = "기타 에러";
			else if (StringUtils.equals(cmd, "C0010A")) description = "충전 대기";
			else if (StringUtils.equals(cmd, "C0010B")) description = "충전 불가";
			else if (StringUtils.equals(cmd, "C0010C")) description = "충전기 강제 탈거";
			else if (StringUtils.equals(cmd, "C0010D")) description = "통신이상";
			
			String link = "style=\"cursor:pointer;\" onclick=\"location.href='/elcg/error/search.htm'\"";
			strBuf.append("<tr " + link + ">" + CR);
			strBuf.append("<td>" +map.get("FST_RG_DT").toString() + "</td>" + CR);
			strBuf.append("<td class='charge'>" + map.get("CHARGER_ID").toString() + "</td>" + CR);
			strBuf.append("<td class='event'>" + description + "</td>" + CR);
			strBuf.append("</tr>" + CR);
		}
		data.put("chargerIf", strBuf.toString());
		
		return data;
	}
	
	private String parseMonth(String month) {
		String retValue = "";
		try {
			retValue = month.substring(0,4) + "년" + month.substring(4) + "월";
		} catch (Exception e) {}
		return retValue;
	}
	
	private String[] getMonths() {
		
		String[] months = new String[3];
		
		Calendar cal = Calendar.getInstance();
		String month = TimeUtilz.get8StrFormatFromTick(cal.getTimeInMillis());
		months[0] = month.substring(0,6);
		
		cal.add(Calendar.MONTH, -1);
		month = TimeUtilz.get8StrFormatFromTick(cal.getTimeInMillis());
		months[1] = month.substring(0,6);
		
		cal.add(Calendar.MONTH, -1);
		month = TimeUtilz.get8StrFormatFromTick(cal.getTimeInMillis());
		months[2] = month.substring(0,6);
		
		return months;
	}
	
	private Map<String, Object> dashboardInstaller(Admin admin) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("process1", 0L);
		data.put("process2", 0L);
		
		data.put("process3", 0L);
		data.put("process4", 0L);

		data.put("process5", 0L);
		data.put("process6", 0L);

		data.put("application1", 0L);
		data.put("application2", 0L);
		
		data.put("broken1", 0L);
		data.put("broken2", 0L);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wkUsid", admin.getId());
		
		List<Map<String, Object>> list = null;
		// 월별 처리 현황 - 당원, 전월, 전전월
		String[] months = this.getMonths();
		data.put("month1", this.parseMonth(months[0]));
		data.put("month2", this.parseMonth(months[1]));
		data.put("month3", this.parseMonth(months[2]));
		
		list = dashboardDao.dashboardInstallerMonth(param);
		for (int i=0 ; i<list.size() ; i++) {
			Map<String, Object> map = list.get(i);
			
			if (map.get("WK_YM") == null) continue;
			if (map.get("WK_TYPE") == null) continue;
			if (map.get("WK_CNT") == null) continue;
			
			String month = map.get("WK_YM").toString();
			String type = map.get("WK_TYPE").toString();
			Long count = (Long) map.get("WK_CNT");

			// 당월
			if (StringUtils.equals(months[0], month)) {
				if (StringUtils.equals(type, "802101")) data.put("process1", count); // 설치완료
				if (StringUtils.equals(type, "802102")) data.put("process2", count); // 고장처리완료
			}
			if (StringUtils.equals(months[1], month)) {
				if (StringUtils.equals(type, "802101")) data.put("process3", count); // 설치완료
				if (StringUtils.equals(type, "802102")) data.put("process4", count); // 고장처리완료
			}
			if (StringUtils.equals(months[2], month)) {
				if (StringUtils.equals(type, "802101")) data.put("process5", count); // 설치완료
				if (StringUtils.equals(type, "802102")) data.put("process6", count); // 고장처리완료
			}
		}
		
		// 설치신청 현황 -------------------------------------------------------
		list = dashboardDao.dashboardInstallerApplicationCount(param);
		for (Map<String, Object> map : list) {
			if (map.get("STATUS") == null) continue;
			if (map.get("CNT") == null) continue;
			
			String status = map.get("STATUS").toString();
			Long count = (Long) map.get("CNT");

			// 당월
			if (StringUtils.equals(status, "407102")) data.put("application1", count); // 처리중
			if (StringUtils.equals(status, "407103")) data.put("application2", count); // 처리완료
		}
		
		// 설치신청 목록
		list = dashboardDao.dashboardInstallerApplicationList(param);
		StringBuffer strBuf = new StringBuffer();
		for (Map<String, Object> map : list) {
			
			if (map.get("STATUS") == null) continue;
			if (map.get("BD_NAME") == null) continue;
			if (map.get("BD_GROUP_NAME") == null) continue;
			
			String status = map.get("STATUS").toString();
			String description = "-";
			if (StringUtils.equals(status, "407102")) description = "처리중";
			else if (StringUtils.equals(status, "407103")) description = "처리완료";
			
			strBuf.append("<tr>" + CR);
			strBuf.append("<td style='width:50%' scope='row'>" + map.get("BD_GROUP_NAME").toString() + "</td>" + CR);
			strBuf.append("<td style='width:30%'>" + map.get("BD_NAME").toString() + "</td>" + CR);
			strBuf.append("<td style='width:20%' class='point_color'>" + description + "</td>" + CR);
			strBuf.append("</tr>" + CR);
		}
		data.put("applicationList", strBuf.toString());

		// 고장 신고 현황 -------------------------------------------------------
		list = dashboardDao.dashboardInstallerBrokenCount(param);
		for (Map<String, Object> map : list) {
			if (map.get("STATUS") == null) continue;
			if (map.get("CNT") == null) continue;
			
			String status = map.get("STATUS").toString();
			Long count = (Long) map.get("CNT");

			// 당월
			if (StringUtils.equals(status, "409102")) data.put("trouble1", count); // 처리중
			if (StringUtils.equals(status, "409103")) data.put("trouble2", count); // 처리완료
		}
		list = dashboardDao.dashboardInstallerBrokenList(param);
		strBuf = new StringBuffer();
		for (Map<String, Object> map : list) {
			
			if (map.get("STATUS") == null) continue;
			if (map.get("BD_NAME") == null) continue;
			if (map.get("BD_GROUP_NAME") == null) continue;
			
			String status = map.get("STATUS").toString();
			String description = "-";
			if (StringUtils.equals(status, "409102")) description = "처리중";
			else if (StringUtils.equals(status, "409103")) description = "처리완료";
			
			strBuf.append("<tr>" + CR);
			strBuf.append("<td style='width:50%' scope='row'>" + map.get("BD_GROUP_NAME").toString() + "</td>" + CR);
			strBuf.append("<td style='width:30%'>" + map.get("BD_NAME").toString() + "</td>" + CR);
			strBuf.append("<td style='width:20%' class='point_color'>" + description + "</td>" + CR);
			strBuf.append("</tr>" + CR);
		}
		data.put("troubleList", strBuf.toString());
		
		return data;
	}

	@RequestMapping("/home/dashboard.json")
	@ResponseBody
	public Map<String, Object> dashboard(HttpSession session) throws Exception {
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		if (adminGroup.getId() == 2) return dashboardInstaller(admin); 
		return dashboardData(admin, adminGroup);
	}

	@RequestMapping("/home/systemInspection.htm")
	public ModelAndView systemInspection() {
		ModelAndView mav = new ModelAndView("home/popupSystemInspection");

		JsonObject values = this.securityKeyService.getSystemInspectionValues();
		mav.addObject("date", values.get("date"));
		mav.addObject("desc", values.get("desc"));

		return mav;
	}

	@RequestMapping("/home/menuRender.json")
	@ResponseBody
	public List<Map<String, Object>> menuRender(@RequestParam("node") Integer id, HttpSession session) throws Exception {
		List<Map<String, Object>> authChildMenus = new ArrayList<Map<String, Object>>();
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		List<Map<String, Object>> allChildMenus = this.cmsMenuService.getChildMenus(id);
		TraceLog.debug("menu rendering [nodeId:%d]", id);
		
		for (Map<String, Object> menu : allChildMenus) {
			Integer childMenuId = Integer.parseInt(menu.get("id").toString());
			Authority authority = authentication.getAuthority(childMenuId);
			if (authority != null && authority.isRead()) {
				authChildMenus.add(menu);
			}
		}

		List<Map<String, Object>> authorizedMenus = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> cmsMenu : authChildMenus) {
			authorizedMenus.add(this.getJsonObjectOfCmsMenu(cmsMenu));
		}

		return authorizedMenus;
	}

	private Map<String, Object> getJsonObjectOfCmsMenu(Map<String, Object> menu) {
		Map<String, Object> jsonObj = new HashMap<String, Object>();

		jsonObj.put("id", Integer.parseInt(menu.get("id").toString()));
		jsonObj.put("text", menu.get("title"));
		jsonObj.put("url", menu.get("url"));

		String menuType = menu.get("type").toString();
		if ("LEAF".equals(menuType)) {
			jsonObj.put("leaf", true);
			jsonObj.put("cls", "leafNode");
			jsonObj.put("icon", "/images/menuIcon/leaf.gif");
			jsonObj.put("iconCls", "leafIcon");
		} else {
			jsonObj.put("cls", "dirNode");
			jsonObj.put("icon", "/images/menuIcon/folder.gif");
			jsonObj.put("iconCls", "dirIcon");
		}
		
		return jsonObj;
	}
}

