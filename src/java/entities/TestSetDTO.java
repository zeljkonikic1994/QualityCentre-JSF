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

/**
 *
 * @author ZXNIKIC
 */
public class TestSetDTO implements Serializable{
    private String name;
    private List<Folder> folderList = new ArrayList<>();
    private Date dateCreated;
    private int testSetId;

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
    
    public TestSetDTO(String name) {
        this.name = name;
    }

    public TestSetDTO() {
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
