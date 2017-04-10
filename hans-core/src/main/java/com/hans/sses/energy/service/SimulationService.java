package com.hans.sses.energy.service;

import java.util.List;
import java.util.Map;

/**
 * Created by leogon on 2017. 4. 10..
 */
public interface SimulationService {
    List<Map<String,Object>> getEnergyList(Map<String, Object> params);
}
