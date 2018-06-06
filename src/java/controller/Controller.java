/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CompletionStatusService;
import dao.TestService;
import dao.TestSetService;
import dao.TypeService;
import dao.UserService;
import dto.Folder;
import dto.Step;
import entities.CompletionStatus;
import entities.Test;
import entities.TestSet;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import entities.Type;
import entities.User;
import java.util.List;
import util.EntityHelper;
import util.TestConverter;
import util.TestSetConverter;

/**
 *
 * @author zeljk
 */
@Named("Controller")
@ApplicationScoped
public class Controller implements Serializable {

    public Type getTypeByName(String name) {
        TypeService typeService = new TypeService();
        Type type = typeService.findByName(name);
        return type;
    }

    public User getUserWithSpecifiedUserName(String username) {
        UserService userService = new UserService();
        User user = userService.findById(username);
        return user;
    }

    public List<Type> getTypes() {
        TypeService typeService = new TypeService();
        List<Type> types = typeService.findAll();
        return types;
    }

    public void saveUser(User newUser) {
        UserService userService = new UserService();
        userService.save(newUser);
    }

    public void updateUser(User user) {
        UserService userService = new UserService();
        userService.update(user);
    }

    public List<User> getAllUsers() {
        UserService userService = new UserService();
        List<User> users = userService.findAll();
        return users;
    }

    public void deleteUser(String username) {
        UserService userService = new UserService();
        userService.delete(username);
    }

    public List<dto.Test> getTests() {
        return EntityHelper.convertFromTestList(loadTests());
    }

    private static List<Test> loadTests() {
        TestService testService = new TestService();
        List<entities.Test> testList = testService.findAll();
        return testList;
    }

    public void saveTest(dto.Test testDTO) {
        TestService testService = new TestService();
        testService.save(TestConverter.convertTo(testDTO));
    }

    public void updateTest(dto.Test testDTO) {
        TestService testService = new TestService();
        testService.update(TestConverter.convertTo(testDTO));
    }

    public void deleteTest(dto.Test selectedTest) {
        TestService testService = new TestService();
        testService.delete(selectedTest.getTestId());
    }

    public List<dto.TestSet> getSets() {
        return EntityHelper.convertFromTestSetList(loadSets());
    }

    private static List<TestSet> loadSets() {
        TestSetService testSetService = new TestSetService();
        List<TestSet> testSetList = testSetService.findAll();
        return testSetList;
    }

    public void saveSet(dto.TestSet selectedSet) {
        TestSetService testSetService = new TestSetService();
        testSetService.save(TestSetConverter.convertToTestSet(selectedSet));
    }

    public void deleteSet(dto.TestSet selectedSet) {
        TestSetService testSetService = new TestSetService();
        testSetService.delete(selectedSet.getTestSetId());
    }

    public void updateSet(dto.TestSet selectedSet) {
        TestSetService testSetService = new TestSetService();
        testSetService.update(TestSetConverter.convertToTestSet(selectedSet));
    }

    public Object getStatusByName(String value) {
        CompletionStatusService completionStatusService = new CompletionStatusService();
        CompletionStatus status = completionStatusService.findByName(value);
        return status;
    }

    public List<CompletionStatus> getAllStatuses() {
        CompletionStatusService completionStatusService = new CompletionStatusService();
        List<CompletionStatus> statuses = completionStatusService.findAll();
        return statuses;
    }


}
