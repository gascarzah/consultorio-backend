package com.gafahtec.consultorio.config;

import static com.gafahtec.consultorio.enums.Permission.ADMIN_CREATE;
import static com.gafahtec.consultorio.enums.Permission.ADMIN_DELETE;
import static com.gafahtec.consultorio.enums.Permission.ADMIN_READ;
import static com.gafahtec.consultorio.enums.Permission.ADMIN_UPDATE;
import static com.gafahtec.consultorio.enums.Permission.MANAGER_CREATE;
import static com.gafahtec.consultorio.enums.Permission.MANAGER_DELETE;
import static com.gafahtec.consultorio.enums.Permission.MANAGER_READ;
import static com.gafahtec.consultorio.enums.Permission.MANAGER_UPDATE;
import static com.gafahtec.consultorio.enums.Role.ADMIN;
import static com.gafahtec.consultorio.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
        		
        		 "/auth/**",
        		"/usuarios/**",
        		"/empleados/**",
        		"/citas/**",
        		"/clientes/**",
        		"/empresas/**",
        		"/historiasClinicas/**",
        		"/horarios/**",
        		"/menus/**",
        		"/programaciones/**",
        		"/roles/**",
        		"/programacionesDetalladas/**",
        		"/usuarios/**",

                "/swagger-ui/**",
                "/webjars/**",
				"/v3/api-docs/**",
				"/swagger-ui.html"

		)
          .permitAll()
        
          .requestMatchers(GET, "/usuarios/").permitAll()
          .requestMatchers(POST, "/usuarios/").permitAll()
          .requestMatchers(PUT, "/usuarios/").permitAll()
          .requestMatchers(GET, "/**").permitAll()
          .requestMatchers(POST, "/**").permitAll()
          .requestMatchers(PUT, "/**").permitAll()
//          .requestMatchers("/usuarios/**").hasRole("ADMIN")
//          .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
//        .requestMatchers("/usuarios/**").hasAnyRole(ADMIN.name(), MANAGER.name())


//        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())


       /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

        .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
        .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
        .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
        .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/


        .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/v1/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
}
