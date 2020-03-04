package com.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.pojo.Product;
import com.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //查询所有产品，并分页
    @GetMapping("/findAll")
    public ResponseEntity<PageInfo<Product>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows) {

        List<Product> products = productService.findAll(page, rows);
        if (CollectionUtils.isEmpty(products)) {
            return ResponseEntity.notFound().build();
        }

        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return ResponseEntity.ok(pageInfo);
    }

    //添加产品
    @PostMapping("/addProduct")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {

        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //批量删除产品
    @DeleteMapping("/delProducts/{ids}")
    public ResponseEntity<Void> delProducts(@PathVariable("ids") List<String> productIds) {
        productService.delProducts(productIds);
        return ResponseEntity.noContent().build();
    }

    //批量开启或关闭产品状态
    @PutMapping("/setProductStatus")
    public ResponseEntity<Void> setProductStatus(
            @RequestParam("ids") List<String> productIds,
            @RequestParam Integer status) {
        productService.setProductStatus(productIds, status);
        return ResponseEntity.noContent().build();
    }
}
