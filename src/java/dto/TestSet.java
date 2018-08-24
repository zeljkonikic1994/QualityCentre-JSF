/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import constants.Constants;
import dto.Folder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestSet implements Serializable {

    private String name;
    private Date dateCreated;
    private Date dateModified;
    private List<Folder> folderList = new ArrayList<>();
    private String modifiedBy;
    
    private int testSetId;

    public TestSet(int testSetId, String name, Date dateCreated, Date dateModified) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.testSetId = testSetId;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getTestSetId() {
        return testSetId;
    }

    public void setTestSetId(int testSetId) {
        this.testSetId = testSetId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public TestSet(String name) {
        this.name = name;
    }

    public TestSet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }

    public void addFolder(Folder folder) {
        folderList.add(folder);
    }

    public void removeFolder(Folder selectedDestinationFolder) {
        folderList.remove(selectedDestinationFolder);
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "TestSet{" + "name=" + name + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", folderList=" + folderList + ", testSetId=" + testSetId + '}';
    }
    
    public int getStatus(){
        int noRun = 0;
        int failed = 0;
        int passed = 0;
        int notCompleted = 0;
        for (Folder folder : folderList) {
            switch(folder.getStatus()){
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
        if(noRun == folderList.size())
            return Constants.NO_RUN;
        if(noRun > 0 )
            return Constants.NOT_COMPLETED;
        if(notCompleted > 0)
            return Constants.NOT_COMPLETED;
        if(passed == folderList.size())
            return Constants.PASSED;
        if(failed > 0 && noRun ==0 && notCompleted == 0)
            return Constants.FAILED;
        
        return -1;
    }

    public int getNumberOfStepsWithStatus(int status) {
        List<Step> steps = new ArrayList<>();
        for(Folder f : folderList){
            steps.addAll(f.getStepList());
        }
        return (int) steps.stream().filter(s -> s.getStatusId() == status).count();
    }
}
