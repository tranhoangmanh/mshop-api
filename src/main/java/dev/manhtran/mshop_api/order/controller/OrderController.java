package dev.manhtran.mshop_api.order.controller;

import dev.manhtran.mshop_api.order.dto.OrderResponse;
import dev.manhtran.mshop_api.order.dto.PlaceOrderRequest;
import dev.manhtran.mshop_api.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request, Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponse order = orderService.placeOrder(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        List<OrderResponse> orders = orderService.getUserOrders(userEmail);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponse order = orderService.getOrderById(id, userEmail);
        return ResponseEntity.ok(order);
    }
}
