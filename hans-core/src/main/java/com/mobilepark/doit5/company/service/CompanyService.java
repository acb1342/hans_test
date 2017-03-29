package com.mobilepark.doit5.company.service;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 3. 29..
 */
public interface CompanyService {

    List<Map<String,Object>> getTree();

    int checkMenu(String id);

    int orderInsert(Map<String, Object> param);

    int orderUpdate(Map<String, Object> param);

    Map<String, Object> getMenu(Integer id);

    int deleteMenu(Integer id);
}
