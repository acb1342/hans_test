package com.mobilepark.doit5.customer.service;

import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.customer.dao.PaymentDaoMybatis;
import com.mobilepark.doit5.customer.dao.RfidApplicationDao;
import com.mobilepark.doit5.customer.dao.RfidCardDao;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.model.RfidApplication;
import com.mobilepark.doit5.customer.model.RfidCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class RfidCardServiceImpl extends AbstractGenericService<RfidCard, Long> implements RfidCardService {
	
	@Autowired
	private RfidCardDao rfidCardDao;
	
	@Autowired
	private RfidApplicationDao rfidApplicationDao;
	
	@Autowired
	private PaymentDaoMybatis paymentDaoMybatis;
	

	@Override
	protected GenericDao<RfidCard, Long> getGenericDao() {
		return rfidCardDao;
	}
	
	@Override
	public int updateLoseDt(Long snId) {
		return rfidCardDao.updateLoseDt(snId);
	}
	
	
	@Override
	public Map<String, Object> validCardNo(RfidCard rfidCard) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long isCard = rfidCardDao.isCardNo(rfidCard);	
		
		if(isCard == 0) {
			// 카드번호 미존재 에러
			map.put("errorCd", "428001");
			map.put("msg", "확인되지 않는 카드번호입니다. 번호를 확인하시고 정확하게 다시 입력해 주세요.");
			
			return map;
		}
		
		Long isOverCard = rfidCardDao.isOverCardNo(rfidCard);	

		if(isOverCard != 0) {
			// 이미 사용중인 카드번호 에러
			map.put("errorCd", "428002");
			map.put("msg", "다른 사용자에 의해 등록된 카드입니다. 본인이 수령한 카드번호를 입력해 주세요.");
			return map;
		}
		
				
		return rfidCardDao.isValidCardNo(rfidCard);
	}
	
	@Override
	public RfidApplication insertCardReq(RfidApplication rfidApplication) {
		
		Member m = rfidApplication.getMember();
		
		Map<String, Object> detail = paymentDaoMybatis.selectPaymentMonth(m.getId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", m.getId());
		paramMap.put("priceType", "502102");
		
		
		if(detail == null) {
			paramMap.put("cardIssuingCnt", 1);
			paymentDaoMybatis.insertPaymentMonthFee(paramMap);
		} else {
			paramMap.put("useYm", detail.get("useYm"));
			paymentDaoMybatis.updatePaymentMonthFee(paramMap);
		}
		
		return rfidApplicationDao.insertCardReq(rfidApplication);
	}
	
	
}
