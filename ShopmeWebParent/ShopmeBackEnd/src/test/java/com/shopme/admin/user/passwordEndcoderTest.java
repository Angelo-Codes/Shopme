package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordEndcoderTest {

	@Test
	public void testEcodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rowPassword = "llllllll";
        String encodedPassword = passwordEncoder.encode(rowPassword);

        System.out.println(encodedPassword);

        boolean matches = passwordEncoder.matches(rowPassword, encodedPassword);
        assertThat(matches).isTrue();

    }
}