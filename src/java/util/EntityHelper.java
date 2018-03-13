/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.StepDTO;
import dto.TestDTO;
import entities.Step;
import entities.Test;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class EntityHelper {

    public static List<StepDTO> convertFromStepList(List<Step> stepList) {
        List<StepDTO> dtos = new ArrayList<>();
        for (Step step : stepList) {
            StepDTO dto = StepConverter.convertFrom(step);
            dto.setTest(TestConverter.convertFrom(step.getTest()));
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<Step> convertToStepList(List<StepDTO> stepList) {
        List<Step> entities = new ArrayList<>();
        for (StepDTO step : stepList) {
            Step entity = StepConverter.convertTo(step);
            entity.setTest(TestConverter.convertTo(step.getTest()));
            entities.add(entity);
        }
        return entities;
    }

    public static List<TestDTO> convertFromTestList(List<Test> testList) {
        List<TestDTO> dtos = new ArrayList<>();
        for (Test test : testList) {
            TestDTO testDto = TestConverter.convertFrom(test);
            for (Step step : test.getStepList()) {
                StepDTO stepDto = StepConverter.convertFrom(step);
                stepDto.setTest(testDto);
                testDto.addStep(stepDto);
            }
            dtos.add(testDto);
        }
        return dtos;
    }

    public static List<Test> convertToTestList(List<TestDTO> testList) {
        List<Test> entities = new ArrayList<>();
        for (TestDTO testDto : testList) {
            Test test = TestConverter.convertTo(testDto);
            for (StepDTO stepDTO : testDto.getStepList()) {
                Step step = StepConverter.convertTo(stepDTO);
                step.setTest(test);
                test.addStep(step);
            }
            entities.add(test);
        }
        return entities;
    }
}
