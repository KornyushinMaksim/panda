package com.panda.controller;

import com.panda.dto.CustomerDto;
import com.panda.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add-customer")
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }

    @DeleteMapping("/delete-customer")
    public void deleteCustomer(@RequestParam UUID customerId) {
        customerService.deleteCustomer(customerId);
    }
}
