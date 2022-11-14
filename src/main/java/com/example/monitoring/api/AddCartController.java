package com.example.monitoring.api;

import static com.example.monitoring.common.util.Validator.checkError;

import com.example.monitoring.addCart.dto.AddCartRequest;
import com.example.monitoring.addCart.service.AddCartService;
import com.example.monitoring.common.exception.NoMisMatchGradeException;
import com.example.monitoring.showProduct.dto.MustGradeRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO : Slf4j 삭제
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/addCart")
public class AddCartController {
    private final AddCartService addCartService;

    @GetMapping()
    public ResponseEntity<Long> getCountWish(@Valid AddCartRequest request) {
        return ResponseEntity.ok().body(addCartService.getCountWish(request));
    }

    @GetMapping("/count/grade")
    public ResponseEntity<Long> countGradeEquals(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(addCartService.countGradeEquals(request));
    }

//3. 기한안에 특정 등급인 사람이 담은 수
//4. 기한안에 장바구니가 ~% 실패한경우

}
