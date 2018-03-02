/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZXNIKIC
 */
public class Folder implements Serializable{
    private String name;
    private List<Step> stepList=new ArrayList<>();
    
    public Folder() {
    }

    public Folder(String name) {
        this.name = name;
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
    
    public void addStep(Step s){
        this.stepList.add(s);
    }
    public void addSteps(List<Step> steps){
        this.stepList.addAll(steps);
    }

    public void removeStep(Step step) {
        stepList.remove(step);
    }

    @Override
    public String toString() {
        return "Folder{" + "name=" + name + ", stepList=" + stepList + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    
    
    
    
}
