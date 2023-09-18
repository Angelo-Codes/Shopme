package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}



	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) ->
				requests.requestMatchers("/images/**", "/js/**", "/webjars/**", "/fontawesome/**", "/style.css/**", "/webfonts/**", "/user-photos/**").permitAll()
						.anyRequest().authenticated()
		).formLogin((form) ->
				form.loginPage("/login")
						.usernameParameter("email")
						.permitAll()
		).logout((formLogout) ->
				formLogout.logoutUrl("/logout")
						.permitAll()
		).rememberMe((form) ->
				form.key("fsdafsdfsdffd_435432543425432")
						.tokenValiditySeconds(7 * 24 * 60 * 60)
						.userDetailsService(userDetailsService())
		);

		return http.build();
	}

}