package dev.manhtran.mshop_api.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {
    private List<OrderItemRequest> items;
    private String shippingAddress;
    private String phone;
    private String note;
}
