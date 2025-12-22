package dev.manhtran.mshop_api.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.manhtran.mshop_api.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private String phone;
    private String note;
    @JsonFormat(pattern = "HH:mm:ss - dd/MM/yyyy", timezone = "Asia/Bangkok")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss - dd/MM/yyyy", timezone = "Asia/Bangkok")
    private LocalDateTime updatedAt;

    public static OrderResponse fromEntity(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setUserEmail(order.getUser().getEmail());
        response.setItems(order.getOrderItems().stream()
                .map(OrderItemResponse::fromEntity)
                .collect(Collectors.toList()));
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setShippingAddress(order.getShippingAddress());
        response.setPhone(order.getPhone());
        response.setNote(order.getNote());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        return response;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;

        public static OrderItemResponse fromEntity(dev.manhtran.mshop_api.order.entity.OrderItem item) {
            OrderItemResponse response = new OrderItemResponse();
            response.setId(item.getId());
            response.setProductId(item.getProduct().getId());
            response.setProductName(item.getProduct().getName());
            response.setQuantity(item.getQuantity());
            response.setPrice(item.getPrice());
            response.setSubtotal(item.getSubtotal());
            return response;
        }
    }
}
