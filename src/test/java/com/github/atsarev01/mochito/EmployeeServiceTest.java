package com.github.atsarev01.mochito;

import com.github.atsarev01.mochito.exeption.*;
import com.github.atsarev01.mochito.model.Employee;
import com.github.atsarev01.mochito.service.EmployeeService;
import com.github.atsarev01.mochito.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    public static Stream<Arguments> addWithIncorrectNameTestParams() {
        return Stream.of(
        Arguments.of("Vasya1"),
        Arguments.of("Vasya@"),
        Arguments.of("Vasya!")
        );
    }
    public static Stream<Arguments> addWithIncorrectSurnameTestParams() {
        return Stream.of(
                Arguments.of("Vasilev1"),
                Arguments.of("Vasilev@"),
                Arguments.of("Vasilev!")
        );
    }

@BeforeEach
    public void beforeEach() {
        employeeService.add("Ivan", "Ivanov", 1, 10000);
        employeeService.add("Maxim", "Putin", 2, 8000);

    }

    @Test
    public void addTest() {
        Employee expected = new Employee("Andrey", "Petrov", 3, 25000);
        int beforeCount = employeeService.getAll().size();

        Employee actual = employeeService.add("Andrey", "Petrov", 3, 25000);
        assertThat(actual).isEqualTo(expected)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surname", "department", "salary")
        .isIn(employeeService.getAll());
        assertThat(employeeService.getAll().size()).isEqualTo(beforeCount + 1);
    }

    @ParameterizedTest
    @MethodSource("addWithIncorrectNameTestParams")
    public void when_incorrect_name_was_passed_then_IncorrectNameException_will_be_thrown(String incorrectName) {
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() ->employeeService.add(incorrectName, "Vasilev", 4, 5000));
    }

    @ParameterizedTest
    @MethodSource("addWithIncorrectSurnameTestParams")
    public void addWithIncorrectSurnameTestParams(String incorrectSurname) {
        assertThatExceptionOfType(IncorrectSurnameException.class)
                .isThrownBy(() ->employeeService.add("Vasya", incorrectSurname, 4, 5000));
    }

    @Test
    public void addEmployeeWhichWasAlreadyAddedTest() {
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() ->employeeService.add("Ivan", "Ivanov", 1, 10000));
    }

    @Test
    public void addEmployeeWhenStorageIsFullTest() {
        for (int i = 0; i < 8; i++) {
            employeeService.add("Name" + ((char) ('a' + i)), "SurName" + ((char)('a' + i)), i, 10000);
        }

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() ->employeeService.add("Vasya", "Pupkin", 1, 12000));
    }

    @Test
    public void removeTest() {
        Employee expected = new Employee("Ivan", "Ivanov", 1, 10000);
        int beforeCount = employeeService.getAll().size();

        Employee actual = employeeService.remove("Ivan", "Ivanov");
        assertThat(actual).isEqualTo(expected)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surname", "department", "salary")
                .isNotIn(employeeService.getAll());
        assertThat(employeeService.getAll().size()).isEqualTo(beforeCount - 1);
    }

    @Test
    public void removeNotFoundTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() ->employeeService.remove("Vasya", "Pupkin"));
    }
    @Test
    public void findTest() {
        Employee expected = new Employee("Ivan", "Ivanov", 1, 10000);

        Employee actual = employeeService.find("Ivan", "Ivanov");
        assertThat(actual).isEqualTo(expected)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "surname", "department", "salary")
                .isEqualTo(expected);
    }

    @Test
    public void findNotFoundTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() ->employeeService.find("Vasya", "Pupkin"));
    }
}
