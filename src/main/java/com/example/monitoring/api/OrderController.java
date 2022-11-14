package com.example.monitoring.api;

import static com.example.monitoring.common.util.Validator.checkError;

import com.example.monitoring.common.exception.IllegalDateException;
import com.example.monitoring.common.exception.NoMisMatchGradeException;
import com.example.monitoring.order.dto.MustGradeRequest;
import com.example.monitoring.order.dto.OrderRequest;
import com.example.monitoring.order.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<Long> getCountOrder(@Valid OrderRequest request, BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        return ResponseEntity.ok().body(orderService.getCountOrder(request));
    }

    @GetMapping("/count/grade")
    public ResponseEntity<Long> countGradeEquals(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(orderService.countGradeEquals(request));
    }
//
//    @GetMapping("/count/grade/more")
//    public ResponseEntity<Long> countGradeMoreThan(@Valid MustGradeRequest request, BindingResult result) {
//        checkError(result, () -> new NoMisMatchGradeException());
//        return ResponseEntity.ok().body(addCartService.countGradeMoreThan(request));
//    }
//
//    @GetMapping("/fail")
//    public ResponseEntity<List<FailAddCartResponse>> getFailCartResponse(@Valid FailAddCartRequest request,
//                                                                         BindingResult result) {
//        checkError(result, () -> new IllegalDateException());
//        return ResponseEntity.ok().body(addCartService.getFailAddCarts(request));
//    }
//
//    @PostMapping()
//    public ResponseEntity<Void> addAddCartRecord(@Valid @RequestBody AddCartRequest request,
//                                                 BindingResult result) {
//        checkError(result, () -> new IllegalDateException());
//        addCartService.addAddCartRecord(request);
//        return ResponseEntity.ok().build();
//    }

}
