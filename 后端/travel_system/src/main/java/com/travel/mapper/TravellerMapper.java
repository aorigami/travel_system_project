package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravellerMapper extends BaseMapper<Traveller> {

    @Select("select t.* from traveller t,order_traveller ot where ot.orderId = #{orderId} and ot.travellerId = t.id")
    List<Traveller> findByOrderId(String orderId);
}
