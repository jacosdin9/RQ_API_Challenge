package com.example.rqchallenge.employees.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesClientResponse {
    private String status;
    private List<ClientEmployee> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientEmployee {
        @JsonProperty("id")
        private String id;
        @JsonProperty("employee_name")
        private String employeeName;
        @JsonProperty("employee_salary")
        private String employeeSalary;
        @JsonProperty("employee_age")
        private String employeeAge;
        @JsonProperty("profile_image")
        private String profileImage;
    }
}
