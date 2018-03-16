/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Test;
import entities.TestSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class EntityHelper {

    public static List<dto.Test> convertFromTestList(List<Test> testList) {
        List<dto.Test> dtos = new ArrayList<>();
        for (Test test : testList) {
            dto.Test testDto = TestConverter.convertFrom(test);
            dtos.add(testDto);
        }
        return dtos;
    }

    public static List<Test> convertToTestList(List<dto.Test> testList) {
        List<Test> entities = new ArrayList<>();
        for (dto.Test testDto : testList) {
            Test test = TestConverter.convertTo(testDto);
            entities.add(test);
        }
        return entities;
    }

    public static List<dto.TestSet> convertFromTestSetList(List<TestSet> testSetList) {
        List<dto.TestSet> dtos = new ArrayList<>();
        for (TestSet testSet : testSetList) {
            dtos.add(TestSetConverter.convertFromTestSet(testSet));
        }
        return dtos;
    }

}
