package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import com.shopme.common.exeption.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {

    public static final int PRODUCT_PER_PAGE = 4;
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll() {
        return (List<Product>) repo.findAll();
    }

    public Page<Product> listByPage(int PageNum, String sortField, String sortDir, String keyword, Integer categoryId) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(PageNum - 1, PRODUCT_PER_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
            return repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
        }
        if (categoryId != null && categoryId > 0) {
            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
            return repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
        }
        return repo.findAll(pageable);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replace(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replace(" ", "-"));
        }
        product.setUpdatedTime(new Date());

        return repo.save(product);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Product productName = repo.findByName(name);

        if (isCreatingNew) {
            if (productName != null) return "Duplicate";
        } else {
            if (productName != null || productName.getId() != 0) {
                return "Duplicate";
            }
        }

        return "OK";
    }


    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("could not find any product with ID " + id);
        }
        repo.deleteById(id);
    }

    public Product get(Integer id) throws ProductNotFoundException{
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Could not find any product with ID =" + id);
        }

    }
}
