package com.foodstore.service;

import com.foodstore.entity.Order;
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
public class OrderServiceTests {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private OrderService service;

    @Before
    public void reset() {
        template.update("delete from sys_order");
    }

    @Test
    public void test_createOrder_expectCreated() {
        Order order = createSampleOrder();
        service.createOrder(order);
    }

    @Test
    public void test_deleteOrder_expectDeleted() {
        Order order = createSampleOrder();
        long createdOrderId = service.createOrder(order);
        service.deleteOrder(createdOrderId);
    }

    @Test
    public void test_findOneOrder_expectFound() {
        Order order = createSampleOrder();
        long createdOrderId = service.createOrder(order);
        Order foundedOrder = service.findOne(createdOrderId);
        Assert.assertNotNull(foundedOrder);
    }

    @Test
    public void test_submitOrder_expectSubmitted() {
        service.submitOrder("Submit Customer", "Submit Amount");
    }

    private Order createSampleOrder() {
        Order order = new Order();
        order.setId(0);
        order.setCustomer("Sample Customer");
        order.setAmount("Sample Amount");
        return order;
    }
}
