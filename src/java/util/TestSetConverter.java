/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.Folder;
import entities.CompletionStatus;
import entities.SpecificStep;
import entities.TestSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetConverter {

    public static dto.TestSet convertFromTestSet(TestSet testSet) {
        dto.TestSet dto = new dto.TestSet(testSet.getId(), testSet.getName(), testSet.getDateCreated(), testSet.getDateModified());

        List<Folder> folderList = new ArrayList<>();
        for (Map.Entry<Integer, SpecificStep> entry : testSet.getSteps().entrySet()) {
            Folder folder = findFolderByName(folderList, entry.getValue().getFolder());
            if (folder == null) {
                folder = new Folder(entry.getValue().getFolder());
                folderList.add(folder);
            }
            dto.Step stepDto = new dto.Step(entry.getKey(), testSet.getId(), entry.getValue().getName(), entry.getValue().getDescription(), entry.getValue().getExpected());
            stepDto.setStatusId(entry.getValue().getCompletionStatus().getId());
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
        entity.setModifiedBy(dto.getModifiedBy());
        for (Folder folder : dto.getFolderList()) {
            for (dto.Step step : folder.getStepList()) {
                SpecificStep ss = new SpecificStep(step.getName(), step.getDescription(), step.getExpected(), folder.getName());
                ss.setCompletionStatus(new CompletionStatus(step.getStatusId(), ""));
                if (!entity.getSteps().containsKey(step.getStepId())) {
                    entity.addStep(step.getStepId(), ss);
                } else {
                    ArrayList<Integer> ints = new ArrayList<>(entity.getSteps().keySet());
                    Collections.sort(ints, (one, two) -> one < two ? -1 : one == two ? 0 : 1);
                    entity.addStep(ints.get(ints.size()-1)+1, ss);
                }
            }
        }
        return entity;
    }
}
