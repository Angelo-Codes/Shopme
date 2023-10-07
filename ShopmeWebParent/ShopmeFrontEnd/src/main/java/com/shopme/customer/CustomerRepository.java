package com.shopme.customer;

import com.shopme.common.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);
    @Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
    public Customer findByVerificationCode(String code);
    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id = ?1")
    public void enabled(Integer id);
}