package com.kuriosity.kcc.service;

import com.kuriosity.kcc.model.Order;

public interface OrderService {

    Order createOrder(Order orderRequest);
    Order getOrderById(Long orderId);
    Order updateOrder(Long orderId, Order updatedOrder);
    void deleteOrder(Long orderId);
}
