package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id){
        return employeeRepository.findById(id).get();
    }

    public List<Employee> getEmployeesByIds(List<Long> ids){
        return employeeRepository.findByIds(ids);
    }

    public List<Employee> getEmployeesByService(LocalDate date, Set<EmployeeSkill> skills){
        return employeeRepository.findEmployeesForService(date.getDayOfWeek())
                .stream().filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
