package com.eval.coronakit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * @Autowired AuthenticationSuccessHandler successHandler;
	 */

	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	
	 	UserBuilder builder =  User.withDefaultPasswordEncoder();
	 	auth.inMemoryAuthentication()
	 		.withUser(builder.username("Admin").password("admin").roles("ADMIN"))
	 		.withUser(builder.username("First").password("abc").roles("USER"))
	 		.withUser(builder.username("Second").password("abc").roles("USER"));
	} 
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/").permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		//.anyRequest().authenticated()
		.antMatchers("/admin-home/**").hasRole("ADMIN")
		.antMatchers("/user-home/**").hasRole("USER")
		//.antMatchers("/systems/**").hasRole("ADMIN")
		.and().formLogin()
		.loginPage("/")
		.loginProcessingUrl("/validate")
		//.successHandler(succe	ssHandler)
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		
		.exceptionHandling().accessDeniedPage("/custom-error");
	}
	
}

