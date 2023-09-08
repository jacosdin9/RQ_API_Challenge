package com.example.rqchallenge.employees.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeResponse {
    private String status;
    private ClientCreateEmployee data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientCreateEmployee {
        @JsonProperty("name")
        private String name;
        @JsonProperty("salary")
        private String salary;
        @JsonProperty("age")
        private String age;
        @JsonProperty("id")
        private String id;
    }
}
