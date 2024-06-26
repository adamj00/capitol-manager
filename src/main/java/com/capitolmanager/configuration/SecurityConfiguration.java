

package com.capitolmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.capitolmanager.user.application.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String MANAGER = "MANAGER";
	private static final String EMPLOYEE = "EMPLOYEE";

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {

		Assert.notNull(customUserDetailsService, "customUserDetailsService must not be null");

		this.customUserDetailsService = customUserDetailsService;
	}

	public SecurityConfiguration(boolean disableDefaults, CustomUserDetailsService customUserDetailsService) {

		super(disableDefaults);
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()
			.authorizeRequests()

			.antMatchers("/user-edit**").hasRole(MANAGER)
			.antMatchers("/position**").hasRole(MANAGER)
			.antMatchers("/stage-edit**").hasRole(MANAGER)
			.antMatchers("/show-edit**").hasRole(MANAGER)
			.antMatchers("/events**").hasRole(MANAGER)
			.antMatchers("/scheduleEdit**").hasRole(MANAGER)
			.antMatchers("/availability**").hasRole(EMPLOYEE)
			.antMatchers("/scheduleList**").hasRole(EMPLOYEE)
			.antMatchers("/schedule").hasRole(EMPLOYEE)
			.antMatchers("/payroll/manager**").hasRole(MANAGER)

			.anyRequest().authenticated()
			.and()
			.formLogin().permitAll()
			.and()
			.logout().permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}