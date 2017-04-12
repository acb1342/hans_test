package com.hans.sses.cms.board.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
		
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Iterator<Map.Entry<String, Object>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			TraceLog.debug("%s : %s", entry.getKey(), entry.getValue().toString());
		}
		
		
		Equipment equipment = this.equipmentService.getDetail((String) map.get("macAddress"));
		
		// 장비 정보 없으면  장비TABLE INSERT 후 LOG 쌓기
		if(equipment==null){
			TraceLog.info("장비정보 없음");
			Equipment equipParam = new Equipment();
			equipParam.setMacaddress(String.valueOf(map.get("macAddress")));
			equipParam.setHardwareinfo(String.valueOf(map.get("hardwardInfo")));
			
			TraceLog.info(equipParam.getMacaddress());
			TraceLog.debug("%s / %s",equipParam.getMacaddress(), equipParam.getHardwareinfo());
			
			
			
			this.equipmentService.equipmentCreate(equipParam);
			
			map.put("regDate", new Date());
			
			this.energyService.EnergyCreate(map);
			
		}
		// 있으면 바로 LOG 쌓기
		else{
			TraceLog.info("장비정보 있음");
			map.put("regDate", new Date());
			
			this.energyService.EnergyCreate(map);
			
			
			
		}
		

		// 구현 필요
		// TBL_EQUIPMENT_INFO 조회 후 존재하지 않으면 insert, 존재하면 pass
		// TBL_LOG_INFO insert

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