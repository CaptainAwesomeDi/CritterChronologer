package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("select e from Employee e where :dayOfWeek member of e.daysAvailable")
    List<Employee> findEmployeesForService(DayOfWeek dayOfWeek);

    @Query("select e from Employee e where id in :employeeIds")
    List<Employee> findByIds(List<Long> employeeIds);
}
