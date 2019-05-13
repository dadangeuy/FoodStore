package com.foodstore.service;

import com.foodstore.entity.Food;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml", "/spring-mvc.xml", "/spring-shiro-web.xml"})
@WebAppConfiguration
public class FoodServiceTests {
    @Autowired
    private FoodService service;

    @Before
    public void cleanDb() {
        List<Food> foods = service.findAll();
        for (Food food : foods) {
            service.deleteFood(food.getId());
        }
    }

    @Test
    public void test_create_expectCreated() {
        Food food = createSampleFood();
        service.createFood(food);
    }

    @Test
    public void test_update_expectUpdated() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        createdFood.setDescription("Updated Description");
        service.updateFood(food);
    }

    @Test
    public void test_update_expectNotFound() {
        Food food = createSampleFood();
        service.updateFood(food);
    }

    @Test
    public void test_delete_expectDeleted() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        service.deleteFood(createdFood.getId());
    }

    @Test
    public void test_delete_expectNotFound() {
        Food food = createSampleFood();
        service.deleteFood(food.getId());
    }

    @Test
    public void test_findOne_expectFound() {
        Food food = createSampleFood();
        Food createdFood = service.createFood(food);
        service.findOne(createdFood.getId());
    }

    public void test_findOne_expectNotFound() {
        Food food = createSampleFood();
        service.findOne(food.getId());
    }

    @Test
    public void test_findAll_expectExists() {
        Food food = createSampleFood();
        service.createFood(food);
        service.findAll();
    }

    @Test
    public void test_findAll_expectEmpty() {
        service.findAll();
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
