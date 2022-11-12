package com.example.monitoring.api;

import com.example.monitoring.common.exception.IllegalDateException;
import com.example.monitoring.signup.dto.SignUpRequest;
import com.example.monitoring.signup.service.SignUpService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signup")
public class SignUpController {
    private final SignUpService signUpService;

    @GetMapping("/count")
    public ResponseEntity<Long> signCount(@Valid SignUpRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalDateException();
        }
        Long count = signUpService.getCount(request);
        return ResponseEntity.ok().body(count);
    }
}
