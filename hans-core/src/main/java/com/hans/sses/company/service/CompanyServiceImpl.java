package com.hans.sses.company.service;

import com.hans.sses.company.dao.CompanyDaoMybatis;
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
    public List<Map<String, Object>> getTree(int groupId) { return companyDaoMybatis.getTree(groupId); }

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

    @Override
    public Map<String, Object> getMenu(Integer id) {
        return this.companyDaoMybatis.get(id);
    }

    @Override
    public int deleteMenu(Integer id) { return this.companyDaoMybatis.deleteMenu(id); }
}
