package com.RestApiExample.demo.services;

import com.RestApiExample.demo.repositories.FoodRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private FoodService foodService;
}
