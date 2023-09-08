package com.example.rqchallenge.controllers;

import com.example.rqchallenge.employees.controllers.EmployeeController;
import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.services.CachedEmployeeService;
import com.example.rqchallenge.employees.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {

    private final String NAME = "paul";
    private final String ID = "123";
    private final int SALARY = 50000;
    private final String SUCCESS_MESSAGE = "Success!";

    @Mock
    private EmployeeService employeeService;
    @Mock
    private CachedEmployeeService cachedEmployeeService;
    @InjectMocks
    EmployeeController employeeController;

    @Test
    void getAllEmployeesTest_success() {
        when(cachedEmployeeService.getAllEmployees()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Employee>> result = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllEmployeesTest_exception() {
        when(cachedEmployeeService.getAllEmployees()).thenThrow(new RuntimeException());

        ResponseEntity<List<Employee>> result = employeeController.getAllEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getEmployeesByNameSearch_success() {
        when(employeeService.getEmployeesByNameSearch(NAME)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Employee>> result = employeeController.getEmployeesByNameSearch(NAME);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getEmployeesByNameSearch_exception() {
        when(employeeService.getEmployeesByNameSearch(NAME)).thenThrow(new RuntimeException());

        ResponseEntity<List<Employee>> result = employeeController.getEmployeesByNameSearch(NAME);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getEmployeeById_success() {
        when(employeeService.getEmployeeById(ID)).thenReturn(new Employee());

        ResponseEntity<Employee> result = employeeController.getEmployeeById(ID);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getEmployeeById_exception() {
        when(employeeService.getEmployeeById(ID)).thenThrow(new RuntimeException());

        ResponseEntity<Employee> result = employeeController.getEmployeeById(ID);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getHighestSalaryOfEmployees_success() {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(SALARY);

        ResponseEntity<Integer> result = employeeController.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(SALARY, result.getBody());
    }

    @Test
    void getHighestSalaryOfEmployees_exception() {
        when(employeeService.getHighestSalaryOfEmployees()).thenThrow(new RuntimeException());

        ResponseEntity<Integer> result = employeeController.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getHighestSalaryOfEmployees_noSuchElementException() {
        when(employeeService.getHighestSalaryOfEmployees()).thenThrow(new NoSuchElementException());

        ResponseEntity<Integer> result = employeeController.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_success() {
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(new ArrayList<>());

        ResponseEntity<List<String>> result = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_exception() {
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenThrow(new RuntimeException());

        ResponseEntity<List<String>> result = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void createEmployee_success() {
        when(employeeService.createEmployee(new HashMap<>())).thenReturn(new Employee());

        ResponseEntity<Employee> result = employeeController.createEmployee(new HashMap<>());

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void createEmployee_exception() {
        when(employeeService.createEmployee(new HashMap<>())).thenThrow(new RuntimeException());

        ResponseEntity<Employee> result = employeeController.createEmployee(new HashMap<>());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void deleteEmployeeById_success() {
        when(employeeService.deleteEmployeeById(ID)).thenReturn(SUCCESS_MESSAGE);

        ResponseEntity<String> result = employeeController.deleteEmployeeById(NAME);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteEmployeeById_exception() {
        when(employeeService.deleteEmployeeById(ID)).thenThrow(new RuntimeException());

        ResponseEntity<String> result = employeeController.deleteEmployeeById(ID);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

}
