package com.example.monitoring.api;

import static com.example.monitoring.common.util.Validator.checkError;

import com.example.monitoring.cancelOrder.dto.CancelOrderRequest;
import com.example.monitoring.cancelOrder.dto.CancelOrderResponse;
import com.example.monitoring.cancelOrder.dto.CancelOrderSearchRequest;
import com.example.monitoring.cancelOrder.dto.CancelPercentRequest;
import com.example.monitoring.cancelOrder.dto.CancelPercentResponse;
import com.example.monitoring.cancelOrder.dto.MustGradeRequest;
import com.example.monitoring.cancelOrder.service.CancelOrderService;
import com.example.monitoring.common.exception.IllegalDateException;
import com.example.monitoring.common.exception.NoMisMatchGradeException;
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

//TODO : Slf4j 삭제
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order/cancel")
public class CancelOrderController {
    private final CancelOrderService cancelOrderService;

    @GetMapping()
    public ResponseEntity<Long> getCountOrder(@Valid CancelOrderRequest request, BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        return ResponseEntity.ok().body(cancelOrderService.getCountOrder(request));
    }

    @GetMapping("/count/grade")
    public ResponseEntity<Long> countGradeEquals(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(cancelOrderService.countGradeEquals(request));
    }

    @GetMapping("/count/grade/more")
    public ResponseEntity<Long> countGradeMoreThan(@Valid MustGradeRequest request, BindingResult result) {
        checkError(result, () -> new NoMisMatchGradeException());
        return ResponseEntity.ok().body(cancelOrderService.countGradeMoreThan(request));
    }

    @GetMapping("/percent")
    public ResponseEntity<List<CancelPercentResponse>> getFailOrderResponse(@Valid CancelPercentRequest request,
                                                                            BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        return ResponseEntity.ok().body(cancelOrderService.getCancelPercent(request));
    }

    @PostMapping()
    public ResponseEntity<Void> addCancelOrderRecord(@Valid @RequestBody CancelOrderRequest request,
                                                     BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        cancelOrderService.addOrderRecord(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CancelOrderResponse>> searchByName(@Valid CancelOrderSearchRequest request,
                                                                  BindingResult result) {
        checkError(result, () -> new IllegalDateException());
        List<CancelOrderResponse> searchList = cancelOrderService.searchByName(request);
        return ResponseEntity.ok().body(searchList);
    }

}
