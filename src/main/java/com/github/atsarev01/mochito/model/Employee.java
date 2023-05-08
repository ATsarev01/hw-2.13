package com.github.atsarev01.mochito.model;

import java.util.Objects;

public class Employee {
    private String firstName;
    private String surName;
    private int department;
    private int salary;

    public Employee(String firstName, String surName, int department, int salary) {
        this.firstName = firstName;
        this.surName = surName;
        this.department = department;
        this.salary = salary;
    }

    public Employee(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return surName;
    }

    public String getFullName() { return firstName + " " + surName; }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(surName, employee.surName);
    }

    @Override
    public int hashCode() {
            return Objects.hash(firstName, surName, department, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", отдел='" + department + '\'' +
                ", ЗП='" + salary + '\'' +
                '}';
    }
}
