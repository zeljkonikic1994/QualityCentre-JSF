/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ZXNIKIC
 */
@Embeddable
public class StepPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "step_id")
    private int stepId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_id")
    private int testId;

    public StepPK() {
    }

    public StepPK(int stepId, int testId) {
        this.stepId = stepId;
        this.testId = testId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) stepId;
        hash += (int) testId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StepPK)) {
            return false;
        }
        StepPK other = (StepPK) object;
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
        return "entities.StepPK[ stepId=" + stepId + ", testId=" + testId + " ]";
    }
    
}
