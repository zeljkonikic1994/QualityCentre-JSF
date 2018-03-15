/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.SpecificStep;
import entities.Step;
import entities.Test;
import entities.TestSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class EntityHelper {

    public static List<dto.Step> convertFromStepList(List<Step> stepList) {
        List<dto.Step> dtos = new ArrayList<>();
        for (Step step : stepList) {
            dto.Step dto = StepConverter.convertFrom(step);
            dto.setTest(TestConverter.convertFrom(step.getTest()));
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<Step> convertToStepList(List<dto.Step> stepList) {
        List<Step> entities = new ArrayList<>();
        for (dto.Step step : stepList) {
            Step entity = StepConverter.convertTo(step);
            entity.setTest(TestConverter.convertTo(step.getTest()));
            entities.add(entity);
        }
        return entities;
    }

    public static List<dto.Test> convertFromTestList(List<Test> testList) {
        List<dto.Test> dtos = new ArrayList<>();
        for (Test test : testList) {
            dto.Test testDto = TestConverter.convertFrom(test);
            for (Step step : test.getStepList()) {
                dto.Step stepDto = StepConverter.convertFrom(step);
                stepDto.setTest(testDto);
                testDto.addStep(stepDto);
            }
            dtos.add(testDto);
        }
        return dtos;
    }

    public static List<Test> convertToTestList(List<dto.Test> testList) {
        List<Test> entities = new ArrayList<>();
        for (dto.Test testDto : testList) {
            Test test = TestConverter.convertTo(testDto);
            for (dto.Step stepDTO : testDto.getStepList()) {
                Step step = StepConverter.convertTo(stepDTO);
                step.setTest(test);
                test.addStep(step);
            }
            entities.add(test);
        }
        return entities;
    }

    public static List<dto.TestSet> convertFromTestSetList(List<TestSet> testSetList) {
        List<dto.TestSet> dtos = new ArrayList<>();
        for (TestSet testSet : testSetList) {
            dtos.add(TestSetConverter.convertFromTestSet(testSet));
        }
        return dtos;
    }

}
