package com.github.atsarev01.mochito.service;

import com.github.atsarev01.mochito.exeption.EmployeeAlreadyAddedException;
import com.github.atsarev01.mochito.exeption.EmployeeNotFoundException;
import com.github.atsarev01.mochito.exeption.EmployeeStorageIsFullException;
import com.github.atsarev01.mochito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private static final int SIZE = 3;

    private final Map <String, Employee> employees = new HashMap<>();

    private final ValidatorService validatorService;

    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    private String getKey(String firstName, String surName) {
        return firstName + "|" + surName;
    }

    public Employee add(String firstName, String surName, int department, int salary) {
        if (employees.size() < SIZE) {
            Employee employee = new Employee(validatorService.validateName(firstName),
                    validatorService.validateSurname(surName),
                    department,
                    salary);
            if (employees.containsKey(employee.getFullName() )) {
                throw new EmployeeAlreadyAddedException();
            }
            employees.put(employee.getFullName(), employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();

    }
    public Employee remove(String firstName, String surName) {
        Employee employee = new Employee(firstName, surName);
        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee.getFullName());
        return employee;
    }
    public Employee find(String firstName, String surName) {
        Employee employee = new Employee(firstName, surName);
        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(employee.getFullName());

    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }
}
