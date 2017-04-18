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
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private UserEqService userEqService;	
	
	@Autowired
	private EnergyService energyService;

	@RequestMapping(value = "/getAppVer", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	
	/**
	 * 앱버전 관리
	 */
	public Map<String, Object> getAppver(@RequestParam(value="ver", required=false) String ver, HttpServletRequest request) {
		
		String clientType = request.getHeader("Client-Type");
		TraceLog.info("clientType = " + clientType);
		TraceLog.info("ver : " + ver);
		
		return appVerService.getAppVer_api(ver, clientType, "101206");
	}

	/**
	 * 에너지 로그 수집
	 */
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
		
		Equipment equipment = this.equipmentService.getDetail(map.get("macAddress").toString());		
				
		// 등록된 장비정보 없으면  장비 table insert
		if(equipment == null){
			TraceLog.info("%","장비 등록정보 없음");
			Equipment equipParam = new Equipment();
			equipParam.setMacaddress(map.get("macAddress").toString());
			equipParam.setHardwareinfo(map.get("hardwardInfo").toString());
			
			this.equipmentService.equipmentCreate(equipParam);
		}
		
		List<Map<String, Object>> userSeq = new ArrayList<Map<String, Object>>();
		userSeq = this.userEqService.getUserSeq(map.get("macAddress").toString());
		
		//유저 장비 맵핑정보 있을때만
		if(!userSeq.isEmpty()){
			TraceLog.info("%s","유저/장비 맵핑정보 있음");
			
			//이벤트 구분이 0: 전원OFF  1:전원 ON 이면  근태관리 table insert
			if(map.get("eventType").toString().equals("0")||map.get("eventType").toString().equals("1")){
				map.put("userSeq", userSeq.get(0).get("userSeq"));
				this.attendaceService.create(map);
			}
		}
		
		//에너지로그 table insert
		map.put("regDate", new Date());
		this.energyService.EnergyCreate(map);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 에너지 절감량 조회
	 */	
	@RequestMapping(value = "/getPCEnergy", method = RequestMethod.POST)
	public ResponseEntity<?> getPCEnergy(@RequestBody Map<String, Object> map) throws Exception {

		Map<String, Object> entity = new HashMap<String, Object>();

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
		
		Map<String, Object> savingEnergy = new HashMap<String, Object>();
		savingEnergy = this.energyService.getSavingEnergy(map);
		
		if(savingEnergy==null){
			entity.put("errorCode", HttpStatus.BAD_REQUEST.toString());
			entity.put("errorMsg", "검색 결과가 존재하지 않습니다.");

			return new ResponseEntity<>(entity, HttpStatus.OK);
		}
		
		int savingTime, electricPower;									// 총 절약시간, 소비전력, 전기요금
		double dualW, money, co2, tree, charge;											// 절약된 전력량, 전기요금, 탄소배출량, 나무 수
		
		savingTime = Integer.valueOf(savingEnergy.get("savingTime").toString());
		electricPower = Integer.valueOf(savingEnergy.get("watt").toString());
		charge = Double.valueOf(savingEnergy.get("charge").toString());
		
		dualW = (savingTime * electricPower) / 3600.0 / 1000.0;				// (절약시간 * 소비전력) / 3600 / 1000	
		money = dualW * charge;													// 절약 전력량 * 전기요금
		co2 = dualW * 0.4836;													// 절약 전력량 * 0.4836
		tree = co2 / 2.77;														// 절약 탄소배출량 / 2.77  ( 나무 1그루당 탄소 2.77kg 상쇄 )
		
		dualW = Double.parseDouble(String.format("%.4f" , dualW));
		money = Double.parseDouble(String.format("%.4f" , money));
		co2 = Double.parseDouble(String.format("%.4f" , co2));
		tree = Double.parseDouble(String.format("%.4f" , tree));

		entity.put("watt", dualW);
		entity.put("money", money);
		entity.put("co2", co2);
		entity.put("tree", tree);

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