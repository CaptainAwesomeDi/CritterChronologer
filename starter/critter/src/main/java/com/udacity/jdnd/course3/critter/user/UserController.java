package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.junit.experimental.theories.internal.SpecificDataPointsSupplier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.saveCustomer(convertCustomerDTOtoCustomer(customerDTO));
        return convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getCusotmers();
        List<CustomerDTO> dtos = new LinkedList<>();
        for(Customer customer: customers){
            dtos.add(convertCustomerToCustomerDTO(customer));
        }
        return dtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPet(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        return  convertEmployeeToEmployeeDTO(employeeService.save(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
       Employee employee = employeeService.getEmployeeById(employeeId);
       if (employee != null) {
           employee.setDaysAvailable(daysAvailable);
           employeeService.save(employee);
       }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
       List<Employee> employees = employeeService.getEmployeesByService(employeeDTO.getDate(),employeeDTO.getSkills());
       List<EmployeeDTO> employeeDTOS = new LinkedList<>();
       for (Employee employee: employees){
           employeeDTOS.add(convertEmployeeToEmployeeDTO(employee));
       }
       return  employeeDTOS;
    }

    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO){
        Customer newCustomer = new Customer();
        BeanUtils.copyProperties(customerDTO,newCustomer);
        return newCustomer;
    }

    private  CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO newDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,newDTO);
        if (customer.getPets() != null) {
            List<Pet> pets  = customer.getPets();
            List<Long> petIds = new ArrayList<>();
            for(Pet pet: pets){
                petIds.add(pet.getId());
            }

            newDTO.setPetIds(petIds);
        }
        return newDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee newEmployee = new Employee();
        BeanUtils.copyProperties(employeeDTO,newEmployee);
        return newEmployee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
