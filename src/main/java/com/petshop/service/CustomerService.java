package com.petshop.service;

import com.petshop.dto.CustomerDTO;
import com.petshop.dto.LoginRequest;
import com.petshop.entity.Address;
import com.petshop.entity.Customer;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.AddressRepository;
import com.petshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AddressRepository addressRepo;

    // ================= LOGIN =================
    public String login(LoginRequest request) {

        Customer c = customerRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        if (!c.getPhone().equals(request.getPhone())) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        return "Login successful";
    }

    // ================= REGISTER =================
    public CustomerDTO registerCustomer(CustomerDTO dto) {
        if (customerRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Customer already exists");
        }

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());

        address = addressRepo.save(address);

        Customer c = new Customer();
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        c.setAddress(address);

        return mapToDTO(customerRepo.save(c));
    }

    // ================= GET =================
    public CustomerDTO getCustomer(int id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return mapToDTO(c);
    }

    // ================= UPDATE =================
    public CustomerDTO updateCustomer(int id, CustomerDTO dto) {

        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());

        Address a = c.getAddress();

        if (a == null) {
            a = new Address();
        }

        a.setStreet(dto.getStreet());
        a.setCity(dto.getCity());
        a.setState(dto.getState());
        a.setPincode(dto.getPincode());

        a = addressRepo.save(a);
        c.setAddress(a);

        return mapToDTO(customerRepo.save(c));
    }

    // ================= ADDRESS =================
    public Address getAddress(int customerId) {

        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return c.getAddress();
    }

    public Address addAddress(int customerId, Address address) {

        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Address saved = addressRepo.save(address);
        c.setAddress(saved);

        customerRepo.save(c);

        return saved;
    }

    public Address updateAddress(int id, Address updated) {

        Address a = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        a.setStreet(updated.getStreet());
        a.setCity(updated.getCity());
        a.setState(updated.getState());
        a.setPincode(updated.getPincode());

        return addressRepo.save(a);
    }

    // ================= MAPPER =================
    private CustomerDTO mapToDTO(Customer c) {

        Address a = c.getAddress();

        return new CustomerDTO(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getPhone(),
                a != null ? a.getStreet() : null,
                a != null ? a.getCity() : null,
                a != null ? a.getState() : null,
                a != null ? a.getPincode() : null
        );
    }
}