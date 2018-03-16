/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Step;
import entities.StepPK;

/**
 *
 * @author ZXNIKIC
 */
public class StepConverter {

    public static dto.Step convertFrom(int stepId, Step step, int testId) {
        return new dto.Step(stepId, testId, step.getName(), step.getDescription(), step.getExpected());
    }

    public static Step convertTo(dto.Step step) {
        return new Step(step.getName(), step.getDescription(), step.getExpected());
    }
}
