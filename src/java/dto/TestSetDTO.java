/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Folder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetDTO implements Serializable {

    private String name;
    private List<Folder> folderList = new ArrayList<>();
    private Date dateCreated;
    private Date dateModified;
    private List<FolderDTO> folderDtoList = new ArrayList<>();

    private int testSetId;

    public TestSetDTO(int testSetId, String name, Date dateCreated, Date dateModified) {
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
    
    public void setFolderDtoList(List<FolderDTO> folderList){
        this.folderDtoList = folderList;
    }

    public List<FolderDTO> getFolderDtoList() {
        return folderDtoList;
    }
    
    public void addFolderDto(FolderDTO folder){
        this.folderDtoList.add(folder);
    }
    public void removeFolder(FolderDTO folder){
        this.folderDtoList.remove(folder);
    }
    
}
