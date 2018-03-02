/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ZXNIKIC
 */
@Entity
@Table(name = "step")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Step.findAll", query = "SELECT s FROM Step s")
    , @NamedQuery(name = "Step.findByStepId", query = "SELECT s FROM Step s WHERE s.stepPK.stepId = :stepId")
    , @NamedQuery(name = "Step.findByTestId", query = "SELECT s FROM Step s WHERE s.stepPK.testId = :testId")
    , @NamedQuery(name = "Step.findByName", query = "SELECT s FROM Step s WHERE s.name = :name")
    , @NamedQuery(name = "Step.findByDescription", query = "SELECT s FROM Step s WHERE s.description = :description")
    , @NamedQuery(name = "Step.findByExpected", query = "SELECT s FROM Step s WHERE s.expected = :expected")})
public class Step implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StepPK stepPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "expected")
    private String expected;
    
    @JoinColumn(name = "test_id", referencedColumnName = "test_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Test test;

    public Step() {
    }

    public Step(StepPK stepPK) {
        this.stepPK = stepPK;
    }

    public Step(StepPK stepPK, String name, String description, String expected) {
        this.stepPK = stepPK;
        this.name = name;
        this.description = description;
        this.expected = expected;
    }

    public Step(int stepId, int testId) {
        this.stepPK = new StepPK(stepId, testId);
    }

    public StepPK getStepPK() {
        return stepPK;
    }

    public void setStepPK(StepPK stepPK) {
        this.stepPK = stepPK;
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
        int hash = 0;
        hash += (stepPK != null ? stepPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Step)) {
            return false;
        }
        Step other = (Step) object;
        if ((this.stepPK == null && other.stepPK != null) || (this.stepPK != null && !this.stepPK.equals(other.stepPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Step{" + "stepPK=" + stepPK + ", name=" + name + ", description=" + description + ", expected=" + expected + ", test=" + test + '}';
    }

    
    
}
