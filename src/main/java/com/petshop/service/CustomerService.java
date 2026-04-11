package com.petshop.service;

import com.petshop.dto.CustomerDTO;
import com.petshop.entity.Customer;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    // REGISTER
    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    // UPDATE
    public Customer updateCustomer(int id, Customer updated) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        c.setFirstName(updated.getFirstName());
        c.setLastName(updated.getLastName());
        c.setEmail(updated.getEmail());
        c.setPhone(updated.getPhone());
        c.setAddress(updated.getAddress());

        return customerRepo.save(c);
    }

    // GET BY ID
    public CustomerDTO getCustomer(int id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return new CustomerDTO(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getPhone(),
                c.getAddress()
        );
    }

    // GET ALL
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(c -> new CustomerDTO(
                        c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getPhone(),
                        c.getAddress()
                ))
                .toList();
    }
}