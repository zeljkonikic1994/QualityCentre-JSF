/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import constants.Constants;
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
    private List<Step> stepList = new ArrayList<>();

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

    public void addStep(Step step) {
        this.stepList.add(step);
    }

    public void addSteps(List<Step> list) {
        this.stepList.addAll(list);
    }

    public void removeStep(Step step) {
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
        final Folder other = (Folder) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FolderDTO{" + "name=" + name + ", stepList=" + stepList + '}';
    }
    
    public int getStatus(){
        int noRun = 0;
        int failed = 0;
        int passed = 0;
        int notCompleted = 0;
        
        for (Step step : stepList) {
            switch(step.getStatusId()){
                case Constants.FAILED:
                    failed++;
                    break;
                case Constants.NOT_COMPLETED:
                    notCompleted++;
                    break;
                case Constants.NO_RUN:
                    noRun++;
                    break;
                case Constants.PASSED:
                    passed++;
                    break;
                default:
            }
        }
        if(noRun == stepList.size())
            return Constants.NO_RUN;
        if(noRun > 0 )
            return Constants.NOT_COMPLETED;
        if(notCompleted > 0)
            return Constants.NOT_COMPLETED;
        if(passed == stepList.size())
            return Constants.PASSED;
        if(failed > 0 && noRun ==0 && notCompleted == 0)
            return Constants.FAILED;
        return -1;
    }
    
    

}
