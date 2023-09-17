package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userAngelo = new User("delac@gmail.com", "llldfl", "angelo", "dela");
		userAngelo.addRole(roleAdmin);

		User saveUser = repo.save(userAngelo);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRole() {
		User userjaje = new User("jaje@gmail.com", "llll", "jaje", "masinopa");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userjaje.addRole(roleEditor);
		userjaje.addRole(roleAssistant);
		
		User saveUser = repo.save(userjaje);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User username = repo.findById(1).get();
		System.out.print(username);
		assertThat(username).isNotNull();
	}

	@Test
	public void testUpdateUserDetail() {
		User username = repo.findById(2).get();
		username.setEnable(true);
		username.setEmail("angelos@gmail.com");
		
		repo.save(username);
	}

	@Test
	public void testUpdateUserRoles() {
		User userAngelo = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		userAngelo.getRoles().remove(roleEditor);
		userAngelo.addRole(roleSalesPerson);
	}

	@Test
	public void testDeleteUser() {
		Integer UserId = 2;
		repo.deleteById(UserId);
	}

	@Test
	public void testGetUserByEmail() {
		String email = "delac@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnableStatus(id, false);
	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<User> page = repo.findAll(pageable);
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}

	@Test
	public void testSearchUser() {
		String firstName = "bruce";
		int pageNumber = 0;
		int pageSize = 1;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<User> page = repo.findAll(firstName, pageable);
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
}
