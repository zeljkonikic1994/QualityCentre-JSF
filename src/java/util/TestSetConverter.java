/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.FolderDTO;
import dto.StepDTO;
import dto.TestSetDTO;
import entities.SpecificStep;
import entities.TestSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetConverter {

    public static TestSetDTO convertFromTestSet(TestSet testSet) {
        TestSetDTO dto = new TestSetDTO(testSet.getId(), testSet.getName(), testSet.getDateCreated(), testSet.getDateModified());

        List<FolderDTO> folderList = new ArrayList<>();
        for (SpecificStep ss : testSet.getSpecificStepList()) {
            FolderDTO folder = findFolderByName(folderList, ss.getFolder());
            if (folder == null) {
                folder = new FolderDTO(ss.getFolder());
                folderList.add(folder);
            }
            StepDTO stepDto = new StepDTO(ss.getSpecificStepPK().getId(), ss.getSpecificStepPK().getTestSetId(), ss.getName(), ss.getDescription(), ss.getExpected());
            stepDto.setTest(null);
            folder.addStep(stepDto);
        }
        dto.setFolderDtoList(folderList);
        return dto;
    }

    private static FolderDTO findFolderByName(List<FolderDTO> folderList, String folderName) {
        for (FolderDTO folder : folderList) {
            if (folder.getName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }

    public static TestSet convertToTestSet(TestSetDTO dto) {
        TestSet entity = new TestSet(dto.getTestSetId(), dto.getName(), dto.getDateCreated(), dto.getDateModified());
        int stepCount = 1;
        for (FolderDTO folder : dto.getFolderDtoList()) {
            for (StepDTO step : folder.getStepList()) {
                SpecificStep ss = new SpecificStep(stepCount++, dto.getTestSetId(), step.getName(), step.getDescription(), step.getExpected(), folder.getName(), entity);
                entity.addSpecificStep(ss);
            }
        }
        return entity;
    }
}
