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
@Table(name = "specificstep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpecificStep.findAll", query = "SELECT s FROM SpecificStep s")
    , @NamedQuery(name = "SpecificStep.findById", query = "SELECT s FROM SpecificStep s WHERE s.specificStepPK.id = :id")
    , @NamedQuery(name = "SpecificStep.findByTestSetId", query = "SELECT s FROM SpecificStep s WHERE s.specificStepPK.testSetId = :testSetId")
    , @NamedQuery(name = "SpecificStep.findByName", query = "SELECT s FROM SpecificStep s WHERE s.name = :name")
    , @NamedQuery(name = "SpecificStep.findByDescription", query = "SELECT s FROM SpecificStep s WHERE s.description = :description")
    , @NamedQuery(name = "SpecificStep.findByExpected", query = "SELECT s FROM SpecificStep s WHERE s.expected = :expected")
    , @NamedQuery(name = "SpecificStep.findByFolder", query = "SELECT s FROM SpecificStep s WHERE s.folder = :folder")})
public class SpecificStep implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SpecificStepPK specificStepPK;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "folder")
    private String folder;
    @JoinColumn(name = "test_set_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TestSet testSet;

    public SpecificStep() {
    }

    public SpecificStep(SpecificStepPK specificStepPK) {
        this.specificStepPK = specificStepPK;
    }

    public SpecificStep(SpecificStepPK specificStepPK, String name, String description, String expected, String folder) {
        this.specificStepPK = specificStepPK;
        this.name = name;
        this.description = description;
        this.expected = expected;
        this.folder = folder;
    }

    public SpecificStep(int id, int testSetId) {
        this.specificStepPK = new SpecificStepPK(id, testSetId);
    }

    public SpecificStepPK getSpecificStepPK() {
        return specificStepPK;
    }

    public void setSpecificStepPK(SpecificStepPK specificStepPK) {
        this.specificStepPK = specificStepPK;
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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public TestSet getTestSet() {
        return testSet;
    }

    public void setTestSet(TestSet testSet) {
        this.testSet = testSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specificStepPK != null ? specificStepPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpecificStep)) {
            return false;
        }
        SpecificStep other = (SpecificStep) object;
        if ((this.specificStepPK == null && other.specificStepPK != null) || (this.specificStepPK != null && !this.specificStepPK.equals(other.specificStepPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SpecificStep[ specificStepPK=" + specificStepPK + " ]";
    }

}
