package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(convertPetDTOtoPet(petDTO));
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();
        return convertPetsToPetDTOS(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getById(ownerId);
        List<Pet> pets = petService.getPetsByOwner(customer);
        return convertPetsToPetDTOS(pets);
    }

    private List<PetDTO> convertPetsToPetDTOS(List<Pet> pets){
        List<PetDTO> petDTOS = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setOwnerId(customerService.getCustomerByPet(pet.getId()).getId());
        BeanUtils.copyProperties(pet,petDTO);
        return petDTO;
    }

    private  Pet convertPetDTOtoPet(PetDTO petDTO){
        Pet newPet = new Pet();
        Customer customer = customerService.getById(petDTO.getOwnerId());
        newPet.setCustomer(customer);
        BeanUtils.copyProperties(petDTO,newPet);
        return newPet;
    }
}
