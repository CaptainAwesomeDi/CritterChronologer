package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    public Schedule save(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedule(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPet(long petId) {
        Pet pet = petService.getPetById(petId);
        return scheduleRepository.findByPet(pet);
    }

    public List<Schedule> findScheduleByEmployee(long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return scheduleRepository.findByEmployee(employee);
    }

    public List<Schedule> findScheduleByCustomer(long customerId){
        Customer customer = customerService.getById(customerId);
        return scheduleRepository.findByOwner(customer);
    }

}
