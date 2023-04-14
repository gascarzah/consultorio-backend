package com.gafahtec.consultorio.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafahtec.consultorio.dto.request.UsuarioLoginRequest;
import com.gafahtec.consultorio.service.UsuarioService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2

@NoArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{


	private  AuthenticationManager authenticationManager;

	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		

		try {
			UsuarioLoginRequest usuarioLoginRequest = new ObjectMapper().readValue(request.getInputStream(), UsuarioLoginRequest.class);
			log.info(""+usuarioLoginRequest);
			UsernamePasswordAuthenticationToken authenticationToken
					= new UsernamePasswordAuthenticationToken(usuarioLoginRequest.getEmail(), usuarioLoginRequest.getPassword(),new ArrayList<>());

			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	

	

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		User user = (User)authentication.getPrincipal();

		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+ 20 * 60 * 1000))
//				.withExpiresAt(new Date(System.currentTimeMillis()+ 20000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//				.withClaim("empresa", user.getAuthorities().getIdEmpleado())
//				.withClaim("nombreUsuario", usuario.getEmpleado().getApellidoPaterno() + " " + usuario.getEmpleado().getApellidoMaterno() + ", "+usuario.getEmpleado().getNombres())
				.sign(algorithm);
		
		

		
		
		String refresh_token = JWT.create()
		.withSubject(user.getUsername())
		.withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
//		.withExpiresAt(new Date(System.currentTimeMillis() + 20000))
		.withIssuer(request.getRequestURL().toString())
//		.withClaim("idEmpleado", usuario.getEmpleado().getIdEmpleado())
//        .withClaim("nombreUsuario", usuario.getEmpleado().getApellidoPaterno() + " " + usuario.getEmpleado().getApellidoMaterno() + ", "+usuario.getEmpleado().getNombres())
		.sign(algorithm);
		

		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		System.out.println(tokens);
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);

	}




    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }




    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
        super();
        this.authenticationManager = authenticationManager;

    }






}
