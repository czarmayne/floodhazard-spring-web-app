package ws.floodhazard.service;

import ws.floodhazard.integration.entity.SensitivityCondition;

import java.util.List;

public interface PrecipitationConditionService {

    String addPrecipitationCondition(SensitivityCondition item);
    List<SensitivityCondition> getAllPrecipitationCondition();
    void deleteSensitivityCondition(String id);
    void initializeSensitivityConditions();
}

