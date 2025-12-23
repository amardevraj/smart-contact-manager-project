package com.smart.config;

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
public class MyConfig {

	@Bean
	UserDetailsService getUserDetailsService() {

		return new UserDetailsServiceImpl();
	}

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	// configuration of authentication provider

	@Bean
	DaoAuthenticationProvider authenticatorProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

		return daoAuthenticationProvider;
	}

	// configure method
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// here we configure URLs and decide which urls will be public and private

		httpSecurity.authorizeHttpRequests(authorize -> {
			// authorize.requestMatchers("/","/signup","/do_register").permitAll();
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});

		// form login default
		// if we need to change anything related to form we will come here

		
		//httpSecurity.formLogin(Customizer.withDefaults());
		httpSecurity.formLogin(formLogin->{
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/dashboard");
			//formLogin.failureForwardUrl("/login?error=true");
			
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
		});
		
		httpSecurity.logout(logoutForm->{
			logoutForm.logoutUrl("/logout");
			logoutForm.logoutSuccessUrl("/login?logout=true");
		});
		return httpSecurity.build();
	}
}
