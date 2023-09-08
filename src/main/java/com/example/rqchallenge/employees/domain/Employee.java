package com.example.rqchallenge.employees.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private String employeeName;
    private String employeeSalary;
    private String employeeAge;
    private String profileImage;
}
