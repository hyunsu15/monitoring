package com.example.monitoring.api;

import com.example.monitoring.addCart.dto.AddCartRequest;
import com.example.monitoring.addCart.service.AddCartService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

//2. 기한안에 회원등급이상 사람이 담은 수
//3. 기한안에 특정 등급인 사람이 담은 수
//4. 기한안에 장바구니가 ~% 실패한경우

}
