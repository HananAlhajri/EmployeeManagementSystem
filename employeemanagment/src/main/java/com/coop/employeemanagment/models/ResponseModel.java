package com.coop.employeemanagment.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Builder
@Setter
@Getter
public class ResponseModel {

    private int statusCode;
    private HttpStatus status;
    private String message;
    private Map<String, Object> data;

}
