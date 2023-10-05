package com.shopme.admin.setting.country;

import com.shopme.common.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CountryRepositroyTest {
    @Autowired
    private CountryRepository repo;

    @Test
    public void testCreateCountry() {
        Country country = repo.save(new Country("china", "CN"));
        assertThat(country).isNotNull();
        assertThat(country.getId()).isGreaterThan(0);
    }

    @Test
    public void testListCountries() {
        List<Country> listCountries = repo.findAllByOrderByNameAsc();
        listCountries.forEach(System.out::println);
        assertThat(listCountries.size()).isEqualTo(1);
        assertThat(listCountries.size()).isGreaterThan(0);
    }

    @Test
    public void testUpdateCountries() {
        Integer id = 1;
        String name = "Republic of India";

        Country country = repo.findById(id).get();
        country.setName(name);

        Country updateCountry = repo.save(country);
        assertThat(updateCountry.getName()).isEqualTo(name);
    }

    @Test
    public void testGetCountry() {
        Integer id = 1;
        Country country = repo.findById(id).get();
        assertThat(country).isNotNull();
    }

    @Test
    public void testDeleteCountry() {
        Integer id = 1;
        repo.deleteById(id);
        Optional<Country> findByid = repo.findById(id);
        assertThat(findByid.isEmpty());
    }
}
