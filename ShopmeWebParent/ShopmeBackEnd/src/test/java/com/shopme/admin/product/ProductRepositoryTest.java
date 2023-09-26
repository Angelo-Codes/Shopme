package com.shopme.admin.product;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreatProduct(){
        Brand brand = testEntityManager.find(Brand.class, 37);
        Category category = testEntityManager.find(Category.class, 5);

        Product product = new Product();
        product.setName("Acer Aspire Desktop");
        product.setAlias("acer_aspire_desktop");
        product.setShortDescription("Short Description");
        product.setFullDescription("full Description");
        product.setMainImage("dsfdsaf.png");
        product.addExtraImage("dsfasdf.tre");
        product.addExtraImage("fsdfdsaf.png");

        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(3424);
        product.setCost(53245);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());
        Product saveProduct = repo.save(product);

        assertThat(saveProduct).isNotNull();
        assertThat(saveProduct.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllProduct() {
        Iterable<Product> listAll = repo.findAll();
        listAll.forEach(System.out::println);
    }

    @Test
    public void testGetProduct() {
        Integer id = 2;
        Product product = repo.findById(id).get();
        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct() {
        Integer id = 2;
        Product product = repo.findById(id).get();
        product.setPrice(123213213);
        repo.save(product);

        Product updateProduct = testEntityManager.find(Product.class, id);
        assertThat(updateProduct.getPrice()).isEqualTo(123213213);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 2;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(!result.isPresent());
    }

    @Test
    public void testSaveProductWithImages() {
        Integer id = 1;
        Product product = repo.findById(id).get();


        assertThat(product.getImages().size()).isEqualTo(2);
    }
}
