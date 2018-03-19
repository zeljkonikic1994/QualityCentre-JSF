/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class Test implements Serializable{

    private int testId;
    private Date dateCreated;
    private String name;
    private List<Step> stepList = new ArrayList<>();

    public Test(int testId) {
        this.testId = testId;
    }

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

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public Test(int testId, Date dateCreated, String name) {
        this.testId = testId;
        this.dateCreated = dateCreated;
        this.name = name;
    }

    public Test(int testId, Date dateCreated, String name, List<Step> stepList) {
        this.testId = testId;
        this.dateCreated = dateCreated;
        this.name = name;
        this.stepList = stepList;
    }

    public Test() {
    }

    public void addStep(Step step) {
        this.stepList.add(step);
    }

    public void removeStep(Step step) {
        this.stepList.remove(step);
    }

    @Override
    public String toString() {
        return "Test{" + "testId=" + testId + ", dateCreated=" + dateCreated + ", name=" + name + ", stepList=" + stepList + '}';
    }

}
