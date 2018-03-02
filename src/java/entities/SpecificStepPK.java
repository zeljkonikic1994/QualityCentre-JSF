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
public class SpecificStepPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test_set_id")
    private int testSetId;

    public SpecificStepPK() {
    }

    public SpecificStepPK(int id, int testSetId) {
        this.id = id;
        this.testSetId = testSetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestSetId() {
        return testSetId;
    }

    public void setTestSetId(int testSetId) {
        this.testSetId = testSetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) testSetId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpecificStepPK)) {
            return false;
        }
        SpecificStepPK other = (SpecificStepPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.testSetId != other.testSetId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SpecificStepPK[ id=" + id + ", testSetId=" + testSetId + " ]";
    }

    
    
}
