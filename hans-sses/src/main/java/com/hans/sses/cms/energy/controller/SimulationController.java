package com.hans.sses.cms.energy.controller;

import com.hans.sses.energy.service.SimulationService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 4. 10..
 */
@Controller
public class SimulationController {

    SimulationService simulationService;
    /**
     * 시뮬레이션 화면
     */

    @RequestMapping("/energy/simulation.htm")
    public ModelAndView simulation(@RequestParam(value = "selectDay", required = false) String selectDay,
                                   @RequestParam(value = "selectHour", required = false) String selectHour,
                                   @RequestParam(value = "selectCharge", required = false) String selectCharge) {
        ModelAndView mav = new ModelAndView("energy/simulation");
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("selectDay", selectDay);
        param.put("selectHour", selectHour);
        param.put("selectCharge", selectCharge);

        return mav;
    }

    /**
     * 시뮬레이션 차트 만들기
     */
    @RequestMapping(value = "/energy/simulation/status.json", method = RequestMethod.GET)
    public JSONObject getEnergy(
            @RequestParam Map<String, Object> params) {

        //double dualW = 0;			// 총 전력량
        String charge = params.get("selectCharge").toString();			    // 전력요금
        String savingDay = params.get("selectDay").toString();			    // 절약 기간 selectDay * 24 * 60 * 60
        String savingtime = params.get("selectHour").toString();		    // 절약 시간 selectHour * 60 * 60


        JSONObject joStat =  new JSONObject();

        double arr1 = Integer.parseInt(savingDay) * 24 * Double.parseDouble(charge);
        double arr2 = Integer.parseInt(savingDay) * (24  - Integer.parseInt(savingtime)) * Double.parseDouble(charge);

        double arr3 = arr1 * 500 /3600.0/1000.0;
        double arr4 = arr2 * 500 /3600.0/1000.0;

        int arr11 = (int) arr1 /10 * 10;
        int arr22 = (int) arr2 /10 * 10;
        double arr33 = Double.parseDouble(String.format("%.4f" , arr3));
        double arr44 = Double.parseDouble(String.format("%.4f" , arr4));
        double[] dualWList = {arr11,arr22, arr33, arr44};                                        // 미적용요금, 적용요금


//        Map<String, Object> list = null;
//        list.put("savingDay", params.get("selectDay"));
//        list.put("savingHour", params.get("selectHour"));


//        joStat.put("series", list);
        joStat.put("data", dualWList);
        joStat.put("selectDay", savingDay);
        joStat.put("selectHour", savingtime);
        joStat.put("selectCharge", charge);

        System.out.println("JSON = " + joStat);

        return joStat;
    }

}
