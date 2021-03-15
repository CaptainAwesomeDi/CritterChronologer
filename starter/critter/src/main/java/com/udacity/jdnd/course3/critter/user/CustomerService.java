package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Customer getById(long ownerId){
        return customerRepository.findById(ownerId).get();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCusotmers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByPet(long petId){
        return petRepository.findById(petId).get().getCustomer();
    }
}
