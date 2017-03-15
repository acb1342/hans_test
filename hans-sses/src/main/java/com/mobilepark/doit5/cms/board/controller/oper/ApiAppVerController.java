package com.mobilepark.doit5.cms.board.controller.oper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobilepark.doit5.board.service.AppVerService;
import com.uangel.platform.log.TraceLog;

@RestController
@RequestMapping(headers = "Accept=application/json")
public class ApiAppVerController { //extends BaseResource {
	
	@Autowired
	private AppVerService appVerService;
	
	@RequestMapping(value = "/getAppVer", method = RequestMethod.GET)
	public Map<String, Object> getAppver(@RequestParam(value="ver", required=false) String ver, HttpServletRequest request) throws Exception {
		
		String clientType = request.getHeader("Client-Type");
		TraceLog.debug("clientType = " + clientType);
		TraceLog.debug("ver : " + ver);
		return appVerService.getAppVer_api(ver, clientType, "101202");
	}
}
