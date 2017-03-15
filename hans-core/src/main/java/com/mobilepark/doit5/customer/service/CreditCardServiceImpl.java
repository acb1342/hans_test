package com.mobilepark.doit5.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.customer.dao.CarDao;
import com.mobilepark.doit5.customer.dao.CreditCardDao;
import com.mobilepark.doit5.customer.dao.MemberDao;
import com.mobilepark.doit5.customer.dao.PaymentDaoMybatis;
import com.mobilepark.doit5.customer.model.Car;
import com.mobilepark.doit5.customer.model.CreditCard;
import com.mobilepark.doit5.customer.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
import com.uangel.platform.util.EtcUtil;

@Transactional
public class CreditCardServiceImpl extends AbstractGenericService<CreditCard, Long> implements CreditCardService {
	
	@Autowired
	private CreditCardDao creditCardDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private CarDao carDao;
	
	@Autowired
	private PaymentDaoMybatis paymentDaoMybatis;

	@Override
	protected GenericDao<CreditCard, Long> getGenericDao() {
		return creditCardDao;
	}
		
	@Override
	public Map<String, Object> getPostPayDetail(Long usid) {
		return creditCardDao.selectPostPayDetail(usid);
	}
	
	@Override
	public void insertPostPay(Map<String, Object> map, Long usid) {
		
		// 고객정보 조회
		Map<String, Object> userDetail = memberDao.selectUserDetail(usid);
		
		
		// 결제수단 수정
		Member member = new Member();
		member.setId(usid);
		member.setPaymentPlan("302202"); // 결제방식
		member.setPaymentMethod("301301"); // 결제수단
		
		memberDao.updatePayment(member);
		
		CreditCard creditCard = new CreditCard();
		creditCard.setId(usid);
		creditCard.setCreditCardNo((String)map.get("creditCardNo"));
		//creditCard.setValidity((String)map.get("validity"));
		//creditCard.setCvc((String)map.get("cvc"));
		//creditCard.setPasswd((String)map.get("passwd"));
		creditCard.setValidYn((String)map.get("validYn"));
		
		
		// 고객신용카드 수정 (이미 등록된 결제정보가 존재할 경우)
		if(!EtcUtil.isBlank((String)userDetail.get("paymentMethod"))) {
			creditCardDao.updatePostPay(creditCard);
		// 고객신용카드 등록
		} else {
			creditCard.setFstRgUsid(usid.toString());
			creditCard.setFstRgDt(new Date());
			creditCard.setLstChUsid(usid.toString());
			creditCard.setLstChDt(new Date());
			
			creditCardDao.insertPostPay(creditCard);
			
			Map<String, Object> paymentMonth = paymentDaoMybatis.selectPaymentMonth(usid);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("usid", usid);
			paramMap.put("priceType", "502102"); // 기본요금
			
			if (paymentMonth == null) {
				paymentDaoMybatis.insertPaymentMonthFee(paramMap);
			} else {
				paramMap.put("useYm", paymentMonth.get("useYm").toString());
				paymentDaoMybatis.updatePaymentMonthFee(paramMap);
			}
		}
		
		
		// 차량정보 등록
		if(!EtcUtil.isNone((String)map.get("carNo"))) {
			// 차량정보 조회
			Map<String, Object> carDetail = carDao.selectCarDetail(member.getId());
			
			Car car = new Car();
			
			car.setMember(member); 
			car.setCarNo((String)map.get("carNo"));
			car.setFstRgUsid(usid.toString());
			car.setFstRgDt(new Date());
			car.setLstChUsid(usid.toString());
			car.setLstChDt(new Date());
			
			if(carDetail == null) {
				carDao.insertCar(car);
			} else {
				carDao.updateCarNumber(car);
			}
			
		}
				
	}
	
	
	@Override
	public int updatePostPay(CreditCard creditCard) {
		return creditCardDao.updatePostPay(creditCard);
	}
	
}
