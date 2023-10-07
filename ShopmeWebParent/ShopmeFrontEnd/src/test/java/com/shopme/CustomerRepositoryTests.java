package com.shopme;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository repo;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1() {
        Integer countryId = 234;
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstname("David");
        customer.setLastname("Fountaine");
        customer.setPassword("pasww");
        customer.setEmail("da@gmail.com");
        customer.setPhoneNumber("3232-213-32");
        customer.setAddressLine1("1927  West Drive");
        customer.setCity("Sacramento");
        customer.setState("California");
        customer.setPostalCode("95867");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCustomer2() {
        Integer countryId = 106;
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstname("Sanya");
        customer.setLastname("Lad");
        customer.setPassword("password456");
        customer.setEmail("da@lad2020.com");
        customer.setPhoneNumber("02224928052");
        customer.setAddressLine1("73 , A-, Shah & Nahar Indl.estate");
        customer.setAddressLine2("Dhanraj Mill Compound, Lower Parel (we");
        customer.setCity("Mumbai");
        customer.setState("Maharashtra");
        customer.setPostalCode("400013");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testListCustomer() {
        Iterable<Customer> customers = repo.findAll();
        customers.forEach(System.out::println);
        assertThat(customers).hasSizeGreaterThan(1);
    }

    @Test
    public void testUpdateCustomer() {
        Integer customerId = 1;
        String lastName = "Stanfield";
        Customer customer = repo.findById(customerId).get();
        customer.setLastname(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = repo.save(customer);
        assertThat(updatedCustomer.getLastname()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer() {
        Integer customerId = 2;
        Optional<Customer> findById = repo.findById(customerId);

        assertThat(findById).isPresent();
        Customer customer = findById.get();
        System.out.println(customer);
    }

    @Test
    public void testDeleteCustomer() {
        Integer customerId = 2;
        repo.deleteById(customerId);

        Optional<Customer> findById = repo.findById(customerId);
        assertThat(findById).isNotPresent();
    }

    @Test
    public void testFindByEmail() {
        String email = "da@lad2020.com";
        Customer customer = repo.findByEmail(email);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testFindByVerificationCod() {
        String verify = "code_12";
        Customer customer = repo.findByVerificationCode(verify);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testEnabledCustomer() {
        Integer customerId = 1;
        repo.enabled(customerId);

        Customer customer = repo.findById(customerId).get();
        assertThat(customer.isEnabled()).isTrue();
    }

}
