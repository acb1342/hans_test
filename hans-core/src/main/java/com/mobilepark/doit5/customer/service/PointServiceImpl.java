package com.mobilepark.doit5.customer.service;

import java.util.Date;
import java.util.Map;

import com.mobilepark.doit5.customer.dao.MemberDao;
import com.mobilepark.doit5.customer.dao.PointDao;
import com.mobilepark.doit5.customer.model.Car;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.customer.dao.CarDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class PointServiceImpl extends AbstractGenericService<Point, Long> implements PointService {
	
	@Autowired
	private PointDao pointDao;
	
	@Autowired
	private CarDao carDao;
	
	@Autowired
	private MemberDao memberDao;
	

	@Override
	protected GenericDao<Point, Long> getGenericDao() {
		return pointDao;
	}
	
	@Override
	public Map<String, Object> getPoint(Long usid) {
		return pointDao.selectPoint(usid);
	}
	
	@Override
	public void setPrePay(Map<String, Object> map, Long usid) {
		
		// 결제수단 수정
		Member member = new Member();
		member.setId(usid);
		member.setPaymentPlan("301201"); // 결제방식
		member.setPaymentMethod("301302"); // 결제수단
		
		memberDao.updatePayment(member);
		
		
		// 포인트 조회
		Map<String, Object> pointDetail = pointDao.selectPoint(usid);
		
		Integer remainPoint = (Integer) map.get("remainPoint");
		
		Point point = new Point();
		point.setId(usid);
		point.setRemainPoint(remainPoint.longValue());
		
		if(pointDetail == null) {
			point.setFstRgUsid(usid.toString());
			point.setFstRgDt(new Date());
			point.setLstChUsid(usid.toString());
			point.setLstChDt(new Date());
			
			pointDao.insertPoint(point);
		} else {
			pointDao.updatePoint(point);
		}
		
		// 차량정보 조회
		Map<String, Object> carDetail = carDao.selectCarDetail(usid);

		member = new Member();
		member.setId(usid);
		
		Car car = new Car();
		
		car.setMember(member);
		car.setCarNo((String)map.get("carNo"));
		
		
		if(carDetail == null) {
			car.setFstRgUsid(usid.toString());
			car.setFstRgDt(new Date());
			car.setLstChUsid(usid.toString());
			car.setLstChDt(new Date());
			
			carDao.insertCar(car);
		} else {
			carDao.updateCarNumber(car);
		}
	}
	
	
}
