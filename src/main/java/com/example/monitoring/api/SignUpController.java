package com.example.monitoring.api;

import static com.example.monitoring.common.util.Validator.checkError;

import com.example.monitoring.common.exception.IllegalDateException;
import com.example.monitoring.signup.dto.SearchResponse;
import com.example.monitoring.signup.dto.SignUpRequest;
import com.example.monitoring.signup.exception.NullNameException;
import com.example.monitoring.signup.service.SignUpService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO Slf4j 삭제
@RequiredArgsConstructor
@RestController
@RequestMapping("/signup")
@Slf4j
public class SignUpController {
    private final SignUpService signUpService;

    @GetMapping("/count")
    public ResponseEntity<Long> signCount(@Valid SignUpRequest request, BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        Long count = signUpService.getCount(request);
        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchByName(@Valid SignUpRequest request, BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        checkError(request.isNameNull(), () -> new NullNameException());
        List<SearchResponse> searchList = signUpService.searchByName(request);
        return ResponseEntity.ok().body(searchList);
    }

    @PostMapping
    public ResponseEntity<Void> addSignUpRecord(@Valid @RequestBody SignUpRequest request, BindingResult result) {
        signUpService.addSignUpRecord(request);
        return ResponseEntity.ok().build();
    }
}
