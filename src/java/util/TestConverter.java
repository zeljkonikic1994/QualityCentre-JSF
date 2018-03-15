/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Step;
import entities.Test;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestConverter {

    public static dto.Test convertFrom(Test test) {
        return new dto.Test(test.getTestId(), test.getDateCreated(), test.getName());
    }

    public static Test convertTo(dto.Test test) {
        return new Test(test.getTestId(), test.getDateCreated(), test.getName());
    }
}
