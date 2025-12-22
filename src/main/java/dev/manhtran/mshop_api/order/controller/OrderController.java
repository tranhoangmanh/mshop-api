package dev.manhtran.mshop_api.order.controller;

import dev.manhtran.mshop_api.order.dto.OrderResponse;
import dev.manhtran.mshop_api.order.dto.PlaceOrderRequest;
import dev.manhtran.mshop_api.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            OrderResponse order = orderService.placeOrder(userEmail, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        List<OrderResponse> orders = orderService.getUserOrders(userEmail);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            OrderResponse order = orderService.getOrderById(id, userEmail);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
