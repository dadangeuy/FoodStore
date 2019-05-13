package com.foodstore.service;

import com.foodstore.entity.Food;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml", "/spring-mvc.xml", "/spring-shiro-web.xml"})
@WebAppConfiguration
public class FoodServiceTests {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private FoodService service;

    @Before
    public void reset() {
        template.update("delete from sys_food");
    }

    @Test
    public void test_createFood_expectCreated() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        Assert.assertNotNull(createdFood);
    }

    @Test
    public void test_updateFood_expectUpdated() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        createdFood.setDescription("Updated Description");
        Food updatedFood = service.updateFood(food);
        Assert.assertNotNull(updatedFood);
    }

    @Test
    public void test_deleteFood_expectDeleted() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        service.deleteFood(createdFood.getId());
    }

    @Test
    public void test_findOneFood_expectFound() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        Food foundedFood = service.findOne(createdFood.getId());
        Assert.assertNotNull(foundedFood);
    }

    @Test
    public void test_findAll_expectExists() {
        Food food = createSampleFood();
        service.createFood(food);
        List<Food> foundedFoods = service.findAll();
        Assert.assertFalse(foundedFoods.isEmpty());
    }

    private Food createSampleFood() {
        Food food = new Food();
        food.setId(0);
        food.setFoodname("Sample Food");
        food.setPrice("Sample Price");
        food.setQuantity(0);
        food.setIsvip(false);
        food.setDescription("Sample Description");
        return food;
    }
}
