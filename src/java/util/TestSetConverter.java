/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.Folder;
import entities.SpecificStep;
import entities.TestSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetConverter {

    public static dto.TestSet convertFromTestSet(TestSet testSet) {
        dto.TestSet dto = new dto.TestSet(testSet.getId(), testSet.getName(), testSet.getDateCreated(), testSet.getDateModified());

        List<Folder> folderList = new ArrayList<>();
        for (SpecificStep ss : testSet.getSpecificStepList()) {
            Folder folder = findFolderByName(folderList, ss.getFolder());
            if (folder == null) {
                folder = new Folder(ss.getFolder());
                folderList.add(folder);
            }
            dto.Step stepDto = new dto.Step(ss.getSpecificStepPK().getId(), ss.getSpecificStepPK().getTestSetId(), ss.getName(), ss.getDescription(), ss.getExpected());
            stepDto.setTest(null);
            folder.addStep(stepDto);
        }
        dto.setFolderList(folderList);
        return dto;
    }

    private static Folder findFolderByName(List<Folder> folderList, String folderName) {
        for (Folder folder : folderList) {
            if (folder.getName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }

    public static TestSet convertToTestSet(dto.TestSet dto) {
        TestSet entity = new TestSet(dto.getTestSetId(), dto.getName(), dto.getDateCreated(), dto.getDateModified());
        int stepCount = 1;
        for (Folder folder : dto.getFolderList()) {
            for (dto.Step step : folder.getStepList()) {
                SpecificStep ss = new SpecificStep(stepCount++, dto.getTestSetId(), step.getName(), step.getDescription(), step.getExpected(), folder.getName(), entity);
                entity.addSpecificStep(ss);
            }
        }
        return entity;
    }
}
