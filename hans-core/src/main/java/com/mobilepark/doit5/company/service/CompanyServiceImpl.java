package com.mobilepark.doit5.company.service;

import com.mobilepark.doit5.company.dao.CompanyDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 3. 29..
 */

@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDaoMybatis companyDaoMybatis;

    @Override
    public List<Map<String, Object>> getTree() { return companyDaoMybatis.getTree(); }

    @Override
    public int orderUpdate(Map<String, Object> param) {
        return companyDaoMybatis.orderUpdate(param);
    }

    @Override
    public int orderInsert(Map<String, Object> param) { return companyDaoMybatis.orderInsert(param); }

    @Override
    public int checkMenu(String id) {
        return this.companyDaoMybatis.checkMenu(id);
    }
}
