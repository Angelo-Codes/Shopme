package com.shopme.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @PostMapping("categories/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
        return service.checkUnigue(id, name, alias);
    }
}
