package com.petshop;

import com.petshop.dto.CustomerDTO;
import com.petshop.dto.LoginRequest;
import com.petshop.entity.Address;
import com.petshop.entity.Customer;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.AddressRepository;
import com.petshop.repository.CustomerRepository;

import com.petshop.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private AddressRepository addressRepo;

    @InjectMocks
    private CustomerService service;

    private Customer customer;
    private Address address;
    private CustomerDTO dto;

    @BeforeEach
    void setup() {
        address = new Address();
        address.setId(1);
        address.setStreet("Street 1");
        address.setCity("Pune");
        address.setState("MH");
        address.setPincode("411001");

        customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@test.com");
        customer.setPhone("1234567890");
        customer.setAddress(address);

        dto = new CustomerDTO(
                1,
                "John",
                "Doe",
                "john@test.com",
                "1234567890",
                "Street 1",
                "Pune",
                "MH",
                "411001"
        );
    }

    // ================= LOGIN =================

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest("john@test.com", "1234567890");

        when(customerRepo.findByEmail("john@test.com"))
                .thenReturn(Optional.of(customer));

        String result = service.login(request);

        assertThat(result).isEqualTo("Login successful");
    }

    @Test
    void login_invalidEmail() {
        LoginRequest request = new LoginRequest("wrong@test.com", "123");

        when(customerRepo.findByEmail("wrong@test.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    void login_wrongPhone() {
        LoginRequest request = new LoginRequest("john@test.com", "999");

        when(customerRepo.findByEmail("john@test.com"))
                .thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid credentials");
    }

    // ================= REGISTER =================

    @Test
    void registerCustomer_success() {
        when(addressRepo.save(any(Address.class))).thenReturn(address);
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = service.registerCustomer(dto);

        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getCity()).isEqualTo("Pune");
    }

    // ================= GET =================

    @Test
    void getCustomer_success() {
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));

        CustomerDTO result = service.getCustomer(1);

        assertThat(result.getEmail()).isEqualTo("john@test.com");
    }

    @Test
    void getCustomer_notFound() {
        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCustomer(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found");
    }

    // ================= UPDATE =================

    @Test
    void updateCustomer_success() {
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(addressRepo.save(any(Address.class))).thenReturn(address);
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = service.updateCustomer(1, dto);

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(customerRepo).save(customer);
    }

    @Test
    void updateCustomer_notFound() {
        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateCustomer(1, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found");
    }

    // ================= ADDRESS =================

    @Test
    void getAddress_success() {
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));

        Address result = service.getAddress(1);

        assertThat(result.getCity()).isEqualTo("Pune");
    }

    @Test
    void addAddress_success() {
        Address newAddress = new Address();
        newAddress.setCity("Mumbai");

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(addressRepo.save(any(Address.class))).thenReturn(newAddress);

        Address result = service.addAddress(1, newAddress);

        assertThat(result.getCity()).isEqualTo("Mumbai");
        verify(customerRepo).save(customer);
    }

    @Test
    void updateAddress_success() {
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(addressRepo.save(any(Address.class))).thenReturn(address);

        Address updated = new Address();
        updated.setCity("Delhi");

        Address result = service.updateAddress(1, updated);

        assertThat(result.getCity()).isEqualTo("Delhi");
    }

    @Test
    void updateAddress_notFound() {
        when(addressRepo.findById(1)).thenReturn(Optional.empty());

        Address updated = new Address();

        assertThatThrownBy(() -> service.updateAddress(1, updated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Address not found");
    }
}