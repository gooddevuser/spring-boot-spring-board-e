package com.springdemo.bootboard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springdemo.bootboard.security.SpringBoardNoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/").permitAll()
//			.antMatchers("/home").permitAll()
//			.antMatchers("/css/**").permitAll()
//			.antMatchers("/webjars/**").permitAll()
//			.antMatchers("/**").authenticated();		
//		http.formLogin();		
//		http.logout();		
//		http.csrf().disable();
		
		http	.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/**").authenticated()
			.and()
				.formLogin()
			.and()
				.logout()
			.and()
				.csrf().disable();
	}
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SpringBoardNoOpPasswordEncoder();
		//return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("iamuserone").password("{noop}9922").roles("MEMBER")
//			.and()
//			.withUser("iamadminone").password("{noop}9922").roles("ADMIN");
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT user_name, passwd, enabled FROM tbl_users WHERE user_name = ?")
			.authoritiesByUsernameQuery("SELECT user_name, role_name FROM tbl_users_roles WHERE user_name = ?")
			.passwordEncoder(passwordEncoder());
			
		
	}
	
}








