package com.gafahtec.consultorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.model.Rol;
import com.gafahtec.consultorio.model.TipoEmpleado;
import com.gafahtec.consultorio.service.IEmpresaService;
import com.gafahtec.consultorio.service.IRolService;
import com.gafahtec.consultorio.service.ITipoEmpleadoService;
import com.gafahtec.consultorio.service.UsuarioService;
import com.gafahtec.consultorio.service.impl.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ConsultorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultorioApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEnconder() {

        return new BCryptPasswordEncoder();
    }

    @Bean("auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    CommandLineRunner run(IRolService iRolService, IEmpresaService iEmpresaService, UsuarioService iUsuarioService, ITipoEmpleadoService iTipoEmpleadoService) {

        return args -> {
            List<Rol> roles = new ArrayList<>();
            var rol1 = Rol.builder().idRol(1).nombre("ADMIN").build();
           
            roles.add(rol1);
            
            roles.forEach(rol -> {
                iRolService.registrar(rol);
            });

            List<Empresa> empresas = new ArrayList<>();
            var empresa1 = Empresa.builder().idEmpresa(1).nombre("gafah").build();
            
            empresas.add(empresa1);
            
            empresas.forEach(empresa -> {
                iEmpresaService.registrar(empresa);
            });
            
            List<TipoEmpleado> tipoEmpleados = new ArrayList<>();
            var e1 = TipoEmpleado.builder().idTipoEmpleado(1).descripcion("admin").build();
            
            tipoEmpleados.add(e1);
          
            tipoEmpleados.forEach(tipoEmpleado -> {
                iTipoEmpleadoService.registrar(tipoEmpleado);
            });


            
            
        };
    }


}
