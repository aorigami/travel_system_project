package com.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orders")
public class OrdersVo extends Orders {
    private String productId;
    private String memberId;
}
