/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.CompletionStatus;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class CompletionStatusService {
    private static CompletionStatusDao completionStatusDao;

    public CompletionStatusService() {
        this.completionStatusDao = new CompletionStatusDao();
    }
    
    public List<CompletionStatus> findAll(){
        completionStatusDao.openCurrentSession();
        List<CompletionStatus> statuses = completionStatusDao.findAll();
        completionStatusDao.closeCurrentSession();
        return statuses;
    }
    public CompletionStatus findByName(String name) {
        completionStatusDao.openCurrentSession();
        CompletionStatus status = completionStatusDao.findByName(name);
        completionStatusDao.closeCurrentSession();
        return status;
    }
}
