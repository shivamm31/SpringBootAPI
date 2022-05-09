package com.CrudOperation.SpringBootCrudOperation.controller;

import com.CrudOperation.SpringBootCrudOperation.exception.ResourceNotFoundException;
import com.CrudOperation.SpringBootCrudOperation.model.Employee;
import com.CrudOperation.SpringBootCrudOperation.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/" )
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/getAll")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Adding multiple employees
    @PostMapping("/addEmployee")
    public List<Employee> createEmployee(@RequestBody List<Employee> employee){
        return  employeeRepository.saveAll(employee);
    }

    // build get employee RST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist" + id));
        return ResponseEntity.ok(employee);
    }

    // build update employee RST API

    @PutMapping("/updateEmployee/{id}")
    public  ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id " +id));
        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setAddress(employeeDetails.getAddress());

        employeeRepository.save(updateEmployee);

        return  ResponseEntity.ok(updateEmployee);
    }

    // build delete employee REST API

    @DeleteMapping("{id}")
    public HttpStatus deleteEmployee(@PathVariable long id){

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id " + id));

        employeeRepository.delete(employee);

        return HttpStatus.MOVED_PERMANENTLY;
    }
}
