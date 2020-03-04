package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.OrdersVo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrdersVo> {
}
