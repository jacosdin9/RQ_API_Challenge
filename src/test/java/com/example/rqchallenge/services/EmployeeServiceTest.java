package com.example.rqchallenge.services;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.providers.DummyRestApiProvider;
import com.example.rqchallenge.employees.services.CachedEmployeeService;
import com.example.rqchallenge.employees.services.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    private static Employee employee1;
    private static Employee employee2;
    private static Employee employee3;
    private static List<Employee> employeeList;

    @Mock
    DummyRestApiProvider dummyRestApiProvider;
    @Mock
    CachedEmployeeService cachedEmployeeService;
    @InjectMocks
    EmployeeService employeeService;

    @BeforeAll
    static void setup() {
        employee1 = createEmployee("1","name1", "123");
        employee2 = createEmployee("2", "name2", "456");
        employee3 = createEmployee("3", "name3", "789");
        employeeList = Arrays.asList(employee1, employee2, employee3);
    }

    @Test
    void getEmployeesByNameSearch() {
        when(cachedEmployeeService.getAllEmployees()).thenReturn(employeeList);

        List<Employee> result = employeeService.getEmployeesByNameSearch("name1");

        assertEquals(Collections.singletonList(employee1), result);
    }

    @Test
    void getEmployeeById() {
        when(cachedEmployeeService.getAllEmployees()).thenReturn(employeeList);

        Employee result = employeeService.getEmployeeById("1");

        assertEquals(employee1, result);
    }

    @Test
    void getHighestSalaryOfEmployees() {
        when(cachedEmployeeService.getAllEmployees()).thenReturn(employeeList);

        int result = employeeService.getHighestSalaryOfEmployees();

        assertEquals(789, result);
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
        when(cachedEmployeeService.getAllEmployees()).thenReturn(employeeList);

        List<String> result = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(Arrays.asList("name3", "name2", "name1"), result);
    }

    @Test
    void createEmployee() {
        when(dummyRestApiProvider.createEmployee(new HashMap<>())).thenReturn(employee1);

        Employee result = employeeService.createEmployee(new HashMap<>());

        assertEquals(employee1, result);
    }

    @Test
    void deleteEmployee() {
        when(dummyRestApiProvider.deleteEmployeeById("1")).thenReturn("success");

        String result = employeeService.deleteEmployeeById("1");

        assertEquals("success", result);
    }

    private static Employee createEmployee(String id, String name, String salary) {
        return new Employee(id, name, salary, "", "");
    }
}
