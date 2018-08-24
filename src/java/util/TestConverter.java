/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Step;
import entities.Test;
import java.util.Map;

/**
 *
 * @author ZXNIKIC
 */
public class TestConverter {

    public static dto.Test convertFrom(Test test) {
        dto.Test testDto = new dto.Test(test.getTestId(), test.getDateCreated(), test.getName());
        testDto.setModifiedBy(test.getModifiedBy());
        for (Map.Entry<Integer, Step> step : test.getSteps().entrySet()) {
            dto.Step stepDto = new dto.Step(step.getKey(), test.getTestId(), step.getValue().getName(), step.getValue().getDescription(), step.getValue().getExpected());
            stepDto.setTest(testDto);
            testDto.addStep(stepDto);
        }
        return testDto;
    }

    public static Test convertTo(dto.Test test) {
        Test te = new Test(test.getTestId(), test.getDateCreated(), test.getName());
        te.setModifiedBy(test.getModifiedBy());
        for (dto.Step stepDto : test.getStepList()) {
            Step step = new Step(stepDto.getName(), stepDto.getDescription(), stepDto.getExpected());
            te.addStep(step, stepDto.getStepId());
        }
        return te;
    }
}
