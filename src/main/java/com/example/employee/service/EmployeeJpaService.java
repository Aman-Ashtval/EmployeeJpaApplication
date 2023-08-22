package com.example.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.employee.repository.*;
import com.example.employee.model.Employee;

@Service 
public class EmployeeJpaService implements EmployeeRepository{

    @Autowired
    private EmployeeJpaRepository emp;

    @Override 
    public ArrayList<Employee> getEmployees(){
        List<Employee> employees = emp.findAll();
        return new ArrayList<>(employees);
    }

    @Override
    public Employee getEmployeeById(int employeeId){
        try{
            Employee employee = emp.findById(employeeId).get();
            return employee;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Employee addEmployee(Employee employee){
        emp.save(employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee employee){
        try{
            Employee existingEmp = getEmployeeById(employeeId);
            if(employee.getEmployeeName() != null){
                existingEmp.setEmployeeName(employee.getEmployeeName());
            }
            if(employee.getEmail() != null){
                existingEmp.setEmail(employee.getEmail());
            }
            if(employee.getDepartment() != null){
                existingEmp.setDepartment(employee.getDepartment());
            }

            emp.save(existingEmp);
            return existingEmp;

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override 
    public void deleteEmployee(int employeeId){
        try{
            emp.deleteById(employeeId);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);    
        }
    }
}