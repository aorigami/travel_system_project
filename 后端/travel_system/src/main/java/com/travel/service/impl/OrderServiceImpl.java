package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.mapper.MemberMapper;
import com.travel.mapper.OrderMapper;
import com.travel.mapper.ProductMapper;
import com.travel.mapper.TravellerMapper;
import com.travel.pojo.Member;
import com.travel.pojo.OrdersVo;
import com.travel.pojo.Product;
import com.travel.pojo.Traveller;
import com.travel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private TravellerMapper travellerMapper;

    //查询所有订单，并分页
    @Override
    public List<OrdersVo> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<OrdersVo> orders = orderMapper.selectList(null);
        orders.forEach(order -> {
            order.setProduct(productMapper.selectById(order.getProductId()));
        });
        return orders;
    }

    //根据order id 查出对应的 当前订单、会员、旅行者、商品
    @Override
    public OrdersVo findProAndMemAndTraByOid(String orderId) {
        OrdersVo order = orderMapper.selectById(orderId);

        Member member = memberMapper.selectById(order.getMemberId());
        order.setMember(member);

        List<Traveller> travellers = travellerMapper.findByOrderId(orderId);
        order.setTravellers(travellers);

        Product product = productMapper.selectById(order.getProductId());
        order.setProduct(product);

        return order;
    }

    //批量删除订单
    @Override
    public void delOrder(List<String> orderIds) {
        orderMapper.deleteBatchIds(orderIds);
    }
}
