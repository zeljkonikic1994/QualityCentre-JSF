/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ZXNIKIC
 */
@Embeddable
public class SpecificStep implements Serializable {

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "expected")
    private String expected;
    @Column(name = "folder")
    private String folder;

    @JoinColumn(name="status_id", referencedColumnName = "status_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompletionStatus completionStatus;
    
    public SpecificStep() {
    }


    public SpecificStep(String newName, String description, String expected, String folder) {
        this.name = newName;
        this.description = description;
        this.expected = expected;
        this.folder = folder;
        this.completionStatus = new CompletionStatus(3, "No run");
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

    @Override
    public String toString() {
        return "SpecificStep{" + "name=" + name + ", description=" + description + ", expected=" + expected + ", folder=" + folder + ", completionStatus=" + completionStatus + '}';
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }
}
