package com.mobilepark.doit5.history.service;

import com.mobilepark.doit5.history.dao.HistChargerIfDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilepark.doit5.history.model.HistChargerIf;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Service
public class HistChargerIfServiceImpl extends AbstractGenericService<HistChargerIf, Long> implements HistChargerIfService {

	@Autowired
	private HistChargerIfDao histChargerIfDao;
	
	@Override
	protected GenericDao<HistChargerIf, Long> getGenericDao() {
		return histChargerIfDao;
	}
}
