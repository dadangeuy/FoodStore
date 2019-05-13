package com.foodstore.service;

import com.foodstore.entity.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml", "/spring-mvc.xml", "/spring-shiro-web.xml"})
@WebAppConfiguration
public class RoleServiceTests {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RoleService service;

    @Before
    public void reset() {
        template.update("delete from sys_roles");
    }

    @Test
    public void test_createRole_expectCreated() {
        Role role = createSampleRole();
        Role createdRole = service.createRole(role);
        Assert.assertNotNull(createdRole);
    }

    @Test
    public void test_deleteRole_expectDeleted() {
        Role role = createSampleRole();
        Role createdRole = service.createRole(role);
        service.deleteRole(createdRole.getId());
    }

    private Role createSampleRole() {
        Role role = new Role();
        role.setId(0L);
        role.setRole("Sample Role");
        role.setDescription("Sample Description");
        return role;
    }
}
