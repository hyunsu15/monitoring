package com.example.monitoring.api;

import static com.example.monitoring.common.util.Validator.checkError;

import com.example.monitoring.common.exception.IllegalDateException;
import com.example.monitoring.common.exception.NoMisMatchGradeException;
import com.example.monitoring.common.util.grade.GradeRouter;
import com.example.monitoring.showProduct.dto.MustGradeRequest;
import com.example.monitoring.showProduct.dto.ShowProductRequest;
import com.example.monitoring.showProduct.service.ShowProductService;
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

//TODO : Slf4j 삭제
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Slf4j
public class ShowProductController {
    private final GradeRouter gradeRouter;
    private final ShowProductService showProductService;

    @GetMapping("/count")
    public ResponseEntity<Long> countShowProduct(@Valid ShowProductRequest request, BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        return ResponseEntity.ok().body(showProductService.countShowProduct(request));
    }

    @GetMapping("/count/grade")
    public ResponseEntity<Long> countGradeEquals(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(showProductService.countGradeEquals(request));
    }

    @GetMapping("/count/grade/more")
    public ResponseEntity<Long> countGradeMoreThan(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(showProductService.countGradeMoreThan(request));
    }

    @PostMapping
    public ResponseEntity<Long> addShowProductRecord(@Valid @RequestBody ShowProductRequest request) {
        showProductService.addShowProductRecord(request);
        return ResponseEntity.ok().build();
    }
}
