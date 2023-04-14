package com.gafahtec.consultorio.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity
@EnableScheduling
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder; 


	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.csrf().disable();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.authorizeRequests().antMatchers("**/login/**", "/usuarios/token/refresh","**/usuarios/**").permitAll();
//		http.authorizeRequests().antMatchers("**/usuarios/**").permitAll();
//		http.authorizeRequests().antMatchers("**/usuarios/**").hasAnyAuthority("ROL_SUPER_ADMIN");
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll()
//				.antMatchers(HttpMethod.POST, "/usuarios").permitAll()
//				.antMatchers(HttpMethod.GET, "/roles").permitAll()
//				.antMatchers(HttpMethod.GET, "/empresas").permitAll()
				.anyRequest().authenticated();

		http.addFilter(getCustomAuthenticationFilter())
				.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public CustomAuthenticationFilter getCustomAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());

		customAuthenticationFilter.setFilterProcessesUrl("/login");

		return customAuthenticationFilter;
	}
	
//	@Bean
//	RestAccessDeniedHandler accessDeniedHandler() {
//		return new RestAccessDeniedHandler();
//	}
//
//	@Bean
//	RestAuthenticationEntryPoint authenticationEntryPoint() {
//		return new RestAuthenticationEntryPoint();
//	}
	
}
