/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZXNIKIC
 */
public class FolderDTO implements Serializable{

    private String name;
    private List<StepDTO> stepList = new ArrayList<>();

    public FolderDTO() {
    }

    public FolderDTO(String name) {
        this.name = name;
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

    public void addStep(StepDTO step) {
        this.stepList.add(step);
    }

    public void addSteps(List<StepDTO> list) {
        this.stepList.addAll(list);
    }

    public void removeStep(StepDTO step) {
        this.stepList.remove(step);
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
        final FolderDTO other = (FolderDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FolderDTO{" + "name=" + name + ", stepList=" + stepList + '}';
    }
    
    

}
