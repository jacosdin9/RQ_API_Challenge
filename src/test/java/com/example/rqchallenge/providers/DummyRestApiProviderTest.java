package com.example.rqchallenge.providers;

import com.example.rqchallenge.employees.client.CreateEmployeeResponse;
import com.example.rqchallenge.employees.client.DeleteEmployeeResponse;
import com.example.rqchallenge.employees.client.EmployeesClientResponse;
import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.providers.DummyRestApiProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DummyRestApiProviderTest {

    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private final String SUCCESS_MESSAGE = "success";

    @Mock
    private RestTemplate restTemplate;

    private DummyRestApiProvider dummyRestApiProvider;

    @BeforeEach
    void setup() {
        dummyRestApiProvider = new DummyRestApiProvider(restTemplate);
    }

    @Test
    void getAllEmployees_success() {
        when(restTemplate.getForObject(BASE_URL + "/employees", EmployeesClientResponse.class))
                .thenReturn(new EmployeesClientResponse(SUCCESS_MESSAGE, List.of(new EmployeesClientResponse.ClientEmployee())));

        List<Employee> result = dummyRestApiProvider.getAllEmployees();

        assertEquals(List.of(new Employee()), result);
    }

    @Test
    void createEmployee() {
        when(restTemplate.postForObject(BASE_URL + "/create", new HashMap<>(), CreateEmployeeResponse.class))
                .thenReturn(new CreateEmployeeResponse(SUCCESS_MESSAGE, new CreateEmployeeResponse.ClientCreateEmployee()));

        Employee result = dummyRestApiProvider.createEmployee(new HashMap<>());

        assertEquals(new Employee(null, null, null, null, ""), result);
    }

    @Test
    void deleteEmployee() {
        String ID = "12345";
        when(restTemplate.exchange(
                BASE_URL + "/delete/" + ID,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponse.class
        )).thenReturn(new ResponseEntity<>(new DeleteEmployeeResponse(SUCCESS_MESSAGE, SUCCESS_MESSAGE), HttpStatus.OK));

        String result = dummyRestApiProvider.deleteEmployeeById(ID);

        assertEquals(SUCCESS_MESSAGE, result);
    }
}
