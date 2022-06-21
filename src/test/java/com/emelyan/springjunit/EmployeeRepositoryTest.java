package com.emelyan.springjunit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){
        Employee employee = Employee.builder()
                .firstName("Nick")
                .lastName("Smith")
                .email("smith123@gmail.com")
                .build();

        employeeRepository.save(employee);
        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();

        Assertions.assertThat(employee.getId()).isEqualTo(1l);

    }

    @Order(3)
    @Test
    public void getListOfEmployeesTest(){

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Order(4)
    @Test
    @Rollback(value = false)
    public void updateEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();

        employee.setEmail("smith123@gmail.com");

        Employee employeeUpdate = employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdate.getEmail()).isEqualTo("smith123@gmail.com");

    }

    @Order(5)
    @Rollback(value = false)
    @Test
    public void deleteEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail("smith123@mail.gmail.com");

        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

}
