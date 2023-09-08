package com.example.rqchallenge.employees.services;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.domain.EmployeeSalary;
import com.example.rqchallenge.employees.providers.DummyRestApiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final DummyRestApiProvider dummyRestApiProvider;
    private final CachedEmployeeService cachedEmployeeService;

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = cachedEmployeeService.getAllEmployees();

        return employeeList.stream()
                .filter(employee -> employee.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        List<Employee> employeeList = cachedEmployeeService.getAllEmployees();
        Optional<Employee> result = employeeList.stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst();

        return result.orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
    }

    public int getHighestSalaryOfEmployees() {
        List<Employee> employeeList = cachedEmployeeService.getAllEmployees();
        Optional<Employee> highestSalary = employeeList.stream()
                .max(Comparator.comparing(employee -> {
                    try {
                        return Integer.parseInt(employee.getEmployeeSalary());
                    } catch (NumberFormatException e) {
                        return Integer.MIN_VALUE;
                    }
                }));

        if(highestSalary.isPresent()) {
            return Integer.parseInt(highestSalary.get().getEmployeeSalary());
        } else {
            throw new NoSuchElementException("No employees salaries found to compare");
        }
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employeeList = cachedEmployeeService.getAllEmployees();

        List<EmployeeSalary> highestEarners = employeeList.stream()
                .map(employee -> {
                    try {
                        return new EmployeeSalary(employee.getEmployeeName(), Integer.parseInt(employee.getEmployeeSalary()));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(EmployeeSalary::getSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());

        return highestEarners.stream()
                .map(EmployeeSalary::getName)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "allEmployeeCache", allEntries = true)
    public Employee createEmployee(Map<String, Object> employeeInput) {
        return dummyRestApiProvider.createEmployee(employeeInput);
    }

    @CacheEvict(value = "allEmployeeCache", allEntries = true)
    public String deleteEmployeeById(String id) {
        return dummyRestApiProvider.deleteEmployeeById(id);
    }
}
