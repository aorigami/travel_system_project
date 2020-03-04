package com.travel.service;

import com.travel.pojo.OrdersVo;

import java.util.List;

public interface OrderService {
    List<OrdersVo> findAll(Integer page, Integer rows);

    OrdersVo findProAndMemAndTraByOid(String orderId);

    void delOrder(List<String> orderIds);
}
