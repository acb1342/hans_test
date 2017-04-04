package com.hans.sses.cms.company.controller;

import com.hans.sses.company.service.CompanyService;
import com.uangel.platform.log.TraceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 3. 29..
 */
@Controller
@SessionAttributes({
        "company"
})
public class CompanyController {
    @Autowired
    private CompanyService companyService;


    /**
     * 메뉴 메인 뷰
     */
    @RequestMapping("/member/company/view.htm")
    public String mainView() throws Exception {
        return "company/view";
    }
    /**
     * 루트 메뉴 얻기
     */
    @RequestMapping(value = "/member/company/getTree.json")
    @ResponseBody
    public List<Map<String, Object>> getTree() {
        return this.companyService.getTree();
    }

    /**
     * 메뉴 순서 이동
     */
    @RequestMapping(value="/member/company/orderUpdate.json")
    @ResponseBody
    public List<Map<String, Object>> orderUpdate(@RequestBody List<Map<String, Object>> menuArr){

        int sort = 0;
        for(Map<String, Object> m : menuArr){

            String parentId = m.get("parent").toString();
            String id = m.get("id").toString();
            String title = m.get("text").toString();
            if("#".equals(parentId)) parentId = null;

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", id);
            param.put("parentId", parentId);
            param.put("title", title);
            param.put("sort", sort++);
            param.put("fstRgDt", new Date());
            param.put("lstChDt", new Date());

            int menuIdCnt = this.companyService.checkMenu(id);

            TraceLog.info("==company check="+menuIdCnt);
            if(menuIdCnt == 0){
                TraceLog.info("==company insert=");
                this.companyService.orderInsert(param);
            }else{
                TraceLog.info("==company update=");
                this.companyService.orderUpdate(param);
            }

        }

        return getTree();
    }

    /**
     * 메뉴 삭제
     */
    @RequestMapping("/member/company/delete.json")
    @ResponseBody
    public String deleteMenu(@RequestParam("id") String menuId) {

        int id = 0;
        if (isStringDouble(menuId)) id = Integer.parseInt(menuId);

        Map<String, Object> menu = this.companyService.getMenu(id);

        if (menu != null) {
            this.companyService.deleteMenu(id);
            TraceLog.info("delete cms menu [id:%d, url:%s]", menu.get("id"), menu.get("url"));
        } else {
            TraceLog.info("not exist delete cms menu [id:%d]", id);
        }

        return id+"";
    }

    /**
     * 숫자 체크 함수
     * @param s
     * @return
     */
    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
