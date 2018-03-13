/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.StepDTO;
import entities.Step;
import entities.StepPK;

/**
 *
 * @author ZXNIKIC
 */
public class StepConverter {

    public static StepDTO convertFrom(Step step) {
        return new StepDTO(step.getStepPK().getStepId(), step.getStepPK().getTestId(), step.getName(), step.getDescription(), step.getExpected());
    }

    public static Step convertTo(StepDTO step) {
        return new Step(new StepPK(step.getStepId(), step.getTestId()), step.getName(), step.getDescription(), step.getExpected());
    }
}
