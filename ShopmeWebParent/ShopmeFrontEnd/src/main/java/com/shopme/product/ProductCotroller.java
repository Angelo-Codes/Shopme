package com.shopme.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductCotroller {

    private ProductRepository repo;
    @Autowired
    private ProductService service;

    public String CategoryFirstPage()
}
