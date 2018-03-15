/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

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
    
}
