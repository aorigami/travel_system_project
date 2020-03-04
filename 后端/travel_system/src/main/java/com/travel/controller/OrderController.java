package com.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.pojo.OrdersVo;
import com.travel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //查询所有订单，并分页
    @GetMapping("/findAll")
    public ResponseEntity<PageInfo<OrdersVo>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows
    ) {
        List<OrdersVo> orders = orderService.findAll(page, rows);
        if (CollectionUtils.isEmpty(orders)) {
            return ResponseEntity.notFound().build();
        }

        PageInfo<OrdersVo> pageInfo = new PageInfo<>(orders);
        return ResponseEntity.ok(pageInfo);
    }

    //根据orderId 查出对应的 会员、旅行者、商品、当前订单
    @GetMapping("/findProAndMemAndTraByOid/{orderId}")
    public ResponseEntity<OrdersVo> findProAndMemAndTraByOid(@PathVariable String orderId){
       OrdersVo order =  orderService.findProAndMemAndTraByOid(orderId);
       if (order ==null || "".equals(order)){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(order);
    }

    //批量删除订单
    @DeleteMapping("/delOrder/{ids}")
    public ResponseEntity<Void> delOrder(@PathVariable("ids")List<String> orderIds){
        orderService.delOrder(orderIds);
        return ResponseEntity.noContent().build();
    }

}
