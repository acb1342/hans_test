package com.hans.sses.company.service;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 3. 29..
 */
public interface CompanyService {

    List<Map<String,Object>> getTree(int groupId);

    int checkMenu(String id);

    int orderInsert(Map<String, Object> param);

    int orderUpdate(Map<String, Object> param);

    Map<String, Object> getMenu(Integer id);
    
    List<Map<String,Object>> getDepartmentList(int groupId);

    int deleteMenu(Integer id);
}
