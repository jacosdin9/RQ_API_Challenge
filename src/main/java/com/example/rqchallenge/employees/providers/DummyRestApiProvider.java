package com.example.rqchallenge.employees.providers;

import com.example.rqchallenge.employees.client.CreateEmployeeResponse;
import com.example.rqchallenge.employees.client.DeleteEmployeeResponse;
import com.example.rqchallenge.employees.client.EmployeesClientResponse;
import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exceptions.DummyRestApiProviderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class DummyRestApiProvider {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private static final String SUCCESS_STATUS = "success";

    public List<Employee> getAllEmployees() {
        String url = BASE_URL + "/employees";

        try {
            EmployeesClientResponse response = restTemplate.getForObject(url, EmployeesClientResponse.class);

            if(response != null && response.getStatus().equals(SUCCESS_STATUS)) {
                return response.getData().stream()
                        .map(clientEmployee -> new Employee(clientEmployee.getId(), clientEmployee.getEmployeeName(), clientEmployee.getEmployeeSalary(), clientEmployee.getEmployeeAge(), clientEmployee.getProfileImage()))
                        .collect(Collectors.toList());
            } else {
                throw new DummyRestApiProviderException("Error response received from DummyAPI in provider when trying to get all employees");
            }

        } catch (Exception e) {
            throw new DummyRestApiProviderException("Error occurred in provider when attempting to " +
                    "retrieve all employees as response from Dummy API", e);
        }
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        String url = BASE_URL + "/create";

        try {
            CreateEmployeeResponse response = restTemplate.postForObject(url, employeeInput, CreateEmployeeResponse.class);

            if(response != null && response.getStatus().equals(SUCCESS_STATUS)) {
                CreateEmployeeResponse.ClientCreateEmployee clientCreateEmployee = response.getData();
                return new Employee(clientCreateEmployee.getId(), clientCreateEmployee.getName(), clientCreateEmployee.getSalary(), clientCreateEmployee.getAge(), "");
            } else {
                throw new DummyRestApiProviderException("Error response received from DummyAPI in provider when trying to create new employee");
            }

        } catch(Exception e) {
            throw new DummyRestApiProviderException("Error occurred in provider when attempting to retrieve response from DummyAPI and" +
                    "create new employee", e);
        }
    }

    public String deleteEmployeeById(String id) {
        String url = BASE_URL + "/delete/" + id;

        try {
            ResponseEntity<DeleteEmployeeResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    DeleteEmployeeResponse.class
            );

            if(response.getBody() != null && response.getBody().getStatus().equals(SUCCESS_STATUS)) {
                return response.getBody().getMessage();
            } else {
                throw new DummyRestApiProviderException("Error response received from DummyAPI in provider when trying to delete employee");
            }
        } catch(Exception e) {
            throw new DummyRestApiProviderException("Error occurred in provider when attempting to retrieve response from DummyAPI and" +
                    "delete employee", e);
        }
    }

}
