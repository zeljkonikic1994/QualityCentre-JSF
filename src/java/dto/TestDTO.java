/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestDTO {

    private int testId;
    private Date dateCreated;
    private String name;
    private List<StepDTO> stepList = new ArrayList<>();

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StepDTO> getStepList() {
        return stepList;
    }

    public void setStepList(List<StepDTO> stepList) {
        this.stepList = stepList;
    }

    public TestDTO(int testId, Date dateCreated, String name) {
        this.testId = testId;
        this.dateCreated = dateCreated;
        this.name = name;
    }

    public TestDTO(int testId, Date dateCreated, String name, List<StepDTO> stepList) {
        this.testId = testId;
        this.dateCreated = dateCreated;
        this.name = name;
        this.stepList = stepList;
    }

    public TestDTO() {
    }

    public void addStep(StepDTO step) {
        this.stepList.add(step);
    }

}
