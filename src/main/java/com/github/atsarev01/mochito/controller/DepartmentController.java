package com.github.atsarev01.mochito.controller;

import com.github.atsarev01.mochito.model.Employee;
import com.github.atsarev01.mochito.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;

    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalaryFromDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMaxSalaryFromDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalaryFromDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(department);
    }
    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> findEmployeesFromDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.getAllEmployeesFromDepartment(department);
    }
    @GetMapping(value = "/all")
    public Map<Integer, List<Employee>> findEmployees() {
        return departmentService.getEmployeesByDepartment();
    }

}
