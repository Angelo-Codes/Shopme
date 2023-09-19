package com.shopme.admin.category;

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> lisAll() {
        List<Category> rootCategories = repo.findRootCategory();
        return listHerarchicalCategory(rootCategories);
    }

    public List<Category> listCategory() {
        return (List<Category>) repo.findAll();
    }

    public Category getId(Integer id) throws UserNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("could not find any category with ID " + id);
        }
    }


    public void delete(Integer id) throws UserNotFoundException {
        Long counById = repo.countById(id);
        if (counById == null || counById == 0) {
            throw new UserNotFoundException("could not find any category with ID " + id);
        }
        repo.deleteById(id);
    }

    public void updateCategoriesEnableStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    private List<Category> listHerarchicalCategory(List<Category> rootCategories) {
        List<Category> hierachicalCategory = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierachicalCategory.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierachicalCategory.add(Category.copyFull(subCategory, name));

                listSubHierarchicalCategories(hierachicalCategory, subCategory, 1);
            }
        }

        return hierachicalCategory;
    }

    public void listSubHierarchicalCategories(List<Category> hierachicalCategory, Category parent, int subLevel) {
        Set<Category> children = parent.getChildren();

        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            hierachicalCategory.add(Category.copyFull(subCategory, name));

            listSubHierarchicalCategories(hierachicalCategory, subCategory, newSubLevel);
        }

    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public List<Category> listCategoriesUsedInform() {
        List<Category> categoriesUserInFrom = new ArrayList<>();
        Iterable<Category> categoriesInDB = repo.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUserInFrom.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categoriesUserInFrom.add(Category.copyIdAndName(subCategory.getId(), name));
                    listSubCategoriesUsedInForm(categoriesUserInFrom, subCategory, 1);
                }
            }
        }

        return categoriesUserInFrom;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUserInFrom,Category parent, int sublevel) {
        int newSublevel = sublevel + 1;
        Set<Category> children =  parent.getChildren();
        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSublevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            categoriesUserInFrom.add(new Category(name));

            listSubCategoriesUsedInForm(categoriesUserInFrom, subCategory, newSublevel);
        }
    }
}
