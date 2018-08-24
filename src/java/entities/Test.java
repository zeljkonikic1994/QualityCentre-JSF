/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author ZXNIKIC
 */
@Entity
@Table(name = "test")
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_ID")
    private Integer testId;

    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "NAME")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "STEP", joinColumns = {@JoinColumn(name = "TEST_ID")})
    @MapKeyColumn(name = "STEP_ID")
    private Map<Integer, Step> steps = new HashMap<>();

    @Column(name="LAST_MODIFIED_BY")
    private String modifiedBy;
    
    public Test() {
    }

    public Test(Integer testId) {
        this.testId = testId;
    }

    public Test(Integer testId, Date dateCreated, String name) {
        this.testId = testId;
        this.dateCreated = dateCreated;
        this.name = name;
    }
    
    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
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

    public Map<Integer, Step> getSteps() {
        return steps;
    }

    public void setSteps(Map<Integer, Step> steps) {
        this.steps = steps;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        final Test other = (Test) obj;
        if (!Objects.equals(this.testId, other.testId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Test{" + "testId=" + testId + ", dateCreated=" + dateCreated + ", name=" + name + ", steps=" + steps + ", createdBy=" + modifiedBy + '}';
    }

    
    public void addStep(Step step, int stepId){
        this.steps.put(stepId,step);
    }
    
}
