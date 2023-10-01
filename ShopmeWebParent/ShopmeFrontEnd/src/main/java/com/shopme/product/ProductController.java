package com.shopme.product;

import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/c/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model){
        return viewCategoryByPage(alias, 1, model);
    }
    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_alias") String alias, @PathVariable("pageNum") int pageNum, Model model) {
        Category category = categoryService.getCategory(alias);
        if (category == null) {
            return "error/404";
        }
        List<Category> listCategoryParent = categoryService.getCategoryParents(category);
        Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
        List<Product> listProdutcs = pageProducts.getContent();

        long startCount = (pageNum - 1) + ProductService.PRODUCT_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCT_PER_PAGE - 1;
        if (endCount > pageProducts.getTotalElements()) {
            endCount = pageProducts.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", pageProducts.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("pageTitle", category.getName());
        model.addAttribute("endCount", endCount);
        model.addAttribute("listCategoryParent", listCategoryParent);
        model.addAttribute("listProducts", listProdutcs);
        model.addAttribute("category", category);

        return "products_by_category";

    }

}