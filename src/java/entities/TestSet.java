/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ZXNIKIC
 */
@Entity
@Table(name = "testset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestSet.findAll", query = "SELECT t FROM TestSet t")
    , @NamedQuery(name = "TestSet.findById", query = "SELECT t FROM TestSet t WHERE t.id = :id")
    , @NamedQuery(name = "TestSet.findByName", query = "SELECT t FROM TestSet t WHERE t.name = :name")
    , @NamedQuery(name = "TestSet.findByDateCreated", query = "SELECT t FROM TestSet t WHERE t.dateCreated = :dateCreated")
    , @NamedQuery(name = "TestSet.findByDateModified", query = "SELECT t FROM TestSet t WHERE t.dateModified = :dateModified")})
public class TestSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_modified")
    @Temporal(TemporalType.DATE)
    private Date dateModified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testSet", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SpecificStep> specificStepList = new ArrayList<>();

    public TestSet() {
    }

    public TestSet(Integer id) {
        this.id = id;
    }

    public TestSet(Integer id, String name, Date dateCreated, Date dateModified) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @XmlTransient
    public List<SpecificStep> getSpecificStepList() {
        return specificStepList;
    }

    public void setSpecificStepList(List<SpecificStep> specificStepList) {
        this.specificStepList = specificStepList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestSet)) {
            return false;
        }
        TestSet other = (TestSet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TestSet[ id=" + id + " ]";
    }
    
}
