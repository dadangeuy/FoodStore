package com.foodstore.service;

import com.foodstore.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml", "/spring-mvc.xml", "/spring-shiro-web.xml"})
@WebAppConfiguration
public class UserServiceTests {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private UserService service;

    @Before
    public void reset() {
        template.update("delete from sys_users");
        template.update("delete from sys_users_roles");
    }

    @Test
    public void test_createUser_expectCreated() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        Assert.assertNotNull(createdUser);
    }

    @Test
    public void test_updateUser_expectUpdated() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        createdUser.setUsername("Updated Username");
        User updatedUser = service.updateUser(createdUser);
        Assert.assertNotNull(updatedUser);
    }

    @Test
    public void test_deleteUser_expectDeleted() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        service.deleteUser(createdUser.getUsername());
    }

    @Test
    public void test_changePassword_expectChanged() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        User changedUser = service.changePassword(createdUser.getUsername(), "Changed Password");
        Assert.assertNotNull(changedUser);
    }

    @Test
    public void test_correlationRoles_expectCorrelated() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        service.correlationRoles(createdUser.getId());
    }

    @Test
    public void test_uncorrelationRoles_expectUncorrelated() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        service.uncorrelationRoles(createdUser.getId());
    }

    @Test
    public void test_findByUsername_expectFound() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        User foundedUser = service.findByUsername(createdUser.getUsername());
        Assert.assertNotNull(foundedUser);
    }

    @Test
    public void test_findRoles_expectFound() {
        User user = createSampleUser();
        User createdUser = service.createUser(user);
        Set<String> roles = service.findRoles(createdUser.getUsername());
        Assert.assertTrue(roles.isEmpty());
    }

    private User createSampleUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("Sample Username");
        user.setPassword("Sample Password");
        user.setSalt("Sample Salt");
        return user;
    }
}
