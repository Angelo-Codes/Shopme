package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository repo;
    @Test
    public void testCreatingFirstBrand1() {
        Category loptap = new Category(1);
        Brand acer = new Brand("Acer");
        Brand saveBrand = repo.save(acer);
        acer.getCategories().add(loptap);

        assertThat(saveBrand).isNotNull();
        assertThat(saveBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand2() {
        Category cellphone = new Category(4);
        Category tablet = new Category(7);

        Brand apple = new Brand("Apple");
        apple.getCategories().add(cellphone);
        apple.getCategories().add(tablet);

        Brand saveBrands = repo.save(apple);
        assertThat(saveBrands).isNotNull();
        assertThat(saveBrands.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3() {
        Brand samsung = new Brand("Samsung");
        samsung.getCategories().add(new Category(29));
        samsung.getCategories().add(new Category(24));

        Brand savetoBrand = repo.save(samsung);
        assertThat(savetoBrand).isNotNull();
        assertThat(savetoBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByID() {
        Brand findid = repo.findById(4).get();
        assertThat(findid.getName()).isEqualTo("Acer");
    }

    @Test void testFindAll() {
        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotNull();
    }

    @Test
    public void testUpdateName() {
        String newName = "Acer Electronics";
        Brand acer = repo.findById(4).get();
        acer.setName(newName);

        Brand save = repo.save(acer);
        assertThat(save.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete() {
        Integer id = 4;
        repo.deleteById(id);
        Optional<Brand> result = repo.findById(id);
        assertThat(result).isEmpty();
    }
}
