package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.mapper.ProductMapper;
import com.travel.pojo.Product;
import com.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    //查询所有产品，并分页
    @Override
    public List<Product> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return productMapper.selectList(null);
    }

    //添加产品
    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    //批量删除产品
    @Transactional
    @Override
    public void delProducts(List<String> productIds) {
        productMapper.deleteBatchIds(productIds);
    }

    //批量开启或关闭产品状态
    @Transactional
    @Override
    public void setProductStatus(List<String> productIds, Integer status) {
        Product product = new Product();
        productIds.forEach(id -> {
            product.setId(id);
            product.setProductStatus(status);
            productMapper.updateById(product);
        });

    }
}
