package com.example.rqchallenge.employees.services;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.providers.DummyRestApiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CachedEmployeeService {
    private final DummyRestApiProvider dummyRestApiProvider;

    @Cacheable(value = "allEmployeeCache")
    public List<Employee> getAllEmployees() {
        return dummyRestApiProvider.getAllEmployees();
    }
}
