package com.gafahtec.consultorio.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.gafahtec.consultorio.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafahtec.consultorio.config.JwtService;
import com.gafahtec.consultorio.dto.request.AuthenticationRequest;
import com.gafahtec.consultorio.dto.request.AuthenticationResponse;
import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.dto.request.UsuarioResponse;
import com.gafahtec.consultorio.enums.TokenType;
import com.gafahtec.consultorio.model.auth.Empleado;
import com.gafahtec.consultorio.model.auth.Rol;
import com.gafahtec.consultorio.model.auth.Token;
import com.gafahtec.consultorio.model.auth.Usuario;
import com.gafahtec.consultorio.service.IUsuarioService;
import com.gafahtec.consultorio.util.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class UsuarioServiceImpl  implements IUsuarioService {
	private final IUsuarioRepository iUsuarioRepository;
	private final ITokenRepository iTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final IEmpresaRepository iEmpresaRepository;
	private final IEmpleadoRepository iEmpleadoRepository;
	private final IRolRepository iRolRepository;
	

	public Usuario register(UsuarioRequest usuarioRequest) {

		var usuarioRegistrado = iUsuarioRepository.findByEmail(usuarioRequest.getEmail());

		if (usuarioRegistrado.isEmpty()) {
			var empresa = iEmpresaRepository
					.findById(usuarioRequest.getIdEmpresa())
					.orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + usuarioRequest.getIdEmpresa()));

			var empleado = Empleado.builder()
//					.idEmpleado(usuarioRequest.getIdEmpleado())
					.activo(Constants.ACTIVO)
					.nombres(usuarioRequest.getNombres())
					.apellidoPaterno(usuarioRequest.getApellidoPaterno())
					.apellidoMaterno(usuarioRequest.getApellidoMaterno())
					.numeroDocumento(usuarioRequest.getNumeroDocumento())
					.empresa(empresa)
					.build();

			var savedEmpleado = iEmpleadoRepository.save(empleado);
	            
			Set<Rol> hashRoles = new HashSet<Rol>();
			 hashRoles.add(iRolRepository.findById(usuarioRequest.getIdRol()).get());

			var user = Usuario.builder()
					
					.email(usuarioRequest.getEmail())
					.password(passwordEncoder.encode("123456"))
					.empleado(savedEmpleado)
					.roles(hashRoles).build();
			
			var savedUser = iUsuarioRepository.save(user);
			var jwtToken = jwtService.generateToken(user);

			var refreshToken = jwtService.generateRefreshToken(user);
			saveUserToken(savedUser, jwtToken);

			return savedUser;
//			return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
		}
			log.info("Usuario ya se encuentra regostrado ");
			return null;

	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = iUsuarioRepository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	private void saveUserToken(Usuario user, String jwtToken) {
		var token = Token.builder().usuario(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false)
				.revoked(false).build();
		iTokenRepository.save(token);
	}

	private void revokeAllUserTokens(Usuario user) {
		var validUserTokens = iTokenRepository.findAllValidTokenByUser(user.getIdUsuario());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		iTokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.iUsuarioRepository.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}
	
	public  Optional<Usuario> findByEmail(String email){
		
		return iUsuarioRepository.findByEmail(email);
	}


    @Override
    public Page<Usuario> listarPageable(Pageable pageable) {

        return iUsuarioRepository.findAll(pageable);
    }
    @Override
    public Usuario getUsuarioPorId(Integer id) {

        return iUsuarioRepository.findById(id).get();
    }
    
    
    @Override
    public Usuario modificarUsuario(UsuarioRequest usuarioRequest) {

		var empresa = iEmpresaRepository
				.findById(usuarioRequest.getIdEmpresa())
				.orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + usuarioRequest.getIdEmpresa()));

        Set<Rol> hashRoles = new HashSet<Rol>();
        hashRoles.add(iRolRepository.findById(usuarioRequest.getIdRol()).get());

        var empleado = Empleado.builder()
        		.idEmpleado(usuarioRequest.getIdEmpleado())
                .activo(Constants.ACTIVO)
				.nombres(usuarioRequest.getNombres())
				.apellidoPaterno(usuarioRequest.getApellidoPaterno())
				.apellidoMaterno(usuarioRequest.getApellidoMaterno())
				.numeroDocumento(usuarioRequest.getNumeroDocumento())
                .empresa(empresa)
                .build();

		iEmpleadoRepository.save(empleado);
       
        var usuario = iUsuarioRepository.findByEmail(usuarioRequest.getEmail()).get();
		usuario.setEmpleado(empleado);
        usuario.setRoles(hashRoles);
        var userUpdate = iUsuarioRepository.save(usuario);
        log.info("usuario grabado " + userUpdate);
        return userUpdate;

    }

	@Override
	public UsuarioResponse registrar(UsuarioRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioResponse modificar(UsuarioRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<UsuarioResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioResponse listarPorId(Integer id) {
		return entityToResponse(iUsuarioRepository.findById(id).get());
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
	private UsuarioResponse entityToResponse(Usuario entity) {
		var response = new UsuarioResponse();
		
		return response;
	}

}
