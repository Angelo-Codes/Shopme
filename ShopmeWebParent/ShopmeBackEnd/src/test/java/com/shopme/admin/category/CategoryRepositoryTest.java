package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateFirstCategory() {
        Category category = new Category("Computer");
        Category saveCategory = repo.save(category);
        assertThat(saveCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategories() {
        Category parent = new Category(1);
        Category subCategory = new Category("Memory", parent);
        Category saveCategory = repo.save(subCategory);
        assertThat(saveCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory() {
        Category category = repo.findById(1).get();
        System.out.println(category);

        Set<Category> children = category.getChildren();

        for (Category subCategory : children) {
            System.out.println(subCategory);
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarhicalCategories() {
        Iterable<Category> categories = repo.findAll();

        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int sublevel) {
        int newSublevel = sublevel + 1;
       Set<Category> children =  parent.getChildren();
        for (Category subCategory : children) {
            for (int i = 0; i < newSublevel; i++) {
                System.out.println("--");
            }

            System.out.println(subCategory.getName());
            printChildren(subCategory, newSublevel);
        }
    }

}
