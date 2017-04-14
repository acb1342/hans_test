package com.hans.sses.cms.board.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hans.sses.admin.service.EnergyService;
import com.hans.sses.attendance.service.AttendanceService;
import com.hans.sses.board.service.AppVerService;
import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.service.EquipmentService;

import com.hans.sses.member.service.UserEqService;

import com.uangel.platform.log.TraceLog;

@RestController
@RequestMapping(headers = "Accept=application/json")
public class ApiAppVerController { //extends BaseResource {

	@Autowired
	private AppVerService appVerService;
	
	@Autowired
	private AttendanceService attendaceService;

	private EquipmentService equipmentService;
	
	@Autowired
	private UserEqService userEqService;	
	
	@Autowired
	private EnergyService energyService;

	@RequestMapping(value = "/getAppVer", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getAppver(@RequestParam(value="ver", required=false) String ver, HttpServletRequest request) {
		
		String clientType = request.getHeader("Client-Type");
		TraceLog.info("clientType = " + clientType);
		TraceLog.info("ver : " + ver);
		
		return appVerService.getAppVer_api(ver, clientType, "101206");
	}

	@RequestMapping(value = "/sendPCEnergy", method = RequestMethod.POST)
	public ResponseEntity<?> sendPCEnergy(@RequestBody Map<String, Object> map) throws Exception {

		Map<String, String> entity = new HashMap<String, String>();

		if (map.get("macAddress") == null || StringUtils.isBlank(map.get("macAddress").toString())) {
			entity.put("errorCode", HttpStatus.BAD_REQUEST.toString());
			entity.put("errorMsg", "필수 파라미터가 존재하지 않습니다.");

			return new ResponseEntity<>(entity, HttpStatus.OK);
		}
		
		// LOG
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Iterator<Map.Entry<String, Object>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			TraceLog.debug("%s : %s", entry.getKey(), entry.getValue().toString());
		}
		
		/*
		Equipment equipment = this.equipmentService.getDetail(String.valueOf(map.get("macAddress")));
		
		// 등록된 장비정보 없으면  장비 table insert
		if(equipment == null){
			TraceLog.info("%","장비 등록정보 없음");
			Equipment equipParam = new Equipment();
			equipParam.setMacaddress(String.valueOf(map.get("macAddress")));
			equipParam.setHardwareinfo(String.valueOf(map.get("hardwardInfo")));
			
			this.equipmentService.equipmentCreate(equipParam);
		}
		
		List<Map<String, Object>> userSeq = new ArrayList<Map<String, Object>>();
		userSeq = this.userEqService.getUserSeq(String.valueOf(map.get("macAddress")));
		
		//유저 장비 맵핑정보 있을때만
		if(!userSeq.isEmpty()){
			TraceLog.info("%s","유저/장비 맵핑정보 있음");
			
			//이벤트 구분이 0: 전원OFF  1:전원 ON 이면  근태관리 table insert
			if(String.valueOf(map.get("eventType")).equals("0")||String.valueOf(map.get("eventType")).equals("1")){
				map.put("userSeq", userSeq.get(0).get("userSeq"));
				this.attendaceService.create(map);
			}
		}
		
		//에너지로그 table insert
		map.put("regDate", new Date());
		this.energyService.EnergyCreate(map);*/

		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/getPCEnergy", method = RequestMethod.POST)
	public ResponseEntity<?> getPCEnergy(@RequestBody Map<String, Object> map) throws Exception {

		Map<String, String> entity = new HashMap<String, String>();

		if (map.get("macAddress") == null || StringUtils.isBlank(map.get("macAddress").toString())) {
			entity.put("errorCode", HttpStatus.BAD_REQUEST.toString());
			entity.put("errorMsg", "필수 파라미터가 존재하지 않습니다.");

			return new ResponseEntity<>(entity, HttpStatus.OK);
		}
		
		// LOG
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Iterator<Map.Entry<String, Object>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			TraceLog.debug("%s : %s", entry.getKey(), entry.getValue().toString());
		}
		
		

		// 구현 필요
		// mac address 로 watt횾 구한 후 money, co2, tree로 변환 후 리턴
		entity.put("watt", "2.554");
		entity.put("money", "236.113");
		entity.put("co2", "1.083");
		entity.put("tree", "0.391");

		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	/** 근태관리 일별 등록 */
	@RequestMapping(value = "/sendAttendance", method = RequestMethod.POST)
	public Map<String, String> create(@RequestParam Map<String, Object> map,HttpServletRequest request) {
	
		TraceLog.debug(request.getHeader("Accept") + " - " + request.getHeader("Content-Type"));
		printMap(map);
		
		Map<String, String> resMap = new HashMap<String, String>();
		if (map.get("macAddress") == null || map.get("userSeq") == null) {
			resMap.put("errorMsg", "필수 파라미터가 존재하지 않습니다.");
			return resMap;
		}
		
		return this.attendaceService.create(map);
	}
	
	void printMap(Map<String, ?> map) {
		TraceLog.info("========== Print Map ==========");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TraceLog.debug("[%s] : [%s]", key, map.get(key));
		}
		TraceLog.info("===============================");
	}
}