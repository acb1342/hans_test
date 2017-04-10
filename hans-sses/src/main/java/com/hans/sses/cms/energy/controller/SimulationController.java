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
    public ModelAndView simylation(@RequestParam(value = "searchType", required = false) String searchType,
                                   @RequestParam(value = "searchValue", required = false) String searchValue) {
        ModelAndView mav = new ModelAndView("energy/simulation");
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("searchType", searchType);
        param.put("searchValue", searchValue);


        return mav;
    }

    /**
     * 시뮬레이션 차트 만들기
     */
    @RequestMapping(value = "/energy/simulation/status.json", method = RequestMethod.GET)
    public JSONObject getEnergy(
            @RequestParam Map<String, Object> params) {

        JSONObject joStat =  new JSONObject();
        double[] dualWList;    // 그룹 별 총 전력량 배열

        List<Map<String, Object>> list = this.simulationService.getEnergyList(params);

        dualWList = getSimulationData(list);

        joStat.put("series", list);
        joStat.put("data", dualWList);
        joStat.put("selectValue", params.get("selectValue"));

        System.out.println("JSON = " + joStat);

        return joStat;
    }

    /**
     * 에너지 계산
     */

    public double[] getSimulationData(List<Map<String, Object>> list){

        double[] dualWList = new double[list.size()];

        for(int i=0; i < list.size(); i++){
            double dualW = 0;   // 총 전력량
            String[] watt;      // watt 배열
            String[] event_type;     // event_type 배열

            // 값이 하나인 경우
            if(String.valueOf(list.get(i).get("wattList")).indexOf(";") < 0){
                watt = new String[1]; event_type = new String[1];
                watt[0] = String.valueOf(list.get(i).get("wattList"));
                event_type[0] = String.valueOf(list.get(i).get("eventList"));
            }
            else{
                watt = String.valueOf(list.get(i).get("wattList")).split(";");
                event_type = String.valueOf(list.get(i).get("eventList")).split(";");
            }

            for(int j=0; j<watt.length;j++){

                int wattJ = Integer.parseInt(watt[j]);

                // 0:전원 OFF,  1:전원 ON,  2:절약모드시작,  3:절약모드종료,  4:사용중

                if(event_type[j].equals("1")){
                    dualW += wattJ;
                }
                else if(event_type[j].equals("0")){
                    dualW -= wattJ;
                }
                else if(event_type[j].equals("2")){
                    dualW -= wattJ-1;
                }
                else if(event_type[j].equals("3")){
                    dualW += wattJ-1;
                }
            }
            dualWList[i]=dualW;
        }


        return dualWList;

    }
}
