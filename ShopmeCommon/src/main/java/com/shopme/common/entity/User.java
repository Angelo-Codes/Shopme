package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64, nullable= false)
	private String password;
	
	@Column(name = "firstname", length = 45, nullable = false)
	private String firstname;
	
	@Column(name = "lastname", length = 45, nullable = false)
	private String lastname;
	
	@Column(length = 64)
	private String photos;
	
	private boolean enable;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String email, String password, String firstname, String lastname) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", roles=" + roles + "]";
	}

	@Transient
	public String photosImagePath() {
		if (id == null || photos == null) return "/images/default-image.png";
		return "/user-photos/" + this.id + "/" + this.photos;
 	}

	 @Transient
	public String getFullName() {
		return firstname + " " + lastname;
 	}

}
