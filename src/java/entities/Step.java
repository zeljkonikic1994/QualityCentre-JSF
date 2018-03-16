/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 *
 * @author ZXNIKIC
 */
@Embeddable
public class Step implements Serializable {

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EXPECTED")
    private String expected;

    public Step() {
    }

    public Step(String name, String description, String expected) {
        this.name = name;
        this.description = description;
        this.expected = expected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "Step{" + "name=" + name + ", description=" + description + ", expected=" + expected + '}';
    }
}
