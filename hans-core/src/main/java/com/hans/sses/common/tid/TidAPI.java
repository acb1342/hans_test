package com.hans.sses.common.tid;

import java.util.ArrayList;
import java.util.List;

import com.skt.tid.api.TidApiUtil;
import com.skt.tid.api.vo.DataListVO;
import com.skt.tid.api.vo.MessageVO;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

public class TidAPI {
	public static String withdraw(String mbrChnlId) {
		String retCd = "";

		try {
			String tidApiServerIp = Env.get("tid.api.server.ip"); //  T아이디 API 서버 IP
			String tidApiServerPort = Env.get("tid.api.server.port"); //  T아이디 API 서버 PORT
			String chnlId = Env.get("tid.chnl.id"); // 채널 ID
			String serviceId = Env.get("tid.withdraw.svcid"); // SERVICE ID
			String cryptYn = "Y"; // 암복호화 여부
			
			System.out.println("tidApiServerIp : " + tidApiServerIp);
			System.out.println("tidApiServerPort : " + tidApiServerPort);
			
			MessageVO rtnMsg = new MessageVO();
			
			TidApiUtil tidApi = new TidApiUtil(tidApiServerIp, tidApiServerPort, "classpath:/jose/jose_local.jwks");
			// StringBuffer outStr = new StringBuffer();
			
			List<DataListVO> dataSetList = new ArrayList<DataListVO>();
			DataListVO dataList = new DataListVO();
			dataList.put("CHNL_ID",  chnlId);
			dataList.put("MBR_CHNL_ID", mbrChnlId);
			dataSetList.add(dataList);
			
			System.out.println("### req chnlId : " + chnlId);
			System.out.println("### req mbrChnlId : " + mbrChnlId);
			System.out.println("### req serviceId : " + serviceId);
			System.out.println("### req cryptYn : " + cryptYn);
			
			rtnMsg = tidApi.process(chnlId, serviceId, cryptYn, dataSetList);
			System.out.println("### rtnMsg.getServiceId() : " + rtnMsg.getServiceId());
			System.out.println("### rtnMsg.getResultInfo().getCode() : " + rtnMsg.getResultInfo().getCode());
			System.out.println("### rtnMsg.getResultInfo().getMessage() : " + rtnMsg.getResultInfo().getMessage());
			List<DataListVO> resDataList = rtnMsg.getDataSetList();
			System.out.println("### resDataList size : " + resDataList.size());
			// 결과 코드 확인 : IFH_1001 이 아니면 에러
			for(DataListVO dt : resDataList) {
				retCd = dt.getResultInfo().getCode();
			}
			
		} catch(Exception e) {
			StringBuffer temp = new StringBuffer();
			temp.append("Exception ex : " + e.toString());
			for (int i = 0; i < e.getStackTrace().length; i++)
			{
				temp.append("\n" + e.getStackTrace()[i].toString());
			}
			
			TraceLog.error(temp.toString());
		}
		
		return retCd;

	}
}
