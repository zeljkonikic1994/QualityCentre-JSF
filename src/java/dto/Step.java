/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author ZXNIKIC
 */
public class Step implements Serializable{

    private int stepId;
    private int testId;
    private String name;
    private String description;
    private String expected;
    private Test test;

    public Step(int stepId, int testId, String name, String description, String expected, Test test) {
        this.stepId = stepId;
        this.testId = testId;
        this.name = name;
        this.description = description;
        this.expected = expected;
        this.test = test;
    }

    public Step(int stepId, int testId, String name, String description, String expected) {
        this.stepId = stepId;
        this.testId = testId;
        this.name = name;
        this.description = description;
        this.expected = expected;
    }

    public Step() {
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Step other = (Step) obj;
        if (this.stepId != other.stepId) {
            return false;
        }
        if (this.testId != other.testId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StepDTO{" + "stepId=" + stepId + ", testId=" + testId + ", name=" + name + '}';
    }

}
