package com.panda.controller;

import com.panda.dto.AuthenticationDto;
import com.panda.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/add-auth")
    public void addAuth(@RequestBody AuthenticationDto authenticationDto,
                        @RequestParam("employeeId") UUID employeeId,
                        BindingResult bindingResult) {

        authenticationService.add(authenticationDto, employeeId, bindingResult);
    }

    @PutMapping("/update-auth")
    public void updateAuth(@RequestBody AuthenticationDto authenticationDto) {
        authenticationService.update(authenticationDto);
    }
}
