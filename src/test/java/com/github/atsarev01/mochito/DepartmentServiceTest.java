package com.github.atsarev01.mochito;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.atsarev01.mochito.exeption.EmployeeNotFoundException;
import com.github.atsarev01.mochito.model.Employee;
import com.github.atsarev01.mochito.service.DepartmentService;
import com.github.atsarev01.mochito.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    public static Stream<Arguments> EmployeeWithMaxSalaryTestParams() {
        return Stream.of(
                Arguments.of(1,new Employee("Vasya", "Pupkin", 1,12000)),
                Arguments.of(2,new Employee("Sergey", "Sergeev", 2,15000)),
                Arguments.of(3,new Employee("Andrey", "Petrov", 3,25000))
        );
    }
    public static Stream<Arguments> EmployeeWithMinSalaryTestParams() {
        return Stream.of(
                Arguments.of(1,new Employee("Alexey", "Ivanov", 1,10000)),
                Arguments.of(2,new Employee("Maxim", "Putin", 2,8000)),
                Arguments.of(3,new Employee("Andrey", "Petrov", 3,25000))
        );
    }

    public static Stream<Arguments> getAllEmployeesFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1,
                        List.of(
                                new Employee("Alexey", "Ivanov", 1, 10000),
                                new Employee("Vasya", "Pupkin", 1, 12000)
                        ),
                        Arguments.of(2,
                                List.of(
                                        new Employee("Maxim", "Putin", 2, 8000),
                                        new Employee("Sergey", "Sergeev", 2, 15000)
                                ),
                                Arguments.of(3,
                                        List.of(
                                                new Employee("Andrey", "Petrov", 3, 25000)
                                        ),
                                        Arguments.of(4, List.of())))));
    }

    @BeforeEach
    public void beforeEach() {
        when(employeeService.getAll()).thenReturn(
                List.of(
                new Employee("Alexey", "Ivanov", 1,10000),
                new Employee("Maxim", "Putin", 2,8000),
                new Employee("Andrey", "Petrov", 3,25000),
                new Employee("Vasya", "Pupkin", 1,12000),
                new Employee("Sergey", "Sergeev", 2,15000)

                )
        );
    }

    @ParameterizedTest
    @MethodSource("EmployeeWithMaxSalaryTestParams")
    public void EmployeeWithMaxSalaryTest(int department, Employee expected) {
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(department))
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surName", "department", "salary")
                .isEqualTo(expected);

    }
    @Test
    public void EmployeeWithMaxSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->departmentService.findEmployeeWithMaxSalaryFromDepartment(4));

    }
    @ParameterizedTest
    @MethodSource("EmployeeWithMaxSalaryTestParams")
    public void EmployeeWithMinSalaryTest(int department, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(department))
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surName", "department", "salary")
                .isEqualTo(expected);

    }
    @Test
    public void EmployeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->departmentService.findEmployeeWithMinSalaryFromDepartment(4));

    }
    @ParameterizedTest
    @MethodSource("getAllEmployeesFromDepartmentTestParams")
    public void getAllEmployeesFromDepartmentTest(int department, List<Employee> expected) {
        assertThat(departmentService.getAllEmployeesFromDepartment(department))
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surName", "department", "salary")
                .isEqualTo(expected);

    }
    @Test
    public void getEmployeesByDepartmentTest() {
        assertThat(departmentService.getEmployeesByDepartment())
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surName", "department", "salary")
                .isEqualTo(
                        Map.of(
                                1,
                                List.of(
                                        new Employee("Alexey", "Ivanov", 1, 10000),
                                        new Employee("Vasya", "Pupkin", 1, 12000)
                                ),
                                        2,
                                List.of(
                                        new Employee("Maxim", "Putin", 2, 8000),
                                        new Employee("Sergey", "Sergeev", 2, 15000)
                                ),
                                3,Arguments.of(3,
                                        List.of(
                                                new Employee("Andrey", "Petrov", 3, 25000)
                                        )
                                )

                ));

    }
    }
