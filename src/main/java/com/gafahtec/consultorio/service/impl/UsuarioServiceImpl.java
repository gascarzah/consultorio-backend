package com.gafahtec.consultorio.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.model.Empleado;
import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.model.Persona;
import com.gafahtec.consultorio.model.Rol;
import com.gafahtec.consultorio.model.TipoEmpleado;
import com.gafahtec.consultorio.model.Usuario;
import com.gafahtec.consultorio.repository.IEmpleadoRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IPersonaRepository;
import com.gafahtec.consultorio.repository.IUsuarioRepository;
import com.gafahtec.consultorio.service.UsuarioService;
import com.gafahtec.consultorio.util.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@AllArgsConstructor
@Service

@Log4j2
public class UsuarioServiceImpl extends CRUDImpl<Usuario, Integer> implements UsuarioService, UserDetailsService {

    private IUsuarioRepository repo;
    private IEmpleadoRepository iEmpleadoRepository;

    private IPersonaRepository iPersonaRepository;
//    private IRolRepository rolRepo;

    private final PasswordEncoder passwordEncoder;

//	private AuthTokenRepository authTokenRepository;

    @Override
    protected IGenericRepository<Usuario, Integer> getRepo() {

        return repo;
    }

    @Transactional
    @Override
    public Usuario registrarUsuarioEmpleado(UsuarioRequest usuarioRequest) {

        var usuarioRegistrado = repo.findByEmail(usuarioRequest.getEmail());

        if (usuarioRegistrado == null) {

            var persona = Persona.builder().nombres(usuarioRequest.getNombres())
                    .apellidoPaterno(usuarioRequest.getApellidoPaterno())
                    .apellidoMaterno(usuarioRequest.getApellidoMaterno())
                    .numeroDocumento(usuarioRequest.getNumeroDocumento())
                    .build();

            var empresa = Empresa.builder().idEmpresa(usuarioRequest.getIdEmpresa()).build();
            var tipoEmpleado = TipoEmpleado.builder().idTipoEmpleado(usuarioRequest.getIdTipoEmpleado()).build();
            var savedPersona = iPersonaRepository.save(persona);

            log.info("savedPersona grabado " + savedPersona);

            Set<Rol> hashRoles = new HashSet<Rol>();
//			hashRoles.add(rolSearched);
            hashRoles.add(Rol.builder().idRol(usuarioRequest.getIdRol()).build());

            var empleado = Empleado.builder()
                    .estado(Constants.ACTIVO)
                    .persona(persona)
                    .empresa(empresa)
                    .tipoEmpleado(tipoEmpleado)
                    .build();

            var savedEmpleado = iEmpleadoRepository.save(empleado);

            log.info("savedEmpleado grabado " + savedEmpleado);

            var usuario = Usuario.builder().email(usuarioRequest.getEmail())
                    .password(passwordEncoder.encode(usuarioRequest.getPassword()))
                    .empleado(savedEmpleado)
                    .roles(hashRoles).build();
            log.info("usuario grabado " + usuario);
            return repo.save(usuario);
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(email);

        if (usuario == null) {
            log.error("Usuario no se encuentra en base de datos");
            throw new UsernameNotFoundException("Usuario no se encuentra en base de datos");
        } else {
            log.info("Usuario encontrado en  base de datos: {}", email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        usuario.getRoles().forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));

        });

        return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }

    @Override
    public Usuario getUsuario(String email) {
        return repo.findByEmail(email);

    }

    @Override
    public Page<Usuario> listarPageable(Pageable pageable) {

        return repo.findAll(pageable);
    }

    @Override
    public Usuario getUsuarioPorId(Integer id) {

        return repo.findById(id).get();
    }

    @Transactional
    @Override
    public Usuario modificarUsuarioEmpleado(UsuarioRequest usuarioRequest) {

        var persona = Persona.builder().nombres(usuarioRequest.getNombres())
                .apellidoPaterno(usuarioRequest.getApellidoPaterno())
                .apellidoMaterno(usuarioRequest.getApellidoMaterno())
                .numeroDocumento(usuarioRequest.getNumeroDocumento())
                .build();

        var empresa = Empresa.builder().idEmpresa(usuarioRequest.getIdEmpresa()).build();

        var tipoEmpleado = TipoEmpleado.builder().idTipoEmpleado(usuarioRequest.getIdTipoEmpleado()).build();

        var savedPersona = iPersonaRepository.save(persona);

        log.info("savedPersona grabado " + savedPersona);

        Set<Rol> hashRoles = new HashSet<Rol>();
//          hashRoles.add(rolSearched);
        hashRoles.add(Rol.builder().idRol(usuarioRequest.getIdRol()).build());

        var empleado = Empleado.builder()
                .estado(Constants.ACTIVO)
                .persona(persona)
                .empresa(empresa)
                .tipoEmpleado(tipoEmpleado)
                .build();

        var savedEmpleado = iEmpleadoRepository.save(empleado);

        log.info("savedEmpleado grabado " + savedEmpleado);

        var usuario = Usuario.builder().email(usuarioRequest.getEmail())
                .password(passwordEncoder.encode(usuarioRequest.getPassword()))
                .empleado(savedEmpleado)
                .idUsuario(usuarioRequest.getIdUsuario())
                .roles(hashRoles).build();
        log.info("usuario grabado " + usuario);
        return modificar(usuario);

    }

}
