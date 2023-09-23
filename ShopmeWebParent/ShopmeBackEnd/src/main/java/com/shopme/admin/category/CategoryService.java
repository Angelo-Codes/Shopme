package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CategoryService {

    public static final int ROOT_CATEGORIES_PER_PAGE = 4;

    @Autowired
    private CategoryRepository repo;

    public List<Category> lisByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir, String keyword) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);
        Page<Category> pageCategories = null;
        if (keyword != null && !keyword.isEmpty()) {
            pageCategories = repo.search(keyword, pageable);
        } else {
            pageCategories = repo.findRootCategory(pageable);
        }
        List<Category> rootCategories = pageCategories.getContent();
        pageInfo.setTotalElements(pageCategories.getTotalElements());
        pageInfo.setTotalPages(pageCategories.getTotalPages());
        if (keyword != null && !keyword.isEmpty()) {
            List<Category> searchResult = pageCategories.getContent();
            for (Category category : searchResult) {
                category.setHasChildren(category.getChildren().size() > 0);
            }
            return searchResult;
        } else {
            return listHerarchicalCategory(rootCategories, sortDir);
        }
  }

    public List<Category> listCategory() {
        return (List<Category>) repo.findAll();
    }

    public Category getId(Integer id) throws CategoryNotfoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotfoundException("could not find any category with ID " + id);
        }
    }

    public String checkUnigue(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = repo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = repo.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }
            Category categoryByAlias = repo.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildrend = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if (sortDir.equals("asc")) {
                    return cat2.getName().compareTo(cat2.getName());
                } else {
                    return cat2.getName().compareTo(cat1.getName());
                }
            }
        });
        sortedChildrend.addAll(children);
        return sortedChildrend;
    }


    public void delete(Integer id) throws CategoryNotfoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotfoundException("could not find any category with ID " + id);
        }
        repo.deleteById(id);
    }

    public void updateCategoriesEnableStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    private List<Category> listHerarchicalCategory(List<Category> rootCategories, String sortDir) {
        List<Category> hierachicalCategory = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierachicalCategory.add(Category.copyFull(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);
            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierachicalCategory.add(Category.copyFull(subCategory, name));

                listSubHierarchicalCategories(hierachicalCategory, subCategory, 1, sortDir);
            }
        }
        return hierachicalCategory;
    }

    public void listSubHierarchicalCategories(List<Category> hierachicalCategory, Category parent, int subLevel, String sortDir) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);

        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            hierachicalCategory.add(Category.copyFull(subCategory, name));

            listSubHierarchicalCategories(hierachicalCategory, subCategory, newSubLevel, sortDir);
        }
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public List<Category> listCategoriesUsedInform() {
        List<Category> categoriesUserInFrom = new ArrayList<>();
        Iterable<Category> categoriesInDB = repo.findRootCategory(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUserInFrom.add(Category.copyIdAndName(category));

                Set<Category> children = sortSubCategories(category.getChildren());

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categoriesUserInFrom.add(Category.copyIdAndName(subCategory.getId(), name));
                    listSubCategoriesUsedInForm(categoriesUserInFrom, subCategory, 1);
                }
            }
        }
        return categoriesUserInFrom;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUserInFrom, Category parent, int sublevel) {
        int newSublevel = sublevel + 1;
        Set<Category> children =  sortSubCategories(parent.getChildren());
        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSublevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            categoriesUserInFrom.add(Category.copyIdAndName(subCategory.getId(), name));

            listSubCategoriesUsedInForm(categoriesUserInFrom, subCategory, newSublevel);
        }
    }
}
