package com.example.rqchallenge.employees.controllers;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.services.CachedEmployeeService;
import com.example.rqchallenge.employees.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmployeeController implements IEmployeeController{
    private final EmployeeService employeeService;
    private final CachedEmployeeService cachedEmployeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cachedEmployeeService.getAllEmployees());
        } catch(Exception e) {
            log.error("Error occurred when getting all employees", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeesByNameSearch(searchString));
        } catch(Exception e) {
            log.error("Error occurred when getting employees by name search", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
        } catch(NoSuchElementException nsee) {
            log.error("No employee with this ID exists", nsee);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch(Exception e) {
            log.error("Error occurred when getting employees by id search", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getHighestSalaryOfEmployees());
        } catch(NoSuchElementException nsee) {
            log.error("No employees salaries found to compare", nsee);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch(Exception e) {
            log.error("Error occurred when attempting to get highest employee salary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getTopTenHighestEarningEmployeeNames());
        } catch(Exception e) {
            log.error("Error occurred when getting top ten highest earning employees' names", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.createEmployee(employeeInput));
        } catch(Exception e) {
            log.error("Error occurred when creating new employee", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployeeById(id));
        } catch(Exception e) {
            log.error("Error occurred when creating new employee", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
