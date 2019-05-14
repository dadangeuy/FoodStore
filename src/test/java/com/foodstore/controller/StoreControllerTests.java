package com.foodstore.controller;

import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml", "/spring-mvc.xml", "/spring-shiro-web.xml"})
@WebAppConfiguration
public class StoreControllerTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter((Filter) context.getBean("shiroFilter"))
                .build();
        this.session = new MockHttpSession(context.getServletContext());
    }

    @Test
    public void test_openIndex() throws Exception {
        mvc
                .perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    public void test_openIndex_afterVipLogin() throws Exception {
        vipLogin();
        mvc
                .perform(get("/").session(session))
                .andExpect(view().name("index"));
    }

    @Test
    public void test_openIndex_afterOrdinaryLogin() throws Exception {
        ordinaryLogin();
        mvc
                .perform(get("/").session(session))
                .andExpect(view().name("index"));
    }

    @Test
    public void test_openMenu() throws Exception {
        mvc
                .perform(get("/menu"))
                .andExpect(view().name("menu"));
    }

    public void test_openMenu_afterVipLogin() throws Exception {
        vipLogin();
        mvc
                .perform(get("/menu").session(session))
                .andExpect(view().name("menu"));
    }

    @Test
    public void test_openMenu_afterOrdinaryLogin() throws Exception {
        ordinaryLogin();
        mvc
                .perform(get("/menu").session(session))
                .andExpect(view().name("menu"));
    }

    @Test
    public void test_openDetail() throws Exception {
        mvc
                .perform(get("/cuisine_detail/{name}", "1"))
                .andExpect(view().name("detail"));
    }

    @Test
    public void test_openDetail_afterVipLogin() throws Exception {
        vipLogin();
        mvc
                .perform(get("/cuisine_detail/{name}", "1").session(session))
                .andExpect(view().name("detail"));
    }

    @Test
    public void test_openDetail_afterOrdinaryLogin() throws Exception {
        ordinaryLogin();
        mvc
                .perform(get("/cuisine_detail/{name}", "1").session(session))
                .andExpect(view().name("detail"));
    }

    @Test
    public void test_openSubmit() throws Exception {
        mvc
                .perform(
                        post("/submit")
                                .param("amount", "1"))
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void test_openSubmit_afterLogin() throws Exception {
        vipLogin();
        mvc
                .perform(
                        post("/submit")
                                .session(session)
                                .param("amount", "1"))
                .andExpect(view().name(new StringStartsWith("redirect:/order/")));
    }

    @Test
    public void test_openOrder() throws Exception {
        mvc
                .perform(get("/order/{str}", "1"))
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void test_openOrder_afterVipLogin() throws Exception {
        vipLogin();
        mvc
                .perform(get("/order/{str}", "1").session(session))
                .andExpect(view().name("order"));
    }

    @Test
    public void test_openOrder_afterOrdinaryLogin() throws Exception {
        ordinaryLogin();
        mvc
                .perform(get("/order/{str}", "1").session(session))
                .andExpect(view().name("order"));
    }

    @Test
    public void test_loginGet() throws Exception {
        mvc
                .perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void test_loginGet_afterLogin() throws Exception {
        vipLogin();
        mvc
                .perform(get("/login").session(session))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void test_loginPost_withCorrectCredentials() throws Exception {
        mvc
                .perform(post("/login")
                        .param("username", "Alice")
                        .param("password", "123")
                        .session(session))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void test_loginPost_withIncorrectCredentials() throws Exception {
        mvc
                .perform(post("/login")
                        .param("username", "Alice")
                        .param("password", "1234")
                        .session(session))
                .andExpect(view().name("login"));
    }

    @Test
    public void test_userLogout() throws Exception {
        ordinaryLogin();
        mvc
                .perform(get("/logout").session(session))
                .andExpect(view().name("logout"));

    }

    private void vipLogin() throws Exception {
        login("Alice", "123");
    }

    private void ordinaryLogin() throws Exception {
        login("Bob", "456");
    }

    private void login(String username, String password) throws Exception {
        mvc
                .perform(post("/login")
                        .param("username", username)
                        .param("password", password)
                        .param("remember", "true")
                        .session(session))
                .andExpect(view().name("redirect:/"));
    }
}
