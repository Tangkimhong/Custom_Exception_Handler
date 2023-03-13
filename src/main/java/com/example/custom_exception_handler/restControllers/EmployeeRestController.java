package com.example.custom_exception_handler.restControllers;

import com.example.custom_exception_handler.entities.ApiError;
import com.example.custom_exception_handler.entities.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empl")
public class EmployeeRestController {

    List<Employee> employees = new ArrayList<>();
    {
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee((i + 1),("name " + i),"male",500.10 * (i + 1));
            employees.add(employee);
        }
    }

    @GetMapping("/s")
    public List<Employee> getAll(){
        return employees;
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getAllResponseEntity(){
        if (employees.isEmpty()){
            return new ResponseEntity<>(employees,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody @Valid Employee employee){
        System.out.println(employee);
        employees.add(employee);
        return ResponseEntity.ok(true);
    }

    /*
     * Custom exception in local controller
     */
    /*@ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerSomeControllerEx(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(),
                LocalDateTime.now(),
                "error occurred");

        return new ResponseEntity<>(apiError,new HttpHeaders(),apiError.getStatus());
    }*/


}
