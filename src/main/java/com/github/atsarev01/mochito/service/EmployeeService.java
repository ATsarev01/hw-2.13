package com.github.atsarev01.mochito.service;

import com.github.atsarev01.mochito.exeption.EmployeeAlreadyAddedExeption;
import com.github.atsarev01.mochito.exeption.EmployeeNotFoundExeption;
import com.github.atsarev01.mochito.exeption.EmployeeStorageIsFullExeption;
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

    private String getKey(String firstName, String lastName) {
        return firstName + "|" + lastName;
    }

    public Employee add(String firstName, String lastName, int department, int salary) {
        if (employees.size() < SIZE) {
            Employee employee = new Employee(validatorService.validateName(firstName),
                    validatorService.validateSurname(lastName),
                    department,
                    salary);
            if (employees.containsKey(employee.getFullName() )) {
                throw new EmployeeAlreadyAddedExeption();
            }
            employees.put(employee.getFullName(), employee);
            return employee;
        }
        throw new EmployeeStorageIsFullExeption();

    }
    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundExeption();
        }
        employees.remove(employee.getFullName());
        return employee;
    }
    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundExeption();
        }
        return employees.get(employee.getFullName());

    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }
}
